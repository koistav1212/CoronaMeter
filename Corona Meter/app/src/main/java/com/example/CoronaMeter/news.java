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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class news extends Fragment {
    ScrollView scrollView;
    EditText edtSearch;
    private ListView listView;
    Context context;
    public static List<newsModel> newsModelist=new ArrayList<>();
    newsModel model;
    newsAdapter myCustomAdapter;

    public news() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_news, container, false);

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
                Fragment newFragment = new newsDetail();
                // consider using Java coding conventions (upper first char class names!!!)
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();   }
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
        Button hospitalbutton=view.findViewById(R.id.hospitalbutton);
        hospitalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new myQuestions()
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
        Button stateBtn=view.findViewById(R.id.statbutton);
        stateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new newsDetail()
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
        newsModelist=new ArrayList<newsModel>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String headline,content,date,imgurl,source,description;
                            JSONArray jsonArray=response.getJSONArray("articles");
                          for(int i=0;i<20;i++)
                          {
                              JSONObject newsOBJ=jsonArray.getJSONObject(i);
                              headline=newsOBJ.getString("title");
                              description=newsOBJ.getString("url");
                              date=newsOBJ.getString("publishedAt").substring(0,9);
                              imgurl=newsOBJ.getString("urlToImage");
                              content=newsOBJ.getString("content");
                              JSONObject sourceOBJ=newsOBJ.getJSONObject("source");
                              source=sourceOBJ.getString("name");
                               model=new newsModel(headline,content,date,imgurl,source,description);
                               newsModelist.add(model);
                                          }
                            myCustomAdapter = new newsAdapter(getActivity().getApplicationContext(),newsModelist);
                            listView.setAdapter(myCustomAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }
}
