package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class CalendarPick extends AppCompatActivity {

    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_pick);

        calendarView = (MaterialCalendarView)findViewById(R.id.calendarView);

        calendarView.canGoBack();


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                int year = calendarView.getSelectedDate().getYear();
                int month = calendarView.getSelectedDate().getMonth()+1;
                int dateVal = calendarView.getSelectedDate().getDay();
                String monthAsString;

                if(month<10)
                {
                    monthAsString = "0"+String.valueOf(month);

                }
                else
                {
                    monthAsString=String.valueOf(month);
                }
                String dateValue = String.valueOf(year)+"-"+monthAsString+"-"+String.valueOf(dateVal);

                Toast.makeText(getApplicationContext(),dateValue,
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CalendarPick.this,DiaryEntryByDate.class);
                intent.putExtra(Intent.EXTRA_TEXT,dateValue);
                startActivity(intent);




            }
        });



    }


}
