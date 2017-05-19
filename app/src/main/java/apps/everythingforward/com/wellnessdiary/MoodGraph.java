package apps.everythingforward.com.wellnessdiary;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tapadoo.alerter.Alerter;

import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;
import apps.everythingforward.com.wellnessdiary.database.GraphFilePaths;
import apps.everythingforward.com.wellnessdiary.database.GraphFilePathsEntityManager;

public class MoodGraph extends AppCompatActivity {

    LineChart chart;
    DiaryRecordsEntityManager diaryRecordsEntityManager;
    ArrayList<DiaryRecords> data;
    List<Entry> entries;
    ButtonFlat saveGraphB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_graph);

        saveGraphB=(ButtonFlat)findViewById(R.id.saveGraphButton);
        chart = (LineChart)findViewById(R.id.chart);
        diaryRecordsEntityManager=new DiaryRecordsEntityManager();

        data = (ArrayList<DiaryRecords>)diaryRecordsEntityManager.select().asList();

        entries= new ArrayList<Entry>();


        saveGraphB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    Dexter.withActivity(MoodGraph.this)
                            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                                    String[] timeAndDate = EmotionAnalyze.getTimeAndDate();
                                    String saveString = timeAndDate[0]+"-"+timeAndDate[1];
                                    String saveStringReal = "WellnessDiary-"+saveString+".jpg";
                                    saveStringReal = URLEncoder.encode(saveStringReal);
                                    chart.saveToGallery(saveStringReal,85);

                                    GraphFilePathsEntityManager graphFilePathsEntityManager = new GraphFilePathsEntityManager();
                                    graphFilePathsEntityManager.add(new GraphFilePaths(saveStringReal));




                                    Alerter.create(MoodGraph.this)
                                            .setTitle("Graph Saved!")
                                            .setText("Revisit your mood graph to see it appear again!")
                                            .setBackgroundColor(R.color.positivemood)
                                            .setIcon(R.drawable.ic_add_alert_black_24dp)
                                            .show();


                                    onResume();
                                }
                                @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                                @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                            }).check();





            }
        });


    }


    @Override
    protected void onResume() {
        //Ctrl+Shift+D to promp the exynap dialog


        super.onResume();

        for(int counter=0;counter<data.size();counter++)
        {

            float yValue = Float.valueOf(data.get(counter).getDiaryEntrySentiment().trim()).floatValue();
            float xValue = (float)counter;
            entries.add(new Entry(xValue,yValue));


        }

        LineDataSet dataSet = new LineDataSet(entries, "Emotion Graph"); // add entries to dataset
        dataSet.setColor(getResources().getColor(R.color.colorAccent));

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        startActivity(new Intent(MoodGraph.this,MainActivity.class));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        entries.clear();
        entries=null;
        chart=null;
        data.clear();
        data=null;

    }
}
