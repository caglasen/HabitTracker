package com.malik.roomdeneme3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddHobbyActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    public static final String EXTRA_TITLE = "com.malik.roomdeneme3.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTON = "com.malik.roomdeneme3.EXTRA_DESCRIPTON";

    public static final String EXTRA_TIME = "com.malik.roomdeneme3.EXTRA_TIME";

    public static final String EXTRA_PRIORITY = "com.malik.roomdeneme3.EXTRA_PRIORITY";
    public static final String EXTRA_CLR = "com.malik.roomdeneme3.EXTRA_CLR";


    //start
    public static final String EXTRA_R="com.malik.roomdeneme3.EXTRA_R";
    public static final String EXTRA_P="com.malik.roomdeneme3.EXTRA_P";
    public static final String EXTRA_M="com.malik.roomdeneme3.EXTRA_M";
    public static final String EXTRA_TU="com.malik.roomdeneme3.EXTRA_TU";
    public static final String EXTRA_W="com.malik.roomdeneme3.EXTRA_W";
    public static final String EXTRA_TS="com.malik.roomdeneme3.EXTRA_TS";
    public static final String EXTRA_F="com.malik.roomdeneme3.EXTRA_F";
    public static final String EXTRA_ST="com.malik.roomdeneme3.EXTRA_ST";
    public static final String EXTRA_SN="com.malik.roomdeneme3.EXTRA_SN";
    //public static final String EXTRA_CLR="com.malik.roomdeneme3.EXTRA_CLR";
   // public static final String EXTRA_DATE="com.malik.roomdeneme3.EXTRA_DATE";
    //part end

    Button clrB;
    int defclr;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText time;
    private NumberPicker numberPickerPriority;
    //heyyooo
    private RadioButton routine;
    private RadioButton positive;
    private CheckBox mon;
    private CheckBox tue;
    private CheckBox wed;
    private CheckBox trs;
    private CheckBox fri;
    private CheckBox sat;
    private CheckBox sun;
   // private View clr;
 //   private String date;
    //endooo
    RecyclerView rw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hobby);

        editTextTitle = findViewById(R.id.NameText);
        editTextDescription = findViewById(R.id.descText);
        time=findViewById(R.id.reptime);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        //aaooo
        routine=findViewById(R.id.routine);
        positive=findViewById(R.id.positive);
        mon=findViewById(R.id.mon);
        tue=findViewById(R.id.tue);
        wed=findViewById(R.id.wed);
        trs=findViewById(R.id.trs);
        fri=findViewById(R.id.fri);
        sat=findViewById(R.id.sat);
        sun=findViewById(R.id.sun);
  //      clr=findViewById(( R.id.ambilwarna_newColor));
//pppp
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Hobby");

        clrB=(Button) findViewById(R.id.clrBtn);
       // defclr= ContextCompat.getColor(this, R.color.colorPrimary);
        clrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorScreen(false);
            }
        });

        rw=findViewById(R.id.recycler_view);

    }
    private void colorScreen(boolean AlphaSupport){
        AmbilWarnaDialog awd=new AmbilWarnaDialog(this, defclr, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(AddHobbyActivity.this, "Color picker closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                String hexColor =String.format("#%06X", (0xFFFFFF & color));
                defclr=Color.parseColor(hexColor);
            }
        });
        awd.show();

    }

    private void saveHobby(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String times=time.getText().toString();
        int priority = numberPickerPriority.getValue();
        int color=defclr;
        //.....
        boolean  rot=routine.isChecked();
        boolean  pos=positive.isChecked();
        boolean  m=mon.isChecked();
        boolean  tu=tue.isChecked();
        boolean  w=wed.isChecked();
        boolean  ts=trs.isChecked();
        boolean  f=fri.isChecked();
        boolean  st=sat.isChecked();
        boolean  sn=sun.isChecked();
       // int  cl=Integer.parseInt(clr.toString());
        //String dat=date;
        //-----

             if(title.trim().isEmpty() || description.trim().isEmpty()){
                 Toast.makeText(this,"Please insert a title and description",Toast.LENGTH_LONG).show();
                 return;
             }


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTON,description);
        data.putExtra(EXTRA_TIME,times);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_CLR,color);

        //.......
        data.putExtra(EXTRA_R,rot);
        data.putExtra(EXTRA_P,pos);
        data.putExtra(EXTRA_M,m);
        data.putExtra(EXTRA_TU,tu);
        data.putExtra(EXTRA_W,w);
        data.putExtra(EXTRA_TS,ts);
        data.putExtra(EXTRA_F,f);
        data.putExtra(EXTRA_ST,st);
        data.putExtra(EXTRA_SN,sn);
        ///data.putExtra(EXTRA_CLR,cl);
        ///data.putExtra(EXTRA_DATE,dat);
        //------

        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_hobby_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_hobby:
                saveHobby();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    //    date=hourOfDay+":"+minute;//*****
    }
}
