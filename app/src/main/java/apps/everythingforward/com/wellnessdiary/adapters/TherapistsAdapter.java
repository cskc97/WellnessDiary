package apps.everythingforward.com.wellnessdiary.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnessdiary.R;
import apps.everythingforward.com.wellnessdiary.Utility;

/**
 * Created by santh on 5/20/2017.
 */

public class TherapistsAdapter extends RecyclerView.Adapter<TherapistsAdapter.ViewHolder> {



    ArrayList<ParseObject> data;

    public TherapistsAdapter(List<ParseObject> objectList)
    {

        data = (ArrayList<ParseObject>)objectList;
    }






    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        RelativeLayout layout;



        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.therapistImage);
            name = (TextView)itemView.findViewById(R.id.therapistName);
            layout = (RelativeLayout)itemView.findViewById(R.id.relLayout);


        }
    }

    @Override
    public TherapistsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.therapistitem,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TherapistsAdapter.ViewHolder holder, int position) {

        final String name = data.get(position).getString(Utility.THERAPIST_NAME);
        final String description = data.get(position).getString(Utility.THERAPIST_DESCRIPTION);


        holder.image.setImageDrawable(null);

        final ViewHolder holderFinal = holder;


        ParseFile file = data.get(position).getParseFile(Utility.THERAPIST_IMAGE);
        Log.e("TherapistsAdapter",file.getName());

        Picasso.with(holder.image.getContext())
                .load(file.getUrl())

                .placeholder(R.drawable.applogo100x100)
                .fit()
                .centerCrop()
                .into(holder.image);


/*        file.getUrl();

        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {


                Glide.with(holderFinal.image.getContext())


                        .load(data)

                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.placeholderOf(R.drawable.applogo100x100))
                        .into(holderFinal.image);


            }
        });

        */



//        try {
//            byte[] data = file.getData();
//            Glide.with(holderFinal.image.getContext()).load(data).apply(RequestOptions.fitCenterTransform()).into(holderFinal.image);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }





        holderFinal.name.setText(name);

        final int positionVal = position;

        holderFinal.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialStyledDialog.Builder(holderFinal.layout.getContext())
                        .setTitle(name)
                        .setDescription(description)
                        .setStyle(Style.HEADER_WITH_TITLE)
                        .setHeaderColor(R.color.colorPrimaryDark)
                        .withDialogAnimation(true)
                        .withIconAnimation(true)
                        .setScrollable(true)
                        .setCancelable(true)
                        //.setStyle(Style.HEADER_WITH_TITLE)
                        .show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
