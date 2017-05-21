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
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.squareup.haha.perflib.Main;
import com.squareup.picasso.Picasso;

import apps.everythingforward.com.wellnessdiary.services.SentimentAnalysisIntentService;

public class MainActivity extends AppCompatActivity {

    ImageView mainBG;


    public static String bgURL = "https://source.unsplash.com/1600x900/?nature";

    ButtonFlat buttonFlat;
    ButtonFlat manualEntry;


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
        manualEntry = (ButtonFlat)findViewById(R.id.PickManually);



        if(ParseUser.getCurrentUser()==null) {
            ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
            builder.setAppLogo(R.drawable.applogo100x100);


            startActivityForResult(builder.build(), 0);
        }



            Picasso.with(getApplicationContext()).load(bgURL).fit().into(mainBG);

            manualEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), ManualEntry.class));
                }
            });


            resideMenu = new ResideMenu(this);
            resideMenu.setBackground(R.drawable.background_blue);
            resideMenu.attachToActivity(this);

            buttonFlat = (ButtonFlat) findViewById(R.id.saveToDiaryButton);

            String titles[] = {"Home", "My Diary", "Mood Graph", "Statistics", "Feed", "Find", "Connect", "Inbox"};
            //   int icon[] = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};

            int icon[] = {R.mipmap.mydiary, R.mipmap.graph, R.mipmap.stats, R.mipmap.feed, R.mipmap.connect,
                    R.mipmap.connections, R.mipmap.inbox};

            ResideMenuItem itemMyDiary = new ResideMenuItem(this, icon[0], titles[1]);
            itemMyDiary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, DisplayDiary.class));
                }
            });


            ResideMenuItem itemMoodGraph = new ResideMenuItem(this, icon[1], titles[2]);
            itemMoodGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, MoodGraph.class));
                }
            });

            ResideMenuItem itemStats = new ResideMenuItem(this, icon[2], titles[3]);
            itemStats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                }
            });

            ResideMenuItem itemFeed = new ResideMenuItem(this, icon[3], titles[4]);
            itemFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                }
            });

            ResideMenuItem connectTherapists = new ResideMenuItem(this, icon[4], titles[5]);
            connectTherapists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), SearchTherapists.class));
                }
            });


            ResideMenuItem yourConnections = new ResideMenuItem(this, icon[5], titles[6]);
            yourConnections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), YourConnections.class));
                }
            });

            ResideMenuItem yourInbox = new ResideMenuItem(this, icon[6], titles[7]);
            yourInbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), InboxActivity.class));

                }
            });


            resideMenu.addMenuItem(itemMyDiary, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
            resideMenu.addMenuItem(itemMoodGraph, ResideMenu.DIRECTION_LEFT);
            resideMenu.addMenuItem(itemStats, ResideMenu.DIRECTION_LEFT);
            resideMenu.addMenuItem(itemFeed, ResideMenu.DIRECTION_RIGHT);
            resideMenu.addMenuItem(connectTherapists, ResideMenu.DIRECTION_RIGHT);
            resideMenu.addMenuItem(yourConnections, ResideMenu.DIRECTION_RIGHT);
            resideMenu.addMenuItem(yourInbox, ResideMenu.DIRECTION_RIGHT);


            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {


                    double dSentimentValue = intent.getDoubleExtra("value", 0f);
                    float fSentimentValue = (float) dSentimentValue;


                    Intent intent1 = new Intent(MainActivity.this, EmotionAnalyze.class);
                    intent1.putExtra(Intent.EXTRA_TEXT, fSentimentValue);
                    intent1.putExtra("diaryentrystring", sentimentText);
                    startActivity(intent1);


                }
            };

            intentFilter = new IntentFilter(SentimentAnalysisIntentService.ACTION_FINISHEDTEXT);


            buttonFlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (!diaryText.getText().toString().trim().isEmpty()) {
                        sentimentText = diaryText.getText().toString();
                    }

                    if (sentimentText != null) {
                        Intent intent = new Intent(MainActivity.this, SentimentAnalysisIntentService.class);
                        intent.putExtra(Intent.EXTRA_TEXT, sentimentText);
                        startService(intent);
                    } else {
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


        // or ResideMenu.DIRECTION_RIGHT




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       switch(item.getItemId())
       {
           case R.id.logout_settings:
           {
                ParseUser.logOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
               break;



           }
           case R.id.about_app:
           {
               startActivity(new Intent(getApplicationContext(),AboutApplication.class));
               break;



           }
       }




        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

}
