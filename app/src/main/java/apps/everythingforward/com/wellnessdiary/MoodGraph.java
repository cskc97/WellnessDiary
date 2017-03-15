package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;

public class MoodGraph extends AppCompatActivity {

    LineChart chart;
    DiaryRecordsEntityManager diaryRecordsEntityManager;
    ArrayList<DiaryRecords> data;
    List<Entry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_graph);

        chart = (LineChart)findViewById(R.id.chart);
        diaryRecordsEntityManager=new DiaryRecordsEntityManager();

        data = (ArrayList<DiaryRecords>)diaryRecordsEntityManager.select().asList();

        entries= new ArrayList<Entry>();


    }


    @Override
    protected void onResume() {
        super.onResume();

        for(int counter=0;counter<data.size();counter++)
        {

            float yValue = Float.valueOf(data.get(counter).getDiaryEntrySentiment().trim()).floatValue();
            float xValue = (float)counter;
            entries.add(new Entry(xValue,yValue));


        }

        LineDataSet dataSet = new LineDataSet(entries, "Emtoion Graph"); // add entries to dataset
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
