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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class myQuestions extends Fragment {

    ScrollView scrollView;
    EditText edtSearch;
    private ListView listView;
    Context context;
    public static List<HospitalModel> hospitalModelsList=new ArrayList<>();
    HospitalModel hospitalModel;
    MyHospitalAdapter myCustomAdapter;

    public myQuestions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_my_questions, container, false);

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
                Fragment newFragment = new DetailActivity3();
                // consider using Java coding conventions (upper first char class names!!!)
                newFragment.setArguments(bundle);
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
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("allWB_hospitals.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    private void fetchData1() {
        String jsonFileContent = loadJSONFromAsset(getActivity().getApplicationContext());
        try {
            JSONObject jsonObject = new JSONObject(jsonFileContent);
            JSONArray jsonArray = jsonObject.getJSONArray("HospitalName");
            for (int k = 0; k < jsonArray.length(); k++) {
                JSONObject object = jsonArray.getJSONObject(k);
                String district, name;
                long totalbeds = 0L, withoutOXY = 0L, withOXY = 0L, withoutVentilator = 0L, withVentilator = 0L;
                String availWithoutOXY, availWithOXY, availWithoutVentilator, availWithVentilator ;
                district = object.getString("district");
                name = object.getString("name");
                withoutOXY = Long.valueOf(object.getString("withoutOXY"));
                withOXY = Long.valueOf(object.getString("withOXY"));
                withoutVentilator = Long.valueOf(object.getString("withoutVentilator"));
                withVentilator = Long.valueOf(object.getString("withVentilator"));
                availWithoutOXY = (object.getString("availWithoutOXY"));
                availWithOXY = (object.getString("availWithOXY"));
                availWithoutVentilator = (object.getString("availWithoutVentilator"));
                availWithVentilator = (object.getString("availWithVentilator"));
                totalbeds = withoutOXY + withOXY + withoutVentilator + withVentilator;
                hospitalModel = new HospitalModel(name,district,withoutOXY,withOXY,withoutVentilator,withVentilator,availWithoutOXY,availWithOXY,availWithoutVentilator,availWithVentilator,totalbeds);
                hospitalModelsList.add(hospitalModel);
            }
            myCustomAdapter = new MyHospitalAdapter(getActivity().getApplicationContext(), hospitalModelsList);
            listView.setAdapter(myCustomAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
