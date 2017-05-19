package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.gc.materialdesign.views.ButtonFlat;
import com.shawnlin.numberpicker.NumberPicker;

public class ManualEntry extends AppCompatActivity {

    NumberPicker numberPicker;
    ButtonFlat submitButton;
    EditText diaryEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);
        numberPicker = (NumberPicker)findViewById(R.id.number_picker);
        submitButton = (ButtonFlat)findViewById(R.id.submitmanualentry);
        diaryEntry = (EditText)findViewById(R.id.feelingsET);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(ManualEntry.this,EmotionAnalyze.class);



                intent1.putExtra(Intent.EXTRA_TEXT,Float.valueOf(numberPicker.getValue()));
                intent1.putExtra("diaryentrystring",diaryEntry.getText().toString());
                startActivity(intent1);


            }
        });


    }
}
