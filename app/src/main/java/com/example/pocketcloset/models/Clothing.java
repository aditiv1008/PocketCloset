package com.example.pocketcloset.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Clothing")
public class Clothing extends ParseObject {

    public static final String KEY_CLOTHING_TYPE = "clothingType";
    public static final String KEY_CLOTHING_IMAGE = "clothingPhoto";
    public static final String KEY_USER = "user";
    public static final String KEY_WORN_COUNT = "wornCount";

    public String getClothingType() {
        return getString(KEY_CLOTHING_TYPE);
    }

    public void setClothingType(String clothingType) {
        put(KEY_CLOTHING_TYPE,clothingType);
    }
    public Clothing (){}
    public ParseFile getClothingImage() {
        return getParseFile(KEY_CLOTHING_IMAGE);
    }

    public void setClothingImage(ParseFile parseFile) {
        put(KEY_CLOTHING_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public Integer getWornCount() {
        return getInt(KEY_WORN_COUNT);
    }

    public void setWornCount(Integer count) {
        put(KEY_WORN_COUNT, count);
    }



    }
