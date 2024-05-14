package com.mattel.ecomm.core.interfaces;

import java.util.List;

public interface MarketingContentProviderService {

	/**
	 * Returns content associated with the skuId under the target path
	 * 
	 * @param siteKey
	 *            - Site key for Taking values from config.
	 * @param skuId
	 *            - product skuId for which content has returned
	 * @return List of content paths associated with skuId under targeted path
	 */
	List<String> getContentFromTags(String siteKey,String skuId, String positionTag);

}
