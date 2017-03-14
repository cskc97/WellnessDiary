package apps.everythingforward.com.wellnessdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import apps.everythingforward.com.wellnessdiary.adapters.DiaryAdapter;

public class DisplayDiary extends AppCompatActivity {


    RecyclerView recyclerView;
    DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_diary);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);










    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryAdapter = new DiaryAdapter();

        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


    }
}
