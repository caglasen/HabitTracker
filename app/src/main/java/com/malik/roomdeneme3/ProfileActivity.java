package com.malik.roomdeneme3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    TextView name;
    TextView age;
    EditText editName;
    EditText editAge;
    ImageView img;
    public static final int GET_FROM_GALLERY = 3;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Age = "ageKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //initialize and assign Variable   --- burası bottom menu için kod başlangıcıdır
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.menu_profile);         // buraya tekrar bakarsın

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_habit:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;

                }
                return false;
            }
        });














        name = findViewById(R.id.nameText);
        age = findViewById(R.id.ageText);
        editName=findViewById(R.id.editText);
        editAge=findViewById(R.id.editText2);
        editName.setVisibility(View.INVISIBLE);
        editAge.setVisibility(View.INVISIBLE);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Name)) {
            name.setText(sharedpreferences.getString(Name, ""));
        }
        if (sharedpreferences.contains(Age)) {
            age.setText(sharedpreferences.getString(Age, ""));

        }

        img=findViewById(R.id.imageView);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setVisibility(View.VISIBLE);
                editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            editName.setVisibility(View.INVISIBLE);
                            name.setText(editName.getText());
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(Name,name.getText().toString());
                            editor.commit(); // commit changes
                            editName.clearComposingText();

                        }
                        return false;
                    }
                });


            }});

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAge.setVisibility(View.VISIBLE);
                editAge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            editAge.setVisibility(View.INVISIBLE);
                            age.setText(editAge.getText());

                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(Age,age.getText().toString());
                            editor.commit(); // commit changes
                            editAge.clearComposingText();

                        }
                        return false;
                    }
                });

            }});

    }
    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            img.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


