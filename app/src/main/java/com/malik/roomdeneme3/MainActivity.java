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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private HobbyViewModel hobbyViewModel;
    private CheckmarkViewModel checkmarkViewModel;
    //private CheckmarkViewModel checkmarkViewModel;
    private List<HobbyCheckmark> hobbyList;

    public static final int ADD_HOBBY_REQUEST = 1;
    public static final int EDIT_HOBBY_REQUEST = 2;

    int colorold;

    int checkmarkAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize and assign Variable   --- burası bottom menu için kod başlangıcıdır        // bottom kod başlangıcı
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.menu_habit);         // buraya tekrar bakarsın

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_habit:
                        return true;
                    case R.id.menu_report:
                        startActivity(new Intent(getApplicationContext(), ReportingMainActivity.class));
                        overridePendingTransition(0,0);
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



        // bottom kod bitişi

        FloatingActionButton buttonAddHobby = findViewById(R.id.button_add_hobby);
        buttonAddHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddHobbyActivity.class);
                startActivityForResult(intent, ADD_HOBBY_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final HobbyAdapter adapter = new HobbyAdapter();
        recyclerView.setAdapter(adapter);



        hobbyViewModel = ViewModelProviders.of(this).get(HobbyViewModel.class);
        checkmarkViewModel = ViewModelProviders.of(this).get(CheckmarkViewModel.class);
        hobbyViewModel.getHobbyandCheckmarks().observe(this, new Observer<List<HobbyCheckmark>>() {
            @Override
            public void onChanged(List<HobbyCheckmark> hobbies) {
                hobbyList = hobbies;
                adapter.setHobbies(hobbyList);
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
                int pos = viewHolder.getAdapterPosition();
                hobbyViewModel.delete(adapter.getHobbyAt(viewHolder.getAdapterPosition()));
                hobbyList.remove(pos);
                adapter.notifyDataSetChanged();
                //hobbyViewModel.notify();
                //hobbyList = hobbyViewModel.getHobbyandCheckmarks();
                //adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Hobby deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new HobbyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HobbyCheckmark hobby) {
                Intent intent = new Intent(MainActivity.this, AddEditHobbyActivity.class);
                intent.putExtra(AddEditHobbyActivity.EXTRA_ID, hobby.getId());
                intent.putExtra(AddEditHobbyActivity.EXTRA_TITLE, hobby.getTitle());
                intent.putExtra(AddEditHobbyActivity.EXTRA_TIME, hobby.time);

                //intent.putExtra(AddEditHobbyActivity.EXTRA_CLR, hobby.getClr());
                colorold=hobby.clr; //????
//......

                intent.putExtra(AddEditHobbyActivity.EXTRA_R, hobby.routine);
                intent.putExtra(AddEditHobbyActivity.EXTRA_P, hobby.positive);
                intent.putExtra(AddEditHobbyActivity.EXTRA_M, hobby.mon);
                intent.putExtra(AddEditHobbyActivity.EXTRA_TU, hobby.tue);
                intent.putExtra(AddEditHobbyActivity.EXTRA_W, hobby.wed);
                intent.putExtra(AddEditHobbyActivity.EXTRA_TS, hobby.trs);
                intent.putExtra(AddEditHobbyActivity.EXTRA_F, hobby.fri);
                intent.putExtra(AddEditHobbyActivity.EXTRA_ST, hobby.sat);
                intent.putExtra(AddEditHobbyActivity.EXTRA_SN, hobby.sun);

                intent.putExtra(AddEditHobbyActivity.EXTRA_DESCRIPTION, hobby.getDescription());
                intent.putExtra(AddEditHobbyActivity.EXTRA_PRIORITY, hobby.priority);
                startActivityForResult(intent, EDIT_HOBBY_REQUEST);
            }
        });

        adapter.setOnAddCheckmarkButtonClickListener(new HobbyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final HobbyCheckmark hobby) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add checkmark");

                // Set up the input
                final EditText input = new EditText(MainActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkmarkAmount = Integer.parseInt(input.getText().toString());
                        // database e gerekli miktarları ekle
                        Checkmark cm = new Checkmark();
                        cm.hobby_id = hobby.id;
                        cm.date  = new Date().toString();
                        cm.numberOfMinutesDone = checkmarkAmount;
                        checkmarkViewModel.insert(cm);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_HOBBY_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(AddHobbyActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddHobbyActivity.EXTRA_DESCRIPTON);
            String times = data.getStringExtra(AddHobbyActivity.EXTRA_TIME);
            int color=data.getIntExtra(AddHobbyActivity.EXTRA_CLR,0);

            //......
            boolean routine =data.getBooleanExtra(AddHobbyActivity.EXTRA_R,false);
            boolean positive =data.getBooleanExtra(AddHobbyActivity.EXTRA_P,false);
            boolean mon = data.getBooleanExtra(AddHobbyActivity.EXTRA_M,false);
            boolean tue = data.getBooleanExtra(AddHobbyActivity.EXTRA_TU,false);
            boolean wed = data.getBooleanExtra(AddHobbyActivity.EXTRA_W,false);
            boolean trs = data.getBooleanExtra(AddHobbyActivity.EXTRA_TS,false);
            boolean fri = data.getBooleanExtra(AddHobbyActivity.EXTRA_F,false);
            boolean sat = data.getBooleanExtra(AddHobbyActivity.EXTRA_ST,false);
            boolean sun = data.getBooleanExtra(AddHobbyActivity.EXTRA_SN,false);
            //  int clr = Integer.parseInt(data.getStringExtra(AddHobbyActivity.EXTRA_CLR));
            //String date = data.getStringExtra(AddHobbyActivity.EXTRA_DATE);
            //-------


            int priority = data.getIntExtra(AddHobbyActivity.EXTRA_PRIORITY, 1);
            //updated ....
            if(color==0){
                color= Color.BLACK;
            }
            Hobby hobby = new Hobby(title, description, times, priority,routine,positive,mon,tue,wed,trs,fri,sat,sun,0,color);
            hobbyViewModel.insert(hobby);

            Toast.makeText(this, "Hobby Saved", Toast.LENGTH_LONG).show();

        }
        else if (requestCode == EDIT_HOBBY_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditHobbyActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Hobby can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditHobbyActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditHobbyActivity.EXTRA_DESCRIPTION);
            String times = data.getStringExtra(AddEditHobbyActivity.EXTRA_TIME);
            int color=data.getIntExtra(AddEditHobbyActivity.EXTRA_CLR,0);
            if(color==0){
                color=colorold;
            }
            //......
            boolean routine =data.getBooleanExtra(AddEditHobbyActivity.EXTRA_R,false);
            boolean positive =data.getBooleanExtra(AddEditHobbyActivity.EXTRA_P,false);
            boolean mon = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_M,false);
            boolean tue = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_TU,false);
            boolean wed = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_W,false);
            boolean trs = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_TS,false);
            boolean fri = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_F,false);
            boolean sat = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_ST,false);
            boolean sun = data.getBooleanExtra(AddEditHobbyActivity.EXTRA_SN,false);
            //     int clr = Integer.parseInt(data.getStringExtra(AddHobbyActivity.EXTRA_CLR));
            //       String date = data.getStringExtra(AddHobbyActivity.EXTRA_DATE);
            //-------

            int priority = data.getIntExtra(AddEditHobbyActivity.EXTRA_PRIORITY, 1);

            Hobby hobby = new Hobby(title, description, times, priority,routine,positive,mon,tue,wed,trs,fri,sat,sun,0,color);
            hobby.setId(id);
            hobbyViewModel.update(hobby);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hobby not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                hobbyViewModel.deleteAllHobbies();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
