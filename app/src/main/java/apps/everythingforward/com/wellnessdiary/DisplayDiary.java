package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;

import apps.everythingforward.com.wellnessdiary.adapters.DiaryAdapter;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayDiary extends AppCompatActivity {


    RecyclerView recyclerView;
    DiaryAdapter diaryAdapter;

    ShakeDetector.ShakeListener shakeListener;

    ButtonFlat buttonFlat;

    ButtonFloatSmall buttonFloatSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_diary);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        Sensey.getInstance().init(getApplicationContext());

        buttonFlat = (ButtonFlat)findViewById(R.id.graphButton);

        



        buttonFloatSmall = (ButtonFloatSmall)findViewById(R.id.buttonFloatSmall);

        if(buttonFloatSmall!=null)
        {
            buttonFloatSmall.setDrawableIcon(getResources().getDrawable(R.drawable.ic_unfold_more_black_24dp));
        }

        buttonFloatSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayDiary.this,CalendarPick.class));
            }
        });

        shakeListener = new ShakeDetector.ShakeListener() {
            @Override
            public void onShakeDetected() {

            }

            @Override
            public void onShakeStopped() {
                new SweetAlertDialog(DisplayDiary.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Delete All?")
                        .setContentText("Are you Sure?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                diaryAdapter.removeAll();
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();



            }
        };


        buttonFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayDiary.this,MoodGraph.class));
                finish();
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryAdapter = new DiaryAdapter();

        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        Sensey.getInstance().startShakeDetection(shakeListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Sensey.getInstance().stopShakeDetection(shakeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sensey.getInstance().stop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
