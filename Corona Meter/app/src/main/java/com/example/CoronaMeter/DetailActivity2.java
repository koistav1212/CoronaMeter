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

public class DetailActivity2 extends Fragment {

    private  int positionCountry;
    TextView tvCountry,tvCases,tvRecovered,statename,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvTodayRecovered;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.activity_detail2, container, false);
        positionCountry=Integer.valueOf(getArguments().getString("positionCountry"));

        statename=view.findViewById(R.id.statename);
        tvCountry = view.findViewById(R.id.tvCountry1);
        tvCases = view.findViewById(R.id.tvCases1);
        tvRecovered = view.findViewById(R.id.tvRecovered1);

        tvActive = view.findViewById(R.id.tvActive1);
        tvTotalDeaths = view.findViewById(R.id.tvDeaths1);
        tvCountry.setText(myAns.countryModelsList.get(positionCountry).getCountry());
        tvCases.setText(String.valueOf(myAns.countryModelsList.get(positionCountry).getCases()));
        tvRecovered.setText(String.valueOf(myAns.countryModelsList.get(positionCountry).getRecovered()));
        tvActive.setText(String.valueOf(myAns.countryModelsList.get(positionCountry).getActive()));
        tvTotalDeaths.setText(String.valueOf(myAns.countryModelsList.get(positionCountry).getDeaths()));
statename.setText(myAns.countryModelsList.get(positionCountry).getStatename());
return view;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            getActivity().finish();
        return super.onOptionsItemSelected(item);
    }
}