package com.malik.roomdeneme3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReportingScreen extends AppCompatActivity {

    public static final int EXTRA_DESCRIPTION = 1;
    public static final String EXTRA_TITLE = "2" ;
    public static final int EXTRA_ID = 3;

    private HobbyViewModel hobbyViewModel;
    private CheckmarkViewModel checkmarkViewModel;
    //private CheckmarkViewModel checkmarkViewModel;
    private List<HobbyCheckmark> hobbyList;
    public static Hobby hobby;


    HobbyCheckmark hc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportingscreen);





        checkmarkViewModel = ViewModelProviders.of(this).get(CheckmarkViewModel.class);

        Intent intent = getIntent();
        int hobbyId = intent.getIntExtra("id",-1);
        String hobbyName = intent.getStringExtra("title");
        String hobbytime = intent.getStringExtra("time");
        int hobbyColor = intent.getIntExtra("color",-1);
        boolean hobbyPositive = intent.getBooleanExtra("isPositive",true);
        hc = new HobbyCheckmark();
        hc.id = hobbyId;
        hc.time=String.valueOf(hobbytime);
        hc.title=String.valueOf(hobbyName);
        hc.clr=hobbyColor;
        hc.positive=hobbyPositive;


        final TextView awards=findViewById(R.id.awardTextView);
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final TextView title = (TextView) findViewById(R.id.textView_habit_name);
        final TextView goal = (TextView) findViewById(R.id.textView_daily_goal);
        final TextView goalAverage = (TextView) findViewById(R.id.textView_isGoalAchievedInAverage);
        final Button button = (Button) findViewById(R.id.button4);



        /*
        TextView contactUs = new TextView(this);
        contactUs.setText(hc.title);
        contactUs.setTextSize(25);
        */

        /*
        ConstraintLayout.LayoutParams clpcontactUs = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        contactUs.setLayoutParams(clpcontactUs);

        constraintLayout.addView(contactUs);
        */



        checkmarkViewModel.getHobbyCheckmarks(hc).observe(this, new Observer<List<Checkmark>>() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Checkmark> checkmarks) {
                int aaaa = 5;
                DateFormat fmt1 = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                Date[] dates=new Date[checkmarks.size()];
                DataPoint[] dataPoints = new DataPoint[checkmarks.size()];
                ArrayList<Integer> arrayListMinutes = new ArrayList<Integer>();
                /*
                double intAverage = arrayListMinutes.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);
                */
                double totalAverage=0;

                int numOfCheckmarks=0;
                for (int i = 0; i <checkmarks.size() ; i++) {

                    try {
                        Date date=new Date(String.valueOf(checkmarks.get(i).getDate()));

                        int dailyMinutesDone=checkmarks.get(i).numberOfMinutesDone;
                        arrayListMinutes.add(dailyMinutesDone);
                        totalAverage+=dailyMinutesDone;

                        dates[i]=checkmarks.get(i).getDate();
                        dataPoints[i]=new DataPoint(dates[i],dailyMinutesDone);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    numOfCheckmarks=checkmarks.size();
                }
                int totalTime=(int)totalAverage;
                totalAverage/=numOfCheckmarks;


                title.setText(hc.title);
                title.setTextColor(hc.clr);

                goal.setText(hc.time);

                //Awards

                if(hc.positive){
                    if(totalTime<60*3){
                        awards.setText(R.string.achievement_nothing);
                    }
                    if(totalTime>=60*3){
                        awards.setText(R.string.achievement_3_hour);
                    }
                    if(totalTime>=24*60){
                        awards.setText(R.string.achievement_1_day);
                    }
                    if(totalTime>=3*24*60){
                        awards.setText(R.string.achievement_3_day);
                    }
                    if(totalTime>=7*24*60){
                        awards.setText(R.string.achievement_1_week);
                    }
                    if(totalTime>=10000*60){
                        awards.setText(R.string.achievement_10000_hour);
                    }
                }
                else if(!hc.positive){
                    awards.setText("");
                }

                if(hc.positive){
                    if(!hc.time.equals("")){
                        int dailyTime= Integer.parseInt(hc.time);
                        if(totalAverage>dailyTime){
                            goalAverage.setText("Above average!!");
                        }else if(totalAverage==dailyTime){
                            goalAverage.setText("Average!");
                        }else if(totalAverage<dailyTime){
                            goalAverage.setText("Below average :(");
                        }
                    }
                }else if(!hc.positive){
                    if(!hc.time.equals("")){
                        int dailyTime= Integer.parseInt(hc.time);
                        if(totalAverage>dailyTime){
                            goalAverage.setText("Above average :(");
                        }else if(totalAverage==dailyTime){
                            goalAverage.setText("Average!");
                        }else if(totalAverage<dailyTime){
                            goalAverage.setText("Below average!!");
                        }
                    }
                }




                LineGraphSeries<DataPoint> series;
                series = new LineGraphSeries<DataPoint>(dataPoints);
                //series =new LineGraphSeries<DataPoint>();
                graph.addSeries(series);
                series.setTitle("Daily minutes done");
                series.setColor(Color.GREEN);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(R.color.colorPrimary);
                paint.setStrokeWidth(10);
                paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
                series.setCustomPaint(paint);

                // set manual x bounds to have nice steps
                if(dates.length-1>=0){
                    graph.getViewport().setMinX(dates[0].getTime());
                    graph.getViewport().setMaxX(dates[dates.length-1].getTime());
                }

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(0);
                if(arrayListMinutes.size()!=0){
                    graph.getViewport().setMaxY(Collections.max(arrayListMinutes) +/*0.10*/Collections.max(arrayListMinutes));

                    graph.addSeries(series);
                }


                button.setBackgroundColor(hc.clr);

                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setNumVerticalLabels(6);
                graph.getGridLabelRenderer().setNumHorizontalLabels(numOfCheckmarks);


                graph.getGridLabelRenderer().setHumanRounding(false);

                graph.setTitle("Daily minutes done");

            }
        });




        /*
        hobbyViewModel.getAllHobbies();
        checkmarkViewModel.getHobbyCheckmarks((Hobby) getIntent().getSerializableExtra("Hobby"));

        hobby=(Hobby) getIntent().getSerializableExtra("Hobby");

        int id = Integer.valueOf(intent.getStringExtra("EXTRA_ID"));
        String title = intent.getStringExtra("EXTRA_TITLE");

        System.out.println(id);
        System.out.println(title);
        System.out.println(hobby.getDescription());
*/




/*
        DateFormat fmt1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        Date d1 = null,d2 = null,d3 = null,d4 = null,d5 = null,d6 = null,d7 = null,
                d8 = null,d9 = null,d10 = null,d11 = null, d12 = null;

        try {
            d1 = fmt1.parse("January 1,  2018");
            d2 = fmt1.parse("January 2,  2018");
            d3 = fmt1.parse("January 3,  2018");
            d4 = fmt1.parse("January 4,  2018");
            d5 = fmt1.parse("January 5,  2018");
            d6 = fmt1.parse("January 6,  2018");
            d7 = fmt1.parse("July 1,  2018");
            d8 = fmt1.parse("August 1,  2018");
            d9 = fmt1.parse("September 1,  2018");
            d10 = fmt1.parse("October 1,  2018");
            d11 = fmt1.parse("November 1,  2018");
            d12 = fmt1.parse("December 1,  2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LineGraphSeries<DataPoint>     series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 10),
                new DataPoint(d2, 23.00),
                new DataPoint(d3, 20.00),
                new DataPoint(d4, 15.00),
                new DataPoint(d5, 17.00),
                new DataPoint(d6, 18.00),

        });

 */
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(5); // only 4 because of the space







    }
}
