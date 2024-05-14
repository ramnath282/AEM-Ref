package com.mattel.ecomm.core.pojos;

import java.util.List;
import com.day.cq.tagging.Tag;


public class BasicInformationPojo {

    private String dollImage;
    private String dollDescription;
    private String helperTextTreatment;
    private String helperPopOverTextTreatment;
    private String treatmentLinkType;
    private String treatmentVideoType;
    private String videoLinkTreatment;
    private List<Tag> dollsCategories;


    public String getDollImage() {
        return dollImage;
    }

    public void setDollImage(String dollImage) {
        this.dollImage = dollImage;
    }

    public String getDollDescription() {
        return dollDescription;
    }
    
    public String getHelperTextTreatment() {
        return helperTextTreatment;
    }
    
    public String getHelperPopOverTextTreatment() {
        return helperPopOverTextTreatment;
    }
    
    public String getVideoLinkTreatment() {
        return videoLinkTreatment;
    }

    public void setHelperTextTreatment(String helperTextTreatment) {
        this.helperTextTreatment = helperTextTreatment;
    }
    
    public void setHelperPopOverTextTreatment(String helperPopOverTextTreatment) {
        this.helperPopOverTextTreatment = helperPopOverTextTreatment;
    }
    
    public void setVideoLinkTreatment(String videoLinkTreatment) {
       this.videoLinkTreatment =videoLinkTreatment;
    }
    public void setDollDescription(String dollDescription) {
        this.dollDescription = dollDescription;
    }

    public List<Tag> getDollsCategories() {
        return dollsCategories;
    }

    public void setDollsCategories(List<Tag> dollsCategories) {
        this.dollsCategories = dollsCategories;
    }
    
    public String getTreatmentLinkType() {
        return treatmentLinkType;
    }

    public void setTreatmentLinkType(String treatmentLinkType) {
        this.treatmentLinkType = treatmentLinkType;
    }

    public String getTreatmentVideoType() {
        return treatmentVideoType;
    }

    public void setTreatmentVideoType(String treatmentVideoType) {
        this.treatmentVideoType = treatmentVideoType;
    }

    @Override
    public String toString() {
        return "BasicInformationPojo [dollImage=" + dollImage + ", dollsCategories="
                + dollsCategories + ", dollDescription=" + dollDescription+ 
                ", treatmentLinkType=" + treatmentLinkType + ", treatmentVideoType=" + treatmentVideoType + "]";
    }

}