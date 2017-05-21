package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedItemActivity extends AppCompatActivity {

    Number fID;
    String content;
    String pictureURL;

    ImageView itemImage;
    TextView itemContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item);

        itemImage=(ImageView)findViewById(R.id.itemImage);
        itemContent=(TextView)findViewById(R.id.itemContent);

        fID = getIntent().getIntExtra(Intent.EXTRA_TEXT,0);


        getContent();








    }


    private void getContent()
    {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FeedItem");
        query.whereEqualTo(Utility.FEED_ITEMID,fID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if(e==null) {

                    content = objects.get(0).getString(Utility.FEED_ARTICLECONTENT);
                    pictureURL = objects.get(0).getString(Utility.FEED_ARTICLEIMAGE);
                //    Toast.makeText(FeedItemActivity.this, content, Toast.LENGTH_SHORT).show();
                    Picasso.with(FeedItemActivity.this).load(pictureURL).fit().centerCrop().into(itemImage);
                    itemContent.setText(content);
                }




            }
        });









    }
}
