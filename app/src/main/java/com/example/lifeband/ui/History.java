package com.example.lifeband.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lifeband.R;
import com.example.lifeband.db.ChildDb;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.Set;

public class History extends AppCompatActivity {
    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tl = (TableLayout) findViewById(R.id.table_result);
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        ChildDb.getChildByGuarId(child -> {
//
//        }, uid);
        addNewRow();
    }

    public void addNewRow() {
        /* Create a new row to be added. */
//        TableRow tr = new TableRow(this);
//        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        /* Create a Button to be the row-content. */
//        TextView textView1 = new TextView(this);
//        textView1.setText("Dynamic");
//        textView1.setGravity(Gravity.CENTER);
//        textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//        /* Add TextView to row. */
//        TextView textView2 = new TextView(this);
//        textView2.setText("Dynamic");
//        textView2.setGravity(Gravity.CENTER);
//        textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//        TextView textView = new TextView(this);
//        textView.setText("Dynamic");
//        textView.setGravity(Gravity.CENTER);
//        textView.setHeight(1);
//        textView2.setHeight(1);
//        textView1.setHeight(1);
//        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//        tr.addView(textView1);
//        tr.addView(textView2);
//        tr.addView(textView);
//        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        TableRow tr_head = new TableRow(this);
//        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setPadding(5, 5, 5, 5);
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView label_hello = new TextView(this);
        label_hello.setText("HELLO");
        label_hello.setTextColor(Color.WHITE);          // part2
//        label_hello.setPadding(5, 5, 5, 5);
        label_hello.setGravity(Gravity.CENTER);
        tr_head.addView(label_hello);// add the column to the table row here

        TextView label_android = new TextView(this);    // part3
        label_android.setText("ANDROID..!!"); // set the text for the header
        label_android.setTextColor(Color.WHITE); // set the color
        label_android.setGravity(Gravity.CENTER); // set the padding (if required)
        tr_head.addView(label_android); // add the column to the table row here

        TextView label_androidt = new TextView(this);    // part3
        label_androidt.setText("ANDROIDt..!!"); // set the text for the header
        label_androidt.setTextColor(Color.WHITE); // set the color
        label_androidt.setGravity(Gravity.CENTER); // set the padding (if required)
        tr_head.addView(label_androidt);
        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,                    //part4
                TableRow.LayoutParams.WRAP_CONTENT));


    }
}