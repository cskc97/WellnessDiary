package apps.everythingforward.com.wellnessdiary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.squareup.haha.perflib.Main;
import com.squareup.picasso.Picasso;

import apps.everythingforward.com.wellnessdiary.services.SentimentAnalysisIntentService;

public class MainActivity extends AppCompatActivity {

    ImageView mainBG;


    public static String bgURL = "https://source.unsplash.com/1600x900/?nature";

    ButtonFlat buttonFlat;


    ResideMenu resideMenu;


    IntentFilter intentFilter;
    BroadcastReceiver receiver;

    EditText diaryText;
    String sentimentText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        diaryText = (EditText)findViewById(R.id.feelingsET);



        mainBG = (ImageView)findViewById(R.id.mainBG);

       resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.residemenubgblur);
        resideMenu.attachToActivity(this);

        buttonFlat = (ButtonFlat)findViewById(R.id.saveToDiaryButton);

        String titles[] = {"Home" ,"My Diary","Mood Graph" };
        int icon[] = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};

        ResideMenuItem itemMyDiary = new ResideMenuItem(this,icon[1],titles[1]);
        itemMyDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DisplayDiary.class));
            }
        });




        ResideMenuItem itemMoodGraph = new ResideMenuItem(this,icon[2],titles[2]);
        itemMoodGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MoodGraph.class));
            }
        });



            resideMenu.addMenuItem(itemMyDiary,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
            resideMenu.addMenuItem(itemMoodGraph,ResideMenu.DIRECTION_LEFT);








        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                double dSentimentValue = intent.getDoubleExtra("value",0f);
                float fSentimentValue = (float)dSentimentValue;



                Intent intent1 = new Intent(MainActivity.this,EmotionAnalyze.class);
                intent1.putExtra(Intent.EXTRA_TEXT,fSentimentValue);
                intent1.putExtra("diaryentrystring",sentimentText);
                startActivity(intent1);


            }
        };

        intentFilter = new IntentFilter(SentimentAnalysisIntentService.ACTION_FINISHEDTEXT);




        buttonFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!diaryText.getText().toString().trim().isEmpty()) {
                    sentimentText = diaryText.getText().toString();
                }

                if(sentimentText!=null) {
                    Intent intent = new Intent(MainActivity.this, SentimentAnalysisIntentService.class);
                    intent.putExtra(Intent.EXTRA_TEXT, sentimentText);
                    startService(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill in your entry!", Toast.LENGTH_SHORT).show();
                }





            }
        });







    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Picasso.with(getApplicationContext()).load(bgURL).fit().into(mainBG);

        // or ResideMenu.DIRECTION_RIGHT




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);






        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

}
