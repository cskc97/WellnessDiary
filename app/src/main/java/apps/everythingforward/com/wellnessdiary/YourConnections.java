package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnessdiary.adapters.TherapistsAdapter;

public class YourConnections extends AppCompatActivity {


    RecyclerView recyclerView;

    ArrayList<String> connectionsEmails;
    List<ParseObject> passData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_connections);

        recyclerView = (RecyclerView)findViewById(R.id.connectTherapistsRV);
        connectionsEmails = new ArrayList<>();
        passData = new ArrayList<ParseObject>();

        getConnectedTherapists();
    }



    private void getConnectedTherapists()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Connections");
        query.whereEqualTo(Utility.CONNECTION_USERUSERNAME, ParseUser.getCurrentUser().getEmail());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    for (ParseObject object : objects) {

                        String email = object.getString(Utility.CONNECTION_THERAPISTUSERNAME);


                        List<ParseObject> objectArrayList;


                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Therapist");
                        query.whereEqualTo(Utility.THERAPIST_USERNAME, email);




                        try {
                            objectArrayList = query.find();

                            if(!objectArrayList.isEmpty()) {
                                Toast.makeText(YourConnections.this, objectArrayList.get(0).getString(Utility.THERAPIST_NAME), Toast.LENGTH_SHORT).show();
                                passData.add(objectArrayList.get(0));
                            }
                            else
                            {
                                Toast.makeText(YourConnections.this, "Therapist List is empty", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }


                    }


                    if (!passData.isEmpty()) {

                        TherapistsAdapter adapter = new TherapistsAdapter(passData);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    } else {
                        Toast.makeText(YourConnections.this, "You Don't Have Any Connections", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(YourConnections.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });









    }


}
