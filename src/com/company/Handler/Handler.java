package com.company.Handler;

/**
 * Created by apava on 29.09.2016.
 */
public class Handler {
    int numberOfPackage;
    boolean isLoad;
    boolean isDownLoad;
    boolean isLoadUdp;
    boolean isDownloadUdp;
    String nameOfFile;

    public void setLoadUdp(boolean loadUdp) {
        isLoadUdp = loadUdp;
    }

    public void setDownloadUdp(boolean downloadUdp) {
        isDownloadUdp = downloadUdp;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public Handler(){

    }

    public int getNumberOfPackage() {
        return numberOfPackage;
    }

    public void setNumberOfPackage(int numberOfPackage) {
        this.numberOfPackage = numberOfPackage;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setIsLoad(boolean isLoad) {
        this.isLoad = isLoad;
    }

    public boolean isDownLoad() {
        return isDownLoad;
    }

    public void setIsDownLoad(boolean isDownLoad) {
        this.isDownLoad = isDownLoad;
    }

    public boolean isLoadUdp() {
        return isLoadUdp;
    }

    public boolean isDownloadUdp() {
        return isDownloadUdp;
    }
}
