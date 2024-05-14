package com.mattel.global.core.pojo;

public class AttachmentDetailsPojo {

    private String attachmentTitle;
    private String attachmentDescription;
    private String attachmentUrl;
    private String attachmentUrlThmb;
    private String attachmentUrlMid;
    private String attachmentUrlS;
    private String attachmentUrlPrv;
    private String attachmentPdf;


    public String getAttachmentTitle() {
        return attachmentTitle;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        this.attachmentTitle = attachmentTitle;
    }

    public String getAttachmentDescription() {
        return attachmentDescription;
    }

    public void setAttachmentDescription(String attachmentDescription) {
        this.attachmentDescription = attachmentDescription;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentUrlThmb() {
        return attachmentUrlThmb;
    }

    public void setAttachmentUrlThmb(String attachmentUrlThmb) {
        this.attachmentUrlThmb = attachmentUrlThmb;
    }

    public String getAttachmentUrlMid() {
        return attachmentUrlMid;
    }

    public void setAttachmentUrlMid(String attachmentUrlMid) {
        this.attachmentUrlMid = attachmentUrlMid;
    }

    public String getAttachmentUrlS() {
        return attachmentUrlS;
    }

    public void setAttachmentUrlS(String attachmentUrlS) {
        this.attachmentUrlS = attachmentUrlS;
    }

    public String getAttachmentUrlPrv() {
        return attachmentUrlPrv;
    }

    public void setAttachmentUrlPrv(String attachmentUrlPrv) {
        this.attachmentUrlPrv = attachmentUrlPrv;
    }

  public String getAttachmentPdf() {
    return attachmentPdf;
  }
  public void setAttachmentPdf(String attachmentPdf) {
    this.attachmentPdf = attachmentPdf;
  }

  @Override
  public String toString() {
    return "AttachmentDetailsPojo [attachmentTitle=" + attachmentTitle + ", attachmentDescription="
        + attachmentDescription + ", attachmentUrl=" + attachmentUrl + ", attachmentUrlThmb="
        + attachmentUrlThmb + ", attachmentUrlMid=" + attachmentUrlMid + ", attachmentUrlS="
        + attachmentUrlS + ", attachmentUrlPrv=" + attachmentUrlPrv + ", attachmentPdf="
        + attachmentPdf + "]";
  }

}

