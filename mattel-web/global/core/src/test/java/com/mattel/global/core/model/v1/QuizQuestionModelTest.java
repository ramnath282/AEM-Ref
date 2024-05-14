package com.mattel.global.core.model.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import io.wcm.testing.mock.aem.junit.AemContext;

public class QuizQuestionModelTest {
	
	@Rule
	public final AemContext context = new AemContext();
	
	QuizQuestionModel quizQuestionModel;

	@Before
	public void setup() {
		context.addModelsForClasses(QuizQuestionModel.class);
		Map<String, String> quizContainer = new ImmutableMap.Builder<String, String>().put("bannerText", "Banner Text")
				.put("bannerImage", "/content/dam/ag-dam/discover/xl/ag-blaire-video-thumb.jpg")
				.put("backwardButtonLabel", "Previous")
				.put("trackPreviousCTA", "true")
				.put("previousCTATrackingText","previous")
				.put("forwardButtonLabel","Next")
				.put("trackNextCTA","true")
				.put("nextCTATrackingText","Next track")
				.put("submitButtonLabel","Submit")
				.put("trackSubmitCTA","true")
				.put("submitCTATrackingText","Submit track")
				.put("sling:resourceType","mattel/ecomm/fisher-price/components/content/quizContainer")
				.build();
		
		
		context.create().tag("mattelweb:playsites/pollypocket/products/dolls/dolls_prod1");
		context.create().tag("mattelweb:playsites/pollypocket/products/dolls/dolls-prod2");
		context.create().tag("mattelweb:playsites/pollypocket/products/dolls/dolls-prod3");
		context.create().tag("mattelweb:playsites/pollypocket/products/dolls/dolls-prod4");
		
		String [] tags = {"mattelweb:playsites/pollypocket/products/dolls/dolls_prod1","mattelweb:playsites/pollypocket/products/dolls/dolls-prod2","mattelweb:playsites/pollypocket/products/dolls/dolls-prod3","mattelweb:playsites/pollypocket/products/dolls/dolls-prod4"};
		Map<String, Object> quizQuestion3 = new ImmutableMap.Builder<String,Object>()
				.put("title","what do you like")				
				.put("description","dolls")
				.put("selectedTags", tags)
				.put("subTitle","Select multiple")
				.put("answerSelectionType","radio")	
				.build();
		
		context.build().resource("/content/fisher-price/home/jcr:content/quiz",quizContainer).hierarchyMode()
		.resource("path").resource("item0",quizQuestion3);
		
	}
	
	@Test
	public void test() {
		Resource res = context.currentResource("/content/fisher-price/home/jcr:content/quiz/path/item0");
		quizQuestionModel = res.adaptTo(QuizQuestionModel.class);
		assertEquals("what do you like", quizQuestionModel.getTitle());
		assertEquals("Select multiple", quizQuestionModel.getSubTitle());
		assertEquals("dolls",quizQuestionModel.getDescription());
		assertEquals("radio", quizQuestionModel.getAnswerSelectionType());
		assertEquals("Banner Text", quizQuestionModel.getBannerText());
		assertEquals("/content/dam/ag-dam/discover/xl/ag-blaire-video-thumb.jpg", quizQuestionModel.getBannerImage());
		assertEquals("Previous", quizQuestionModel.getBackwardButtonLabel());
		assertEquals("true", quizQuestionModel.getTrackPreviousCTA());
		assertEquals("previous", quizQuestionModel.getPreviousCTATrackingText());
		assertEquals("Next", quizQuestionModel.getForwardButtonLabel());
		assertEquals("true", quizQuestionModel.getTrackNextCTA());
		assertEquals("Next track", quizQuestionModel.getNextCTATrackingText());
		assertEquals("Submit", quizQuestionModel.getSubmitButtonLabel());
		assertEquals("true", quizQuestionModel.getTrackSubmitCTA());
		assertEquals("Submit track", quizQuestionModel.getSubmitCTATrackingText());
		assertEquals("dolls",quizQuestionModel.getTagParentDetails());
		assertEquals(1,quizQuestionModel.getTotalCount());
		assertEquals(1,quizQuestionModel.getCount());
		assertTrue(quizQuestionModel.getTagDetails().size() > 0);
		
	}
}
