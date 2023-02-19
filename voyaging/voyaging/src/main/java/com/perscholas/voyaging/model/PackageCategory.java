package com.perscholas.voyaging.model;

public enum PackageCategory {
    STANDARD("", ""),
    HALF_BOARD("Half Board","Half board includes breakfast and dinner"),
    FULL_BOARD("Full board", "Full boardincludes breakfast, lunch and dinner");
    private final String aPackage;
    private final String description;
    PackageCategory(String aPackage, String description){
        this.aPackage= aPackage;
        this.description = description;
    }

    public String getaPackage(){
        return aPackage;
    }
    public String getDescription(){
        return description;
    }
}
