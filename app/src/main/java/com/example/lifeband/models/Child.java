package com.example.lifeband.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Child implements Serializable {
    private String guardians_id;
    private String id;
    private List<Map<String, String>> historyBPM;
    private List<Map<String, String>> historyTEMP;

    public String getGuardians_id() {
        return guardians_id;
    }

    public void setGuardians_id(String guardians_id) {
        this.guardians_id = guardians_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, String>> getHistoryBPM() {
        return historyBPM;
    }

    public void setHistoryBPM(List<Map<String, String>> historyBPM) {
        this.historyBPM = historyBPM;
    }

    public List<Map<String, String>> getHistoryTEMP() {
        return historyTEMP;
    }

    public void setHistoryTEMP(List<Map<String, String>> historyTEMP) {
        this.historyTEMP = historyTEMP;
    }

    @Override
    public String toString() {
        return "Child{" +
                "guardians_id='" + guardians_id + '\'' +
                ", id='" + id + '\'' +
                ", historyBPM=" + historyBPM +
                ", historyTEMP=" + historyTEMP +
                '}';
    }
}
