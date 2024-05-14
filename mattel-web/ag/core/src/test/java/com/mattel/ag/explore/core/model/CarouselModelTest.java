package com.mattel.ag.explore.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ag.explore.core.pojos.CarouselPojo;
import com.mattel.ag.retail.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class CarouselModelTest {

    @InjectMocks
    CarouselModel carouselModel;
    Node carouselDetails;
    MultifieldReader multifieldReader;
    Map<String, ValueMap> multifieldProperty = new HashMap<>();
    ValueMap valueMap;
    Resource resource;

    @Before
    public void setUp() throws IllegalAccessException {
        carouselModel = new CarouselModel();
        carouselDetails = Mockito.mock(Node.class);
        multifieldReader = Mockito.mock(MultifieldReader.class);
        valueMap = Mockito.mock(ValueMap.class);
        resource = Mockito.mock(Resource.class);
        multifieldProperty.put("", valueMap);
        MemberModifier.field(CarouselModel.class, "resource").set(carouselModel, resource);
        carouselModel.setCarouselDetails(carouselDetails);
        carouselModel.setMultifieldReader(multifieldReader);

        Mockito.when(multifieldReader.propertyReader(carouselDetails)).thenReturn(multifieldProperty);

    }

    @Test
    public void init() {
        valueMap.put("slideScroll", "val1");
        valueMap.put("centerMode", "val2");
        valueMap.put("arrows", "val3");
        valueMap.put("slideShow", "val4");
        valueMap.put("dots", "val5");
        valueMap.put("slideShow", "val6");
        valueMap.put("seeEverythingLink", "val7");
        Mockito.when(resource.getValueMap()).thenReturn(valueMap);

        carouselModel.init();
    }
    
    @Test
    public void testInitWithAttributes(){
      Mockito.when(resource.getValueMap()).thenReturn(valueMap);
      Mockito.when(valueMap.get("slideScroll", String.class)).thenReturn("slideScroll");
      Mockito.when(valueMap.get("centerMode", String.class)).thenReturn("centerMode");
      Mockito.when(valueMap.get("autoPlay", String.class)).thenReturn("autoPlay");
      Mockito.when(valueMap.get("arrows", String.class)).thenReturn("arrows");
      Mockito.when(valueMap.get("slideShow", String.class)).thenReturn("slideShow");
      Mockito.when(valueMap.get("dots", String.class)).thenReturn("dots");
      Mockito.when(valueMap.get("seeEverythingLink", String.class)).thenReturn("seeEverythingLink");
      carouselModel.init();
    }

    @Test
    public void getMultifieldReader() {
        carouselModel.getMultifieldReader();
    }

    @Test
    public void getCarouselFeaturesData() {
        carouselModel.getCarouselFeaturesData();
    }
    
    @Test
    public void getGetters() {
      @SuppressWarnings("unchecked")
      List<CarouselPojo> carouselDetailsList = Mockito.mock(List.class);
      carouselModel.setCarouselDetailsList(carouselDetailsList);
      carouselModel.getCarouselDetailsList();
      carouselModel.getCarouselDetails();
  }
}
