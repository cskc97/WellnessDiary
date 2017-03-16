package apps.everythingforward.com.wellnessdiary;

import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;
import apps.everythingforward.com.wellnessdiary.database.GraphFilePaths;
import apps.everythingforward.com.wellnessdiary.database.GraphFilePathsColumns;
import apps.everythingforward.com.wellnessdiary.database.GraphFilePathsEntityManager;
import fr.xebia.android.freezer.async.Callback;

import static android.R.attr.data;


public class StatisticsActivity extends AppCompatActivity {


    DiaryRecordsEntityManager manager;
    GraphFilePathsEntityManager graphFilePathsEntityManager;

    TextView meanTV,stdTV;
    ImageView graphImg;
    String latestGraphFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        meanTV = (TextView)findViewById(R.id.meanemotionTV);
        stdTV = (TextView)findViewById(R.id.stddevTV);
        graphImg = (ImageView)findViewById(R.id.graphIMG);



        manager = new DiaryRecordsEntityManager();
        graphFilePathsEntityManager = new GraphFilePathsEntityManager();

        ArrayList<DiaryRecords> dataValue;
        dataValue= (ArrayList<DiaryRecords>) manager.select().asList();

      /*  manager.select().async(new Callback<List<DiaryRecords>>() {
            @Override
            public void onSuccess(List<DiaryRecords> data) {

                dataValue.addAll(data);


            }

            @Override
            public void onError(List<DiaryRecords> data) {

            }
        });
        */

        meanTV.setText(String.valueOf(returnMean(dataValue)));
        stdTV.setText(String.valueOf(returnStdDeviation(dataValue)));



        GraphFilePaths latestGraph = graphFilePathsEntityManager.select().sortDesc(GraphFilePathsColumns.graphfileid).first();
        latestGraphFilePath  = latestGraph.getGraphImagePath();



    }

    @Override
    protected void onResume() {
        super.onResume();
        String fullPath = Environment.getExternalStorageDirectory() + "/"+Environment.DIRECTORY_DCIM + "/";
        fullPath+=latestGraphFilePath;
        Uri uri = Uri.fromFile(new File(fullPath));
        Picasso.with(getApplicationContext()).load(uri).fit().into(graphImg);



    }

    private double returnMean(ArrayList<DiaryRecords> data)
    {
        if(!data.isEmpty())
        {

            double[] valuesArray = new double[data.size()];

            for(int counter=0;counter<data.size();counter++)
            {
                valuesArray[counter]=Double.valueOf(data.get(counter).getDiaryEntrySentiment()).doubleValue();
            }

            Mean mean = new Mean();
            double returnMeanValue = mean.evaluate(valuesArray);
            DecimalFormat numberFormat = new DecimalFormat("#.00");

            return Double.valueOf(numberFormat.format(returnMeanValue)).doubleValue();




        }



        return -1;


    }

    private double returnStdDeviation(ArrayList<DiaryRecords> data)
    {
        if(!data.isEmpty())
        {
            double[] valuesArray = new double[data.size()];

            for(int counter=0;counter<data.size();counter++)
            {
                valuesArray[counter]=Double.valueOf(data.get(counter).getDiaryEntrySentiment()).doubleValue();
            }
            StandardDeviation deviation = new StandardDeviation();
            double retValue=deviation.evaluate(valuesArray);
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            return Double.valueOf(numberFormat.format(retValue)).doubleValue();

        }


        return -1;

    }


}

