package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import apps.everythingforward.com.wellnessdiary.adapters.FeedAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {

    RecyclerView feedRecyclerView;
    FeedAdapter feedAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        feedRecyclerView=(RecyclerView)findViewById(R.id.feedRV);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getFeed();


    }

    public void getFeed()
    {

        /*TODO: Optimize feed:
        Make one ping to the server in onCreate and store all the articles in a SQLite Database until
        next time where you delete and store again.
         */

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FeedItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if(e==null)
                {

                    feedAdapter = new FeedAdapter(objects);
                    if(feedAdapter!=null)
                    {
                        Toast.makeText(FeedActivity.this, "Got it!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(FeedActivity.this, "Adapter is null", Toast.LENGTH_SHORT).show();
                    }



                }

            //    Toast.makeText(FeedActivity.this, objects.get(0).getString(Utility.FEED_ARTICLETITLE), Toast.LENGTH_SHORT).show();

                if(feedRecyclerView!=null) {
                    feedRecyclerView.setAdapter(feedAdapter);
                    feedRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
                else
                {
                    Toast.makeText(FeedActivity.this, "Recycler View is Null", Toast.LENGTH_SHORT).show();
                }



            }
        });











    }
}
