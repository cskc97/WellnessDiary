package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gelitenight.waveview.library.WaveView;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;
import apps.everythingforward.com.wellnessdiary.helpers.WaveHelper;

public class EmotionAnalyze extends AppCompatActivity {

    WaveView waveView;
    private int mBorderColor = Color.parseColor("#44FFFFFF");
    double waterLevel = 0.65;
    private int mBorderWidth = 10;
    WaveHelper waveHelper;

    TextView emotionTV;

    float obtainValue;

    String diaryEntryText;

    DiaryRecordsEntityManager diaryRecordsEntityManager;

    ButtonFlat saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_analyze);


        obtainValue = getIntent().getFloatExtra(Intent.EXTRA_TEXT,0f);
        diaryEntryText = getIntent().getStringExtra("diaryentrystring");


        diaryRecordsEntityManager = new DiaryRecordsEntityManager();

        waveView = (WaveView)findViewById(R.id.wave);
        waveHelper = new WaveHelper(waveView,obtainValue);
        waveView.setShapeType(WaveView.ShapeType.CIRCLE);

        emotionTV = (TextView)findViewById(R.id.textView2);

        saveButton = (ButtonFlat)findViewById(R.id.saveToDiaryButton);

        waveView.setWaveColor(
                Color.parseColor("#28f16d7a"),
                Color.parseColor("#3cf16d7a"));
        mBorderColor = Color.parseColor("#44f16d7a");
        waveView.setBorder(mBorderWidth, mBorderColor);

        Log.e("EA",String.valueOf(obtainValue*100));

        final String setString = String.valueOf(Math.round(obtainValue*100));
        emotionTV.setText(setString+"%");


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateAndTime[] = getTimeAndDate();
                diaryRecordsEntityManager.add(new DiaryRecords(diaryEntryText,setString,dateAndTime[1],dateAndTime[0]));

                 Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

                Intent intent = new Intent(EmotionAnalyze.this,DisplayDiary.class);
                startActivity(intent);


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    protected void onResume() {
        super.onResume();

        waveHelper.start();

        getTimeAndDate();




    }

    @Override
    protected void onPause() {
        super.onPause();
        waveHelper.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private String[] getTimeAndDate()
    {
        Time time = new Time(System.currentTimeMillis());
        Log.e("Current Time and date", time.toString());
        String timeVal = time.toString();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

        return new String[]{timeVal,date};

    }


}
