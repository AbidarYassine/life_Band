package com.example.lifeband.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lifeband.R;

public class CustomPopup extends Dialog {
    //files
    private String title;
    private String content;
    private final Button button;
    private final TextView titleView;
    private final TextView contentView;

    // constructor
    public CustomPopup(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog);
        this.title = "Mon titre";
        this.content = "My content";
        this.button = findViewById(R.id.button);
        this.titleView = findViewById(R.id.title);
        this.contentView = findViewById(R.id.content);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Button getButton() {
        return this.button;
    }

    public void build() {
        show();
        titleView.setText(title);
        contentView.setText(content);
    }


}
