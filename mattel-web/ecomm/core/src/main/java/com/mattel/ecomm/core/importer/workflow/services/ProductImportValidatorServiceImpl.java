package com.mattel.ecomm.core.importer.workflow.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductImportValidatorService;
import com.mattel.ecomm.core.services.GetResourceResolver;
import com.mattel.ecomm.coreservices.core.pojos.ValidationResult;

@Component(service = ProductImportValidatorService.class)
@Designate(ocd = ProductImportValidatorServiceImpl.Config.class)
public class ProductImportValidatorServiceImpl implements ProductImportValidatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportValidatorServiceImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, List<Validator>> fieldToValidators = new HashMap<>();
    private List<String> fieldsToValidate = new ArrayList<>();
    private List<String> mandatoryFields = new ArrayList<>();
    @Reference
    private GetResourceResolver getResourceResolver;

    @Override
    public ValidationResult validate(String fieldName, JsonNode fieldValue, Map<String, Object> attributes) {
        final List<Validator> validators = fieldToValidators.get(fieldName);
        final ValidationResult validationResult = new ValidationResult();

        if (null != validators && !validators.isEmpty()) {
            for (final Validator validator : validators) {
                try {
                    validator.isOk(fieldName, fieldValue);
                } catch (final ValidationException e) {
                    ProductImportValidatorServiceImpl.LOGGER
                            .trace("Field failed validation while reading product feed file", e);
                    validationResult.setSuccess(false);
                    validationResult.addErrorMessage(e.getMessage());
                }
            }
        }

        return validationResult;
    }

    public enum FieldTypes {
        NON_EMPTY_STRING {
          @Override
          public boolean isValid(JsonNode value) {
              return value.isTextual() && !StringUtils.isEmpty(value.textValue());
          }
        },
        INTEGER {
            @Override
            public boolean isValid(JsonNode value) {
                return value.isInt();
            }
        },
        BOOLEAN {
            @Override
            public boolean isValid(JsonNode value) {
                return value.isBoolean();
            }
        },
        DOUBLE {
            @Override
            public boolean isValid(JsonNode value) {
                return value.isDouble();
            }
        },
        FLOAT {
            @Override
            public boolean isValid(JsonNode value) {
                return value.isFloat();
            }
        },
        DEFAULT;

        public boolean isValid(JsonNode value) {
            return null != value;
        }
    }

    public interface Validator {
        boolean isOk(String fieldName, JsonNode value) throws ValidationException;
    }

    public class ValidationException extends Exception {
        private static final long serialVersionUID = 3812857201422413514L;

        public ValidationException() {
            super();
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(Throwable cause) {
            super(cause);
        }
    }

    public class MandatoryFieldValidator implements Validator {
        @Override
        public boolean isOk(String fieldName, JsonNode value) throws ValidationException {
            if (null == value) {
                throw new ValidationException(String.format("Missing mandatory field: %s", fieldName));
            }
            return null != value;
        }
    }

    public class TypeValidator implements Validator {
        private FieldTypes type;

        public TypeValidator(String type) {
            try {
                this.type = FieldTypes.valueOf(type);
            } catch (IllegalArgumentException | NullPointerException e) {
                ProductImportValidatorServiceImpl.LOGGER.error("Invalid type ", e);
                this.type = FieldTypes.DEFAULT;
            }
        }

        @Override
        public boolean isOk(String fieldName, JsonNode value) throws ValidationException {
            if (!type.isValid(value)) {
                throw new ValidationException(String.format("Field: %s should of type %s", fieldName, type));
            }
            return type.isValid(value);
        }
    }

    public class RegexValidator implements Validator {
        private final Pattern pattern;

        public RegexValidator(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        public RegexValidator(String regex, int flags) {
            this.pattern = Pattern.compile(regex, flags);
        }

        @Override
        public boolean isOk(String fieldName, JsonNode value) throws ValidationException {
            final boolean matches = pattern.matcher(value.asText()).matches();
            if (!matches) {
                throw new ValidationException(String.format("%s value does not match predefined pattern", fieldName));
            }
            return matches;
        }
    }

    private void buildValidationSteps(final InputStream in) throws IOException {
        final Map<String, Map<String, String>> productSchema = mapper.readValue(in,
                new TypeReference<Map<String, Map<String, String>>>() {
                });

        for (final Map.Entry<String, Map<String, String>> entrySet : productSchema.entrySet()) {
            final String fieldName = entrySet.getKey();
            final Map<String, String> fieldSchema = entrySet.getValue();
            final List<Validator> validators = new ArrayList<>();

            if (!StringUtils.isEmpty(fieldSchema.get("isMandatory")) && "true".equals(fieldSchema.get("isMandatory"))) {
                validators.add(new MandatoryFieldValidator());
                mandatoryFields.add(fieldName);
            }

            if (!StringUtils.isEmpty(fieldSchema.get("type"))) {
                validators.add(new TypeValidator(fieldSchema.get("type")));
            }

            if (!StringUtils.isEmpty(fieldSchema.get("regex"))) {
                validators.add(new RegexValidator(fieldSchema.get("regex")));
            }

            fieldToValidators.put(fieldName, validators);
        }
    }

    @ObjectClassDefinition(name = "Product feed validator service", description = "This service validates the each product node")
    public @interface Config {
        @AttributeDefinition(name = "Product schema file", description = "Path of product schema file used to build validators")
        String schemaPath() default "/content/dam/ag/productfeedvalidator/schema.json";
    }

    @Activate
    public void activate(Config config) {
        try (ResourceResolver resourceResolver = getResourceResolver.getResourceResolver()) {
            final String path = config.schemaPath();
            final Resource resource = resourceResolver.getResource(path);
            InputStream is = null;

            if (Objects.nonNull(resource)) {
                Node node = resource.adaptTo(Node.class);

                if (Objects.nonNull(node)) {
                    is = node.getNode("jcr:content").getProperty("jcr:data").getBinary().getStream();
                }
            }

            ProductImportValidatorServiceImpl.LOGGER.debug("Schema path: {}", path);

            try (InputStream in = Optional.ofNullable(is)
                    .orElse(getClass().getResourceAsStream("productFeedSchema.json"))) {
                buildValidationSteps(in);
                fieldsToValidate.addAll(fieldToValidators.keySet());
                fieldsToValidate = Collections.unmodifiableList(fieldsToValidate);
            }
        } catch (final Exception io) {
            ProductImportValidatorServiceImpl.LOGGER.error("Unable to build product feed data validators", io);
        }
    }

    @Deactivate
    public void deactivate() {
        fieldsToValidate = null;
        fieldToValidators.clear();
    }

    @Override
    public List<String> fieldToValidate() {
        return fieldsToValidate;
    }

    @Override
    public boolean isMandatory(String fieldName) {
        return mandatoryFields.contains(fieldName);
    }
}
