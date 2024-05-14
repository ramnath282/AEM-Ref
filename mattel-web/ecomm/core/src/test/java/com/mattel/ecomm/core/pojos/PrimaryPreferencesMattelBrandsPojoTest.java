package com.mattel.ecomm.core.pojos;

import com.mattel.global.core.pojo.PrimaryPreferencesMattelBrandsPojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class) public class PrimaryPreferencesMattelBrandsPojoTest {
  private PrimaryPreferencesMattelBrandsPojo primaryPreferencesMattelBrandsPojo = null;

  @Before public void setUp() throws Exception {
    primaryPreferencesMattelBrandsPojo = new PrimaryPreferencesMattelBrandsPojo();
    primaryPreferencesMattelBrandsPojo.setTitle("title");
    primaryPreferencesMattelBrandsPojo.setDescription("description");
    primaryPreferencesMattelBrandsPojo.setPreferenceID("preferenceID");
    primaryPreferencesMattelBrandsPojo.setAlwaysEnglish("alwaysEnglish");
  }

  @Test public void testGetters() {
    Assert.assertEquals("title", primaryPreferencesMattelBrandsPojo.getTitle());
    Assert.assertEquals("description", primaryPreferencesMattelBrandsPojo.getDescription());
    Assert.assertEquals("preferenceID", primaryPreferencesMattelBrandsPojo.getPreferenceID());
    Assert.assertEquals("alwaysEnglish", primaryPreferencesMattelBrandsPojo.getAlwaysEnglish());
    primaryPreferencesMattelBrandsPojo.toString();
  }

}
