package apps.everythingforward.com.wellnessdiary;

import android.app.Application;

import fr.xebia.android.freezer.Freezer;

/**
 * Created by santh on 3/14/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Freezer.onCreate(this);


    }
}
