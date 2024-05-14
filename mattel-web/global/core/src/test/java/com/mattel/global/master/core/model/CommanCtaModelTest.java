package com.mattel.global.master.core.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;

@RunWith(PowerMockRunner.class)
public class CommanCtaModelTest {
    
    @InjectMocks
    private CommanCtaModel commanCtaModel;
    
    @Mock
    Carousel carousel;
    
    List<ListItem> items;

    ListItem listItem;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
      MemberModifier.field(CommanCtaModel.class, "carousel").set(commanCtaModel, carousel);
      MemberModifier.field(CommanCtaModel.class, "entrCompClickable").set(commanCtaModel, "entrCompClickable");
      items = Mockito.mock(List.class);
    }
    
    @Test
    public void testTheCommanCtaModelFields(){
        Assert.assertEquals("entrCompClickable", commanCtaModel.getEntrCompClickable());
        Mockito.when(carousel.getItems()).thenReturn(items);
        Assert.assertEquals(items, commanCtaModel.getItems());
    } 
  
}
