package com.example.lifeband.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Child implements Serializable {
    private String guardians_id;
    private String id;
    private List<Map<String, String>> history;

    public String getGuardians_id() {
        return guardians_id;
    }

    public void setGuardians_id(String guardians_id) {
        this.guardians_id = guardians_id;
    }

    public List<Map<String, String>> getHistory() {
        return history;
    }

    public void setHistory(List<Map<String, String>> history) {
        this.history = history;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Child{" +
                "guardians_id='" + guardians_id + '\'' +
                ", id='" + id + '\'' +
                ", history=" + history +
                '}';
    }
}
