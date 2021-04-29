package com.example.lifeband.utils;

public class AdapterData {
    private String date;
    private String BPM;
    private String TEMP;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBPM() {
        return BPM;
    }

    public void setBPM(String BPM) {
        this.BPM = BPM;
    }

    public String getTEMP() {
        return TEMP;
    }

    public void setTEMP(String TEMP) {
        this.TEMP = TEMP;
    }

    public AdapterData(String date, String BPM, String TEMP) {
        this.date = date;
        this.BPM = BPM;
        this.TEMP = TEMP;
    }

    public AdapterData() {

    }

    @Override
    public String toString() {
        return "AdapterData{" +
                "date='" + date + '\'' +
                ", BPM='" + BPM + '\'' +
                ", TEMP='" + TEMP + '\'' +
                '}';
    }
}
