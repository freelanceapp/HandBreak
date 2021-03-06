package com.creativeshare.hand_break.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.hand_break.R;
import com.creativeshare.hand_break.activities_fragments.home_activity.activity.HomeActivity;
import com.creativeshare.hand_break.models.Catogry_Model;
import com.creativeshare.hand_break.models.UserModel;
import com.creativeshare.hand_break.preferences.Preferences;
import com.creativeshare.hand_break.share.Common;
import com.creativeshare.hand_break.tags.Tags;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adversiment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int ITEM_LOAD = 2;

    private List<Catogry_Model.Advertsing> advertsings;
    private Context context;
    private Fragment fragment;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;
    public Adversiment_Adapter(List<Catogry_Model.Advertsing> advertsings, Context context) {

        this.advertsings = advertsings;
        this.context = context;
       // this.fragment = fragment;
        activity=(HomeActivity)context;
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_DATA) {
            View view = LayoutInflater.from(context).inflate(R.layout.search_row, parent, false);
            return new MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.load_more_row, parent, false);
            return new LoadMoreHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder) {

            final MyHolder myHolder = (MyHolder) holder;
            final Catogry_Model.Advertsing advertsing = advertsings.get(myHolder.getAdapterPosition());
            ((MyHolder) holder).tv_city.setText(advertsing.getCity_title());
            ((MyHolder) holder).tv_title.setText(advertsing.getAdvertisement_title());
            ((MyHolder) holder).tv_user.setText(advertsing.getUser_name());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH);
            String date = dateFormat.format(new Date(Long.parseLong(advertsing.getAdvertisement_date()) * 1000));
            ((MyHolder) holder).tv_time.setText(date.replace(" ",""));

            ((MyHolder) holder).tv_name.setText(advertsing.getMain_category_title());
            Picasso.with(context).load(Uri.parse(Tags.IMAGE_URL+advertsing.getMain_image())).fit().into(((MyHolder) holder).image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.DisplayFragmentAdversimentDetials(advertsings.get(holder.getLayoutPosition()).getId_advertisement());

                }
            });
            //Log.e("msg",advertsing.getMain_image());
        } else {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.progBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return advertsings.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private RoundedImageView image;
        private TextView tv_title, tv_city, tv_user, tv_name, tv_time;

        public MyHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.r_im_search);
            tv_title = itemView.findViewById(R.id.tv_title);

            tv_city = itemView.findViewById(R.id.tv_city);
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);


        }

    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        private ProgressBar progBar;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            progBar = itemView.findViewById(R.id.progBar);
            progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public int getItemViewType(int position) {
      Catogry_Model.Advertsing advertsing = advertsings.get(position);
        if (advertsing == null) {
            return ITEM_LOAD;
        } else {
            return ITEM_DATA;

        }


    }
}
