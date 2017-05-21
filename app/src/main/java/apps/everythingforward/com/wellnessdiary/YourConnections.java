package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class YourConnections extends AppCompatActivity {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_connections);

        recyclerView = (RecyclerView)findViewById(R.id.connectTherapistsRV);
    }
}
