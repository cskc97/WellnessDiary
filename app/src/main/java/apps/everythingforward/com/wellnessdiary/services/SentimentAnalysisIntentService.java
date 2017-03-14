package apps.everythingforward.com.wellnessdiary.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apps.everythingforward.com.wellnessdiary.network.HttpRequest;

/**
 * Created by santh on 3/14/2017.
 */

public class SentimentAnalysisIntentService extends IntentService {

    String URL = "https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
    public static String TextAnalyticsKey = "b5582910d9b242a7a97f3de4d3707634";

    public static String ACTION_FINISHEDTEXT = "finished";

    public SentimentAnalysisIntentService()
    {
        super("SentimentAnalysisIntentService");
    }

    public SentimentAnalysisIntentService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e("Service", "Inside");

        String stuff = intent.getStringExtra(Intent.EXTRA_TEXT);


        JSONObject object = new JSONObject();

        try {
            object.put("language", "en");
            object.put("id", "01");
            object.put("text", stuff);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray array = new JSONArray();
        try {
            array.put(0, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject mainObjectPut = new JSONObject();
        try {
            mainObjectPut.put("documents", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //  array.put
        String postString = mainObjectPut.toString();

        Log.e("Service", postString);

        String response = null;


        try {
            response = HttpRequest.post(URL).contentType("application/json").
                    header("Ocp-Apim-Subscription-Key", TextAnalyticsKey).
                    send(postString).body();
        } catch (Exception exception) {
            Intent sendBroadcastintent = new Intent(SentimentAnalysisIntentService.ACTION_FINISHEDTEXT);
            sendBroadcast(sendBroadcastintent);
            return;

        }

        if (response != null) {
            Log.e("Service", response);


            JSONObject parseJSOMain = null;
            try {
                parseJSOMain = new JSONObject(response);
                JSONArray arrayanother = parseJSOMain.getJSONArray("documents");
                JSONObject anotherObject = (JSONObject) arrayanother.get(0);
                String finalValue = anotherObject.getString("score");
                Log.e("Sentiment", finalValue);



                Intent sendBroadcastintent = new Intent(SentimentAnalysisIntentService.ACTION_FINISHEDTEXT);
                sendBroadcastintent.putExtra("value",Double.valueOf(finalValue).doubleValue());

                sendBroadcast(sendBroadcastintent);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
