package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView mainBG;

    public static String bgURL = "https://source.unsplash.com/1600x900/?nature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBG = (ImageView)findViewById(R.id.mainBG);




    }

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(getApplicationContext()).load(bgURL).into(mainBG);
    }
}
