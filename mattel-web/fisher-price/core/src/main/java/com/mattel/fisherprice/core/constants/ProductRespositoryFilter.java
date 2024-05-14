package com.mattel.fisherprice.core.constants;

import java.util.Objects;
import java.util.function.Predicate;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Predicate to help filtering products based on {@link ProductFeederConstants#ACTIVE active flag}
 * and {@link ProductFeederConstants#PUBLISHED published flag}.
 */
public enum ProductRespositoryFilter implements Predicate<JSONObject> {
  DEFAULT, //
  IS_ACTIVE {
    @Override
    public boolean test(JSONObject product) {
      String activate = null;

      try {
        activate = product.getString(ProductFeederConstants.ACTIVE);
      } catch (final JSONException e) {
        ProductRespositoryFilter.LOGGER.error("Error while reading product for active flag", e);
      }

      return Objects.nonNull(activate) && ProductFeederConstants.NODE_ACTIVE.equals(activate);
    }
  },
  IS_DEACTIVE {
    @Override
    public boolean test(JSONObject product) {
      String deactivate = null;

      try {
        deactivate = product.getString(ProductFeederConstants.ACTIVE);
      } catch (final JSONException e) {
        ProductRespositoryFilter.LOGGER.error("Error while reading product for deactivate flag", e);
      }

      return Objects.nonNull(deactivate) && ProductFeederConstants.NODE_DEACTIVE.equals(deactivate);
    }
  },
  IS_PUBLISH {
    @Override
    public boolean test(JSONObject product) {
      String publish = null;

      try {
        publish = product.getString(ProductFeederConstants.PUBLISHED);
      } catch (final JSONException e) {
        ProductRespositoryFilter.LOGGER.error("Error while reading product for publish flag", e);
      }

      return Objects.nonNull(publish) && ProductFeederConstants.NODE_PUBLISH.equals(publish);
    }
  },
  IS_UNPUBLISH {
    @Override
    public boolean test(JSONObject product) {
      String unpublish = null;

      try {
        unpublish = product.getString(ProductFeederConstants.PUBLISHED);
      } catch (final JSONException e) {
        ProductRespositoryFilter.LOGGER.error("Error while reading product for unpublish flag", e);
      }

      return Objects.nonNull(unpublish) && ProductFeederConstants.NODE_UNPUBLISH.equals(unpublish);
    }
  };

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductRespositoryFilter.class);

  @Override
  public boolean test(JSONObject product) {
    return Objects.nonNull(product);
  }
}
