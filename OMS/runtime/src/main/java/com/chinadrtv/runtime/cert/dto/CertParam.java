package com.chinadrtv.runtime.cert.dto;

public class CertParam {

    public int id;

    public int width;

    public int height;

    public int codeCount;

    public CertType certType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodeCount() {
        return codeCount;
    }

    public void setCodeCount(int codeCount) {
        this.codeCount = codeCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public CertType getCertType() {
        return certType;
    }

    public void setCertType(CertType certType) {
        this.certType = certType;
    }

}
