package com.example.CoronaMeter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDistrictAdapter extends ArrayAdapter<CountryModel> {

    public Context context;
    public List<CountryModel> countryModelsList;
    public List<CountryModel> countryModelsListFiltered;

    public MyDistrictAdapter( Context context, List<CountryModel> countryModelsList) {
        super(context, R.layout.list_custom_item1,countryModelsList);
        this.context = context;
        this.countryModelsList = countryModelsList;
        this.countryModelsListFiltered = countryModelsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item1,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
TextView statename=view.findViewById(R.id.statename1);

        LinearLayout parentList=view.findViewById(R.id.parentList);
        LinearLayout listIcon=view.findViewById(R.id.listIcon);
        TextView listTotal=view.findViewById(R.id.listTotal);
        listIcon.setAnimation(AnimationUtils.loadAnimation(context,R.anim.listicon_anim));
        parentList.setAnimation(AnimationUtils.loadAnimation(context,R.anim.list_anim));
        tvCountryName.setText(countryModelsListFiltered.get(position).getCountry());
        statename.setText(countryModelsListFiltered.get(position).getStatename());
        listTotal.setText(String.valueOf(countryModelsListFiltered.get(position).getCases()));
        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
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
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModel itemsModel:countryModelsList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
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

                countryModelsListFiltered = (List<CountryModel>) results.values;
                myAns.countryModelsList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}