package com.mattel.play.core.commerce;

import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.common.AbstractJcrCommerceServiceFactory;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
 
/**
 * A simple new (for training) implementation for the {@link CommerceServiceFactory} interface.
 */
@Component(service = CommerceServiceFactory.class, immediate = true)
@Designate(ocd = PlayCommerceServiceFactory.Config.class)

public class PlayCommerceServiceFactory extends AbstractJcrCommerceServiceFactory implements CommerceServiceFactory {
 
	private static String commerceProvider;
	
	@Activate
	public void activate(final Config config) {
		PlayCommerceServiceFactory.buildConfigs(config);
	}
	
	private static void buildConfigs(Config config) {
		commerceProvider = config.commerceProvider();
	}
	
	@ObjectClassDefinition(name = "Factory for Play commerce service")
	public @interface Config {
		@AttributeDefinition(name = "Commerce Provider", description = "Enter the commernce provider")
		String commerceProvider() default "mattel-play";

	}
	
	
	public static String getCommerceProvider() {
		return commerceProvider;
	}
	/**
     * Create a new <code>TrainingCommerceServiceImpl</code>.
     */
    public CommerceService getCommerceService(Resource res) {
        return new PlayCommerceServiceImpl(getServiceContext(), res);
    }
 
}