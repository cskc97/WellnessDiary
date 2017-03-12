package apps.everythingforward.com.wellnessdiary;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.victor.loading.book.BookLoading;

public class MainActivity extends AppCompatActivity {

    ImageView mainBG;


    public static String bgURL = "https://source.unsplash.com/1600x900/?nature";


    ResideMenu resideMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mainBG = (ImageView)findViewById(R.id.mainBG);

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.bg);
        resideMenu.attachToActivity(this);

        String titles[] = {"Home" ,"My Diary","Mood Graph" };
        int icon[] = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                }
            });
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }


        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);







    }

    @Override
    protected void onStart() {
        super.onStart();

        Picasso.with(getApplicationContext()).load(bgURL).fit().into(mainBG);

        // or ResideMenu.DIRECTION_RIGHT




    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

}
