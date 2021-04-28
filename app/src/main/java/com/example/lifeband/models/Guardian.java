package com.example.lifeband.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Guardian implements Serializable {
    private String ChildAge;
    private String ChildName;
    private String Email;
    private List<String> disease;

    public String getChildAge() {
        return ChildAge;
    }

    public void setChildAge(String childAge) {
        ChildAge = childAge;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }


    public List<String> getDisease() {
        return disease;
    }

    public void setDisease(List<String> disease) {
        this.disease = disease;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Guardian{" +
                "ChildAge='" + ChildAge + '\'' +
                ", ChildName='" + ChildName + '\'' +
                ", Email='" + Email + '\'' +
                ", disease=" + disease +
                '}';
    }
}
