package com.example.CoronaMeter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class myAns extends Fragment {

    ScrollView scrollView;
    EditText edtSearch;
   private ListView listView;
Context context;
        public static List<CountryModel> countryModelsList=new ArrayList<>();
    CountryModel countryModel;
    MyDistrictAdapter myCustomAdapter;

    public myAns() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view= inflater.inflate(R.layout.activity_my_ans, container, false);

        listView=(ListView) view.findViewById(R.id.listView);
        edtSearch=view.findViewById(R.id.edtSearch);

        scrollView = view.findViewById(R.id.scrollStats);


context=getActivity().getApplicationContext();
        fetchData1();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle =new Bundle();
                bundle.putString("positionCountry",String.valueOf(position));
                Fragment newFragment = new DetailActivity2();
                // consider using Java coding conventions (upper first char class names!!!)
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();            }
        });
        Button newsbutton=view.findViewById(R.id.newsbutton);
        newsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new news();

                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        ImageView share=view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap mBitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                        R.drawable.speedometer);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mBitmap, "Image I want to share", null);
                Uri uri = Uri.parse(path);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Corona Meter");
                sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Corona Meter and get precise data on covid and health news of your State and District.                "+"https://drive.google.com/file/d/1TddTPwZPvLKtOuT9tjUM-VQeFvk3oGTZ/view?usp=sharing");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Hlw");
                startActivity(shareIntent);
            }
        });
        Button stateBTN=view.findViewById(R.id.statbutton);
        stateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Home()
                        ;
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
    private void fetchData1() {
        countryModelsList=new ArrayList<CountryModel>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 1; i < response.length(); i++) {
                                String state;
                                long totalConfirmed=0L,totalActive=0L,totalDeaths=0L,totalRecovered=0L;
                                JSONObject dataOBJ = response.getJSONObject(i);
                                state=dataOBJ.getString("state");
                                JSONArray jsonChild = dataOBJ.getJSONArray("districtData");
                                for (int k = 0; k < jsonChild.length(); k++) {
                                    String district;
                                    JSONObject jsonObject = jsonChild.getJSONObject(k);
                                    // work to be done...
                                    district=jsonObject.getString("district");

                                    totalConfirmed = Long.valueOf(jsonObject.getInt("confirmed"));
                                    totalDeaths = Long.valueOf(jsonObject.getInt("deceased"));
                                    totalRecovered =Long.valueOf(jsonObject.getInt("recovered"));
                                    totalActive =Long.valueOf(jsonObject.getInt("active"));

                                    countryModel = new CountryModel(district,state,totalConfirmed, 0,totalDeaths,0,totalRecovered,0,totalActive);
                                    countryModelsList.add(countryModel);
                                }

                            }


                            myCustomAdapter = new MyDistrictAdapter(getActivity().getApplicationContext(),countryModelsList);
                            listView.setAdapter(myCustomAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(request);

    }

}
