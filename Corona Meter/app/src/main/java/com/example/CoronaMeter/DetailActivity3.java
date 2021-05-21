package com.example.CoronaMeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity3 extends Fragment {

    private  int positionCountry;
    TextView tvCountry,withOXY,withoutOXY,statename,withVenti,withoutVenti,totalBeds,tvTotalDeaths,tvTodayDeaths,tvTodayRecovered;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.activity_detail3, container, false);
        positionCountry=Integer.valueOf(getArguments().getString("positionCountry"));

        statename=view.findViewById(R.id.statename);
        tvCountry = view.findViewById(R.id.tvCountry1);
        withOXY = view.findViewById(R.id.withOXY);
        withoutOXY = view.findViewById(R.id.withoutOXY);

        withoutVenti = view.findViewById(R.id.withoutVenti);
        withVenti = view.findViewById(R.id.withVenti);
        totalBeds = view.findViewById(R.id.totalBeds);
        tvCountry.setText(myQuestions.hospitalModelsList.get(positionCountry).getName());
        totalBeds.setText(String.valueOf(myQuestions.hospitalModelsList.get(positionCountry).getTotalbeds()));
        statename.setText(myQuestions.hospitalModelsList.get(positionCountry).getDistrict());
        withOXY.setText(myQuestions.hospitalModelsList.get(positionCountry).getAvailWithOXY());
        withoutOXY.setText(myQuestions.hospitalModelsList.get(positionCountry).getAvailWithoutOXY());
        withoutVenti.setText(myQuestions.hospitalModelsList.get(positionCountry).getAvailWithoutVentilator());
        withVenti.setText(myQuestions.hospitalModelsList.get(positionCountry).getAvailWithVentilator());
        return view;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            getActivity().finish();
        return super.onOptionsItemSelected(item);
    }
}