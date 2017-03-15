package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;

import apps.everythingforward.com.wellnessdiary.adapters.DiaryAdapter;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;

public class DiaryEntryByDate extends AppCompatActivity {

    String dateValue = null;
    DiaryRecordsEntityManager manager;

    DiaryAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<DiaryRecords> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry_by_date);

        dateValue = getIntent().getStringExtra(Intent.EXTRA_TEXT);


        manager = new DiaryRecordsEntityManager();



        recyclerView = (RecyclerView)findViewById(R.id.diarybydaterv);








    }

    @Override
    protected void onResume() {
        super.onResume();

        data = (ArrayList<DiaryRecords>) manager.select().diaryEntryDate().equalsTo(dateValue)
                .asList();

        adapter= new DiaryAdapter(data);




        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new CenterScrollListener());



    }
}
