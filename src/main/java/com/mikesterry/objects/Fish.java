package com.mikesterry.objects;

public class Fish {
    private String code;
    private String commonName;

    public Fish(String code, String commonName) {
        this.code = code;
        this.commonName = commonName;
    }

    public String getCode() {
        return code;
    }

    public String getCommonName() {
        return commonName;
    }
}
