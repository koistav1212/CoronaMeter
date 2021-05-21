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
import android.widget.ProgressBar;
import android.widget.TextView;

public class DetailActivity extends Fragment {

    private  int positionCountry;
    TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvTodayRecovered;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.activity_detail, container, false);
      positionCountry=Integer.valueOf(getArguments().getString("positionCountry"));
        tvCountry = view.findViewById(R.id.tvCountry);
        tvCases = view.findViewById(R.id.tvCases);
        tvRecovered = view.findViewById(R.id.tvRecovered);
        tvCritical = view.findViewById(R.id.tvCritical);
        tvActive = view.findViewById(R.id.tvActive);
        tvTodayCases = view.findViewById(R.id.tvTodayCases);
        tvTotalDeaths = view.findViewById(R.id.tvDeaths);
        tvTodayDeaths = view.findViewById(R.id.tvTodayDeaths);
        tvTodayRecovered=view.findViewById(R.id.tvTodayRecovered);
        ProgressBar progressBar1=view.findViewById(R.id.progressBar);
        progressBar1.setMax(50000);
        progressBar1.setProgress((int) Home.countryModelsList.get(positionCountry).getTodayCases());

        ProgressBar progressBar2=view.findViewById(R.id.progressBar2);
        progressBar2.setMax(50000);
        progressBar2.setProgress((int) Home.countryModelsList.get(positionCountry).getTodayRecovered());

        ProgressBar progressBar3=view.findViewById(R.id.progressBar3);
        progressBar3.setMax(10000);
        progressBar3.setProgress((int) Home.countryModelsList.get(positionCountry).getTodayDeaths());
        tvCountry.setText(Home.countryModelsList.get(positionCountry).getCountry());
        tvCases.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getCases()));
        tvRecovered.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getRecovered()));
        tvActive.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getActive()));
        tvTodayCases.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getTodayCases()));
        tvTotalDeaths.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getDeaths()));
        tvTodayDeaths.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getTodayDeaths()));
        tvTodayRecovered.setText(String.valueOf(Home.countryModelsList.get(positionCountry).getTodayRecovered()));

return view;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            getActivity().finish();
        return super.onOptionsItemSelected(item);
    }
}