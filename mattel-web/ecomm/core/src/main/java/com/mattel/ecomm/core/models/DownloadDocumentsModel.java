package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.pojos.DownloadDocumentsPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@Model(adaptables = Resource.class)
public class DownloadDocumentsModel {
	@Inject
	@Optional
	private Node documentDetailList;
	@Inject
	MultifieldReader multifieldReader;
	@Self
	Resource resource;
	private List<DownloadDocumentsPojo> downloadDocumentList = new LinkedList<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadDocumentsModel.class);

	@PostConstruct
	protected void init() {
		LOGGER.info("DownloadDocumentsModel init method ---> Start");
		if (documentDetailList != null && null != resource) {
			Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(documentDetailList);
			if (multifieldProperty != null) {
				fetchDownloadLinks(multifieldProperty);
			}
		}
		LOGGER.info("DownloadDocumentsModel init method ---> End");
	}

	private void fetchDownloadLinks(Map<String, ValueMap> multifieldProperty) {
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			DownloadDocumentsPojo documentDetail = new DownloadDocumentsPojo();
			documentDetail.setDocumentTitle(entry.getValue().get("documentTitle", String.class));
			String docLink = entry.getValue().get("documentLink", String.class);
			fetchDocMetadata(docLink, documentDetail);
			downloadDocumentList.add(documentDetail);
		}
	}

	private void fetchDocMetadata(String docLink, DownloadDocumentsPojo documentDetail) {
		ResourceResolver resolver = resource.getResourceResolver();
		if (docLink != null) {
			documentDetail.setDocumentLink(EcommHelper.checkLink(docLink, resource));
			Resource docResource = resolver.getResource(docLink);
			if (docResource != null) {
				Asset document = docResource.adaptTo(Asset.class);
				if (document != null) {
					documentDetail.setDocumentType(document.getMetadataValue("dc:format").split("/")[1].toUpperCase());
					Long size = Long.parseLong(document.getMetadataValue("dam:size"));
					int temp = (int) (size / 1024);
					if (temp > 1024) {
						String tempSize = String.valueOf(temp / 1024) + " mb";
						documentDetail.setDocumentSize(tempSize);
					} else {
						String tempSize = String.valueOf(temp) + " kb";
						documentDetail.setDocumentSize(tempSize);
					}
				}
			}
		}

	}

	public List<DownloadDocumentsPojo> getDownloadDocumentList() {
		return downloadDocumentList;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setDocumentDetailList(Node documentDetailList) {
		this.documentDetailList = documentDetailList;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

}
