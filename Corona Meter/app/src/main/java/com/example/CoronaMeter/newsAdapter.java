package com.example.CoronaMeter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class newsAdapter extends ArrayAdapter<newsModel> {

    public Context context;
    public List<newsModel> countryModelsList;
    public List<newsModel> countryModelsListFiltered;

    public newsAdapter( Context context, List<newsModel> countryModelsList) {
        super(context, R.layout.list_custom_item1,countryModelsList);
        this.context = context;
        this.countryModelsList = countryModelsList;
        this.countryModelsListFiltered = countryModelsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,null,true);
        TextView newsTitle = view.findViewById(R.id.newsname);
        TextView date=view.findViewById(R.id.newsdate);

        LinearLayout parentList=view.findViewById(R.id.parentList);
                ImageView listIcon=view.findViewById(R.id.listIcon1);
               Glide.with(context).load(countryModelsListFiltered.get(position).getImgurl())
                       .into(listIcon);
        listIcon.setAnimation(AnimationUtils.loadAnimation(context,R.anim.listicon_anim));
        parentList.setAnimation(AnimationUtils.loadAnimation(context,R.anim.list_anim));
        newsTitle.setText(countryModelsListFiltered.get(position).getHeadline());
        date.setText(countryModelsListFiltered.get(position).getDate());

        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public newsModel getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;

                }else{
                    List<newsModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(newsModel itemsModel:countryModelsList){
                        if(itemsModel.getDescription().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelsListFiltered = (List<newsModel>) results.values;
                news.newsModelist = (List<newsModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}