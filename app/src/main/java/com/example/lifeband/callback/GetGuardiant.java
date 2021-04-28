package com.example.lifeband.callback;

import com.example.lifeband.models.Child;
import com.example.lifeband.models.Guardian;

import java.security.Guard;

public interface GetGuardiant {
    void onComplete(Guardian guardian);
}
