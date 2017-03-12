package apps.everythingforward.com.wellnessdiary;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.victor.loading.book.BookLoading;

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

        Picasso.with(getApplicationContext()).load(bgURL).fit().into(mainBG);



    }

}
