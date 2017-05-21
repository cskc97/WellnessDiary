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

import java.util.List;

import apps.everythingforward.com.wellnessdiary.adapters.TherapistsAdapter;

import static android.view.View.GONE;

public class SearchTherapists extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBarCircularIndeterminate progressBarCircularIndeterminate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_therapists);
        recyclerView = (RecyclerView)findViewById(R.id.connectTherapistsRV);
        progressBarCircularIndeterminate=(ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);



    }

    @Override
    protected void onResume() {
        super.onResume();
        getTherapists();

    }



    private void getTherapists()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Utility.THERAPIST_CLASSNAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null) {
                    progressBarCircularIndeterminate.setVisibility(GONE);
                    TherapistsAdapter adapter = new TherapistsAdapter(objects);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    recyclerView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(SearchTherapists.this, e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
