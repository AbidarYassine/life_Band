package com.example.lifeband.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeband.R;
import com.example.lifeband.db.ChildDb;
import com.example.lifeband.ui.adapter.MyAdapter;
import com.example.lifeband.utils.AdapterData;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class History extends AppCompatActivity {
    ListView listView_data;
    SearchView searchView;
    private static final String TAG = "History";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupView();
        searchView.setQueryHint("dd-mm-yyyy or hh:mm:ss");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ChildDb.getChildByGuarId(child -> {
            List<AdapterData> adapterDataList = new ArrayList<>();
            for (int i = 0; i < child.getHistory().size(); i++) {
                AdapterData adapterData = new AdapterData();
                adapterData.setTemp(child.getHistory().get(i).get("TEMP"));
                adapterData.setBPM(child.getHistory().get(i).get("BPM"));
                adapterData.setDate(child.getHistory().get(i).get("date"));
                adapterDataList.add(adapterData);
            }
            listView_data.setAdapter(new MyAdapter(adapterDataList, getApplicationContext()));
        }, uid);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ChildDb.getChildByGuarId(child -> {
                    List<AdapterData> adapterDataList = new ArrayList<>();
                    for (int i = 0; i < child.getHistory().size(); i++) {
                        if (child.getHistory().get(i).get("date").contains(query)) {
                            AdapterData adapterData = new AdapterData();
                            adapterData.setDate(child.getHistory().get(i).get("date"));
                            adapterData.setTemp(child.getHistory().get(i).get("TEMP"));
                            adapterData.setBPM(child.getHistory().get(i).get("BPM"));
                            adapterDataList.add(adapterData);
                        }
                    }
                    if (adapterDataList.size() > 0) {
                        listView_data.setAdapter(new MyAdapter(adapterDataList, getApplicationContext()));
                    } else {
                        Toast.makeText(History.this, "History not found!!", Toast.LENGTH_SHORT).show();
                    }

                }, uid);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    ChildDb.getChildByGuarId(child -> {
                        List<AdapterData> adapterDataList = new ArrayList<>();
                        for (int i = 0; i < child.getHistory().size(); i++) {
                            AdapterData adapterData = new AdapterData();
                            adapterData.setTemp(child.getHistory().get(i).get("TEMP"));
                            adapterData.setBPM(child.getHistory().get(i).get("BPM"));
                            adapterData.setDate(child.getHistory().get(i).get("date"));
                            adapterDataList.add(adapterData);
                        }
                        listView_data.setAdapter(new MyAdapter(adapterDataList, getApplicationContext()));
                    }, uid);
                }
                return true;
            }
        });
    }

    private void setupView() {
        listView_data = findViewById(R.id.listView_data);
        searchView = findViewById(R.id.searchView);
    }
}