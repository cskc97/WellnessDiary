package apps.everythingforward.com.wellnessdiary.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import apps.everythingforward.com.wellnessdiary.R;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecords;
import apps.everythingforward.com.wellnessdiary.database.DiaryRecordsEntityManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

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

    public DiaryAdapter(ArrayList<DiaryRecords> otherData)
    {
        manager = new DiaryRecordsEntityManager();
        data = otherData;
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

        String setVal = sVal+"%";
        holder.sentimentTV.setText(setVal);

        final ViewHolder hold = holder;
        final int pos = position;

        holder.diaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(hold.diaryCard.getContext(),SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Your Diary Entry:")
                        .setContentText(data.get(pos).getDiaryEntryText())
                        .show();
            }
        });

        holder.diaryCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new SweetAlertDialog(hold.diaryCard.getContext(),SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Delete Diary Entry")
                        .setContentText("Are you sure you want to delete this diary entry?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                               removeDataPositon(pos);
                                sweetAlertDialog.dismissWithAnimation();



                            }
                        })
                        .show();


                return false;
            }
        });





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

            diaryCard = (CardView)itemView.findViewById(R.id.feedcard);
            dateTV = (TextView)itemView.findViewById(R.id.diarydate);
            timeTV=(TextView)itemView.findViewById(R.id.diarytime);
            sentimentTV=(TextView)itemView.findViewById(R.id.sentimentvalue);


        }
    }

    public void removeDataPositon(int position)
    {

        manager.delete(data.get(position));

        data.remove(position);

        this.notifyItemRemoved(position);

    }

    public void removeAll()
    {
        manager.deleteAll();

        data.clear();

        this.notifyDataSetChanged();

        //this.notifyItemRangeRemoved(0,getItemCount());

    }
}
