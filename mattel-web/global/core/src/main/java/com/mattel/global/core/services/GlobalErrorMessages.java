package com.mattel.global.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author CTS
 *
 */
@Component(service = GlobalErrorMessages.class, immediate = true)
@Designate(ocd = GlobalErrorMessages.Config.class)
public class GlobalErrorMessages {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorMessages.class);


    private String errorMessagePath;
   

    public void setErrorMessagePath(String errorMessagePath) {
		this.errorMessagePath = errorMessagePath;
	}

	@ObjectClassDefinition(name = "Global Error Messages")
    public @interface Config {

        @AttributeDefinition(name = "Error Message Page Path", description = "Please provide the rootpath of global error message page component. Default is /content/mattelweb/crm/language-masters/global-error-messaging/jcr:content/root/errormessages")
        String errorMessagePath() default "/content/mattelweb/crm/language-masters/global-error-messaging/jcr:content/root/errormessages";
    }

    /**
     * @param config
     */
    @Activate
    public void activate(final Config config) {

    	errorMessagePath = config.errorMessagePath();
    }
    
    /**
     * @return
     */
    public String getErrorMessagePath()
    {
    	LOGGER.debug("errorMessage path configured is{}",errorMessagePath);
    	return errorMessagePath;
    }
    
}
