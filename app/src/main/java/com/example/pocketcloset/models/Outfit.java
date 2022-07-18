package com.example.pocketcloset.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Outfit")
public class Outfit extends ParseObject {

    public static final String KEY_HEADWEAR = "Headwear";
    public static final String KEY_TOP = "Top";
    public static final String KEY_BOTTOMS = "Bottoms";
    public static final String KEY_OVERWEAR = "Outerwear";
    public static final String KEY_SHOES = "Shoes";
    public static final String KEY_HANDHELD = "Handheld";
    public static final String KEY_EARRINGS = "Earrings";
    public static final String KEY_BRACELET = "Bracelet";
    public static final String KEY_NECKWEAR = "Neckwear";
    public static final String KEY_OUTFIT_NAME = "outfitName";
    public static final String KEY_USER = "user";

    public Clothing getHeadwear() {
        return (Clothing) getParseObject(KEY_HEADWEAR);
    }
    public void setHeadwear(Clothing headwear) {
        put(KEY_HEADWEAR,headwear);
    }
    public Clothing getTop() {
        return (Clothing) getParseObject(KEY_TOP);
    }
    public void setTop(Clothing top) {
        put(KEY_TOP,top);
    }
    public Clothing getBottoms() {
        return (Clothing) getParseObject(KEY_BOTTOMS);
    }
    public void setBottoms(Clothing bottoms) {
        put(KEY_BOTTOMS,bottoms);
    }
    public Clothing getEarrings() {
        return (Clothing) getParseObject(KEY_EARRINGS);

    } public void setEarrings(Clothing earrings) {
        put(KEY_EARRINGS,earrings);
    }

    public Clothing getNeckwear() {
        return (Clothing) getParseObject(KEY_NECKWEAR);
    }
    public void setNeckwear(Clothing neckwear) {
        put(KEY_NECKWEAR,neckwear);
    }
    public Clothing getBracelet() {
        return (Clothing) getParseObject(KEY_BRACELET);
    }
    public void setBracelet(Clothing bracelet) {
        put(KEY_BRACELET,bracelet);
    }
    public Clothing getHandheld() {
        return (Clothing) getParseObject(KEY_HANDHELD);
    }
    public void setHandheld(Clothing handheld) {
        put(KEY_HANDHELD,handheld);
    }
    public Clothing getShoes() {
        return (Clothing) getParseObject(KEY_SHOES);
    }
    public void setShoes(Clothing shoes) {
        put(KEY_SHOES,shoes);
    }
    public Clothing getOverwear() {
        return (Clothing) getParseObject(KEY_OVERWEAR);
    }
    public void setOverwear(Clothing overwear) {
        put(KEY_OVERWEAR,overwear);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getOutfitName() {return getString(KEY_OUTFIT_NAME);}
    public void setOutfitName(String outfitName) {
        put(KEY_OUTFIT_NAME, outfitName);
    }
}