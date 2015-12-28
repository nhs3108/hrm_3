package com.nhs3108.fhrm.utils;

/**
 * Created by hongson on 28/12/2015.
 */
public class StringWithTag {
    private String string;
    private String tag;

    public StringWithTag(String string, String tag) {
        this.string = string;
        this.tag = tag;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String toString() {
        return string;
    }
}
