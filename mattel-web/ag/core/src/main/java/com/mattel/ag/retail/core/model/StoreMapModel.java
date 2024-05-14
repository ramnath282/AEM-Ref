package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.services.GoogleApiConfiguration;
import com.mattel.ag.retail.core.services.MultifieldReader;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.Map;

/**
 * @author CTS. A Model class for Store Map Component.
 */

@Model(adaptables = Resource.class)

public class StoreMapModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreMapModel.class);

	@Inject
	@Optional
	private GoogleApiConfiguration googleApiConfiguration;

	@Inject
	@Optional
	private Node coordinateDetails;

	@Inject
	@Optional
	private MultifieldReader multifieldReader;

	private String googleApiKeyValue;

	private String coordinateArrayString;

	@PostConstruct
	protected void init() {

		LOGGER.info("Store Map Model init method Start");

		googleApiKeyValue = googleApiConfiguration.getGoogleApiKey();

		LOGGER.debug("API key value {}", googleApiKeyValue);
		if (coordinateDetails != null) {
			coordinateArrayString = constructJson().toString();
			LOGGER.debug("Json in string form {}", coordinateArrayString);
		}

		LOGGER.info("Store Map Model init method End");

	}

	/**
	 * @return JSONArray. This method construct json array from the value map of
	 *         multifield.
	 */
	private JSONArray constructJson() {
		LOGGER.info("Start Of construct JSON array");
		Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(coordinateDetails);
		LOGGER.debug("Map returned from service is {}", multifieldProperty);
		JSONArray jsonArray = new JSONArray();
		try {
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				JSONObject jsonObject = new JSONObject();
				ValueMap valueMap = entry.getValue();
				for (ValueMap.Entry<String, Object> vmEntry : valueMap.entrySet()) {
					LOGGER.debug("Value map Key is {}", vmEntry.getKey());
					LOGGER.debug("Value map Value is {}", vmEntry.getValue());
					if (!(vmEntry.getKey().startsWith("jcr:"))) {
						jsonObject.put(vmEntry.getKey(), vmEntry.getValue().toString());
						LOGGER.debug("JSON Object is {}", jsonObject);
					}
				}
				jsonArray.put(jsonObject);
				LOGGER.debug("JSON array Size {}", jsonArray.length());

			}
			LOGGER.debug("Final Json is {}", jsonArray);
		} catch (JSONException je) {
			LOGGER.info("Error in JSON Formatting", je);
		}
		LOGGER.info("End of Construct json method");
		return jsonArray;
	}

	public String getGoogleApiKeyValue() {
		return googleApiKeyValue;
	}

	public String getCoordinateArrayString() {
		return coordinateArrayString;
	}

	public void setGoogleApiKeyValue(String googleApiKeyValue) {
		this.googleApiKeyValue = googleApiKeyValue;
	}

	public void setCoordinateArrayString(String coordinateArrayString) {
		this.coordinateArrayString = coordinateArrayString;
	}

	public void setCoordinateDetails(Node coordinateDetails) {
		this.coordinateDetails = coordinateDetails;
	}

	public void setGoogleApiConfiguration(GoogleApiConfiguration googleApiConfiguration) {
		this.googleApiConfiguration = googleApiConfiguration;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

}