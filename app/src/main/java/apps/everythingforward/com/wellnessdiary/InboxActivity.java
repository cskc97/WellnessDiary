package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import apps.everythingforward.com.wellnessdiary.adapters.MessageAdapter;

import static android.view.View.GONE;

public class InboxActivity extends AppCompatActivity {

    RecyclerView recyclerview;

    ProgressBarCircularIndeterminate progressBarCircularIndeterminate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);


        recyclerview = (RecyclerView)findViewById(R.id.inboxRV);
        progressBarCircularIndeterminate = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyMessages();

    }



    private void getMyMessages()
    {
        String userEmail = ParseUser.getCurrentUser().getEmail();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Utility.MESSAGE_CLASS);
        query.whereEqualTo(Utility.MESSAGES_TO,userEmail);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    progressBarCircularIndeterminate.setVisibility(GONE);
                    if(!objects.isEmpty())
                    {
                       MessageAdapter adapter = new MessageAdapter(objects);
                        recyclerview.setAdapter(adapter);
                        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                    }
                    else
                    {
                        Toast.makeText(InboxActivity.this, "You have no messages!", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }
}
