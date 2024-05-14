package com.mattel.ecomm.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.ecomm.core.services.GTEmailTnsConfigService;

@RunWith(PowerMockRunner.class)
public class QuizModelTest {

  @InjectMocks
  QuizModel quizModel;
  @Mock
  private Resource resource;
  @Mock
  ResourceResolver resolver; 
  @Mock
  Tag tag;
  @Mock
  GTEmailTnsConfigService gtEmailTnsConfigService;
  @Mock
  JSONObject value;
  @Mock
  TagManager tagManager;
  String[] tagStringList={"tag1","tag2"};
  @Before
  public void setUp() throws Exception {
    Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
    Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
    Mockito.when(gtEmailTnsConfigService.getJSONValues()).thenReturn(value);
  }

  @Test
  public void testGetOccasionTagList() throws IllegalArgumentException, JSONException, IllegalAccessException {
    MemberModifier.field(QuizModel.class, "type").set(quizModel, "occasionSectionConfig");
    MemberModifier.field(QuizModel.class, "occasionCategories").set(quizModel, tagStringList);
    List<Tag> tagList=quizModel.getOccasionTagList();
    quizModel.init();
    Assert.assertNotNull(tagList);
    Assert.assertEquals(2, tagList.size());
  }
  
  @Test
  public void testGetAttributesTagList() throws IllegalArgumentException, JSONException, IllegalAccessException {
    MemberModifier.field(QuizModel.class, "type").set(quizModel, "attributesSectionConfig");
    MemberModifier.field(QuizModel.class, "attributesCategories").set(quizModel, tagStringList);
    List<Tag> tagList=quizModel.getAttributesTagList();
    quizModel.init();
    Assert.assertNotNull(tagList);
    Assert.assertEquals(2, tagList.size());
  }
  
  @Test
  public void testGetAspirationTagList() throws IllegalArgumentException, JSONException, IllegalAccessException {
    MemberModifier.field(QuizModel.class, "type").set(quizModel, "aspirationsSectionConfig");
    MemberModifier.field(QuizModel.class, "aspirationsCategories").set(quizModel, tagStringList);
    List<Tag> tagList=quizModel.getAspirationTagList();
    quizModel.init();
    Assert.assertNotNull(tagList);
    Assert.assertEquals(2, tagList.size());
  }
}
