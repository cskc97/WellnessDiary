package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String description = "Wellness is a mental health wellness suite. It allows the user to monitor their emotions, obtain " +
                "access to various articles about mental health, and connect with therapists. \n " +
                "Emotions are \"evaluated\" on a scale of 0 to 100 based on text that the user inputs. On the scale, 0 represents" +
                " a very negative emotion whereas 100 represents a very positive emotion. \n It is however important to understand that" +
                " what matters is not the absolute emotion/sentiment value but the relative values over a period of time. ";


        View aboutPage = new AboutPage(this)
                .setImage(R.mipmap.applogo100x100)
                .isRTL(false)

                .setDescription(description)


                .addEmail("everythingskc@gmail.com")
                .addFacebook("everything1forward")



                .create();
        setContentView(aboutPage);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
