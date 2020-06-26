package com.malik.roomdeneme3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReportingMainActivity extends AppCompatActivity implements Serializable {
    private HobbyViewModel hobbyViewModel;

    public static final int ADD_HOBBY_REQUEST = 1;
    public static final int VIEW_REPORTING_SCREEN = 2;

    private CheckmarkViewModel checkmarkViewModel;


    private List<HobbyCheckmark> hobbyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportingmain);






        //initialize and assign Variable   --- burası bottom menu için kod başlangıcıdır
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.menu_report);         // buraya tekrar bakarsın

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_habit:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_report:
                        return true;
                    case R.id.menu_note:
                        startActivity(new Intent(getApplicationContext(), NoteActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_alarm:
                        startActivity(new Intent(getApplicationContext(), AlarmActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });











        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final HobbyAdapterReporting adapter = new HobbyAdapterReporting();
        recyclerView.setAdapter(adapter);


        hobbyViewModel = ViewModelProviders.of(this).get(HobbyViewModel.class);
        hobbyViewModel.getHobbyandCheckmarks().observe(this, new Observer<List<HobbyCheckmark>>() {
            @Override
            public void onChanged(List<HobbyCheckmark> hobbies) {

                adapter.setHobbies(hobbies);
            }
        });

        checkmarkViewModel = ViewModelProviders.of(this).get(CheckmarkViewModel.class);


        checkmarkViewModel = ViewModelProviders.of(this).get(CheckmarkViewModel.class);

        adapter.setOnItemClickListener(new HobbyAdapterReporting.OnItemClickListener() {
            @Override
            public void onItemClick(final HobbyCheckmark hobby) {



                Intent intent = new Intent(ReportingMainActivity.this, ReportingScreen.class);
                //intent.putExtra("Hobby", (Serializable) hobby);
                intent.putExtra("id", hobby.getId());
                intent.putExtra("title", hobby.getTitle());
                intent.putExtra("time", hobby.time);
                intent.putExtra("color", hobby.clr);
                intent.putExtra("isPositive", hobby.positive);


                intent.putExtra(String.valueOf(ReportingScreen.EXTRA_DESCRIPTION), hobby.getDescription());
                startActivityForResult(intent, VIEW_REPORTING_SCREEN);


            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerView);


    }





}