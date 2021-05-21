package com.example.CoronaMeter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyHospitalAdapter extends ArrayAdapter<HospitalModel> {

    public Context context;
    public List<HospitalModel> hospitalModelsList;
    public List<HospitalModel> hospitalModelsListFiltered;

    public MyHospitalAdapter( Context context, List<HospitalModel> hospitalModelsList) {
        super(context, R.layout.list_custom_item,hospitalModelsList);
        this.context = context;
        this.hospitalModelsList = hospitalModelsList;
        this.hospitalModelsListFiltered = hospitalModelsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        TextView statename1 = view.findViewById(R.id.statename1);
        TextView listTotal = view.findViewById(R.id.listTotal);
statename1.setText(hospitalModelsListFiltered.get(position).getDistrict());
listTotal.setText(String.valueOf(hospitalModelsListFiltered.get(position).getTotalbeds()));
        tvCountryName.setText(hospitalModelsListFiltered.get(position).getName());

        return view;
    }

    @Override
    public int getCount() {
        return hospitalModelsListFiltered.size();
    }

    @Nullable
    @Override
    public HospitalModel getItem(int position) {
        return hospitalModelsListFiltered.get(position);
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
                    filterResults.count = hospitalModelsList.size();
                    filterResults.values = hospitalModelsList;

                }else{
                    List<HospitalModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(HospitalModel itemsModel:hospitalModelsList){
                        if(itemsModel.getDistrict().toLowerCase().contains(searchStr)){
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

                hospitalModelsListFiltered = (List<HospitalModel>) results.values;
                myQuestions.hospitalModelsList = (List<HospitalModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}