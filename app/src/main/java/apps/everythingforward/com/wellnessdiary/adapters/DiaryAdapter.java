package apps.everythingforward.com.wellnessdiary.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import apps.everythingforward.com.wellnessdiary.R;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;

/**
 * Created by santh on 3/14/2017.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    ArrayList<DiaryRecords> data;
    DiaryRecordsEntityManager manager;

    public DiaryAdapter()
    {
         manager = new DiaryRecordsEntityManager();
         data = (ArrayList<DiaryRecords>) manager.select().asList();

    }



    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diarycards,parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(DiaryAdapter.ViewHolder holder, int position) {

       String dateVal,timeVal,sVal;
        dateVal = data.get(position).getDiaryEntryDate();
        timeVal=data.get(position).getDiaryEntryTime();
        sVal = data.get(position).getDiaryEntrySentiment();

        double sValD = Double.valueOf(sVal).doubleValue();

        if(sValD<40)
        {
            holder.diaryCard.setBackgroundColor(holder.diaryCard.getContext()
                    .getResources().getColor(R.color.negativemood));
        }
        else if(sValD>60)
        {
            holder.diaryCard.setBackgroundColor(holder.diaryCard.getContext()
                    .getResources().getColor(R.color.positivemood));
        }
        else
        {
            holder.diaryCard.setBackgroundColor(holder.diaryCard.getContext()
                    .getResources().getColor(R.color.neutralmood));
        }


        holder.dateTV.setText(dateVal);
        holder.timeTV.setText(timeVal);
        holder.sentimentTV.setText(sVal);




    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView diaryCard;
        TextView dateTV,timeTV,sentimentTV;

        public ViewHolder(View itemView) {
            super(itemView);

            diaryCard = (CardView)itemView.findViewById(R.id.diarycard);
            dateTV = (TextView)itemView.findViewById(R.id.diarydate);
            timeTV=(TextView)itemView.findViewById(R.id.diarytime);
            sentimentTV=(TextView)itemView.findViewById(R.id.sentimentvalue);


        }
    }
}
