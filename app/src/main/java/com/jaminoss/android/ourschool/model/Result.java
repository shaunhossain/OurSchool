package com.jaminoss.android.ourschool.model;

/**
 * Created by kodenerd on 2/15/18.
 */

public class Result {

    String eng, maths, phy, chm;

    public Result() {
    }

    public Result(String eng, String maths, String phy, String chm) {
        this.eng = eng;
        this.maths = maths;
        this.phy = phy;
        this.chm = chm;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getMaths() {
        return maths;
    }

    public void setMaths(String maths) {
        this.maths = maths;
    }

    public String getPhy() {
        return phy;
    }

    public void setPhy(String phy) {
        this.phy = phy;
    }

    public String getChm() {
        return chm;
    }

    public void setChm(String chm) {
        this.chm = chm;
    }
}
