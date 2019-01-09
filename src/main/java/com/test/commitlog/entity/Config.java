package com.test.commitlog.entity;

/**
 * Config params from properties file
 *
 */
public class Config {

    private int numOfWriterForA;

    private int numOfWriterForB;

    private String configFileName;

    public int getNumOfWriterForA() {
        return numOfWriterForA;
    }

    public void setNumOfWriterForA(int numOfWriterForA) {
        this.numOfWriterForA = numOfWriterForA;
    }

    public int getNumOfWriterForB() {
        return numOfWriterForB;
    }

    public void setNumOfWriterForB(int numOfWriterForB) {
        this.numOfWriterForB = numOfWriterForB;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }
}
