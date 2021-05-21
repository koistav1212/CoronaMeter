package com.example.CoronaMeter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.charts.Map;
import com.anychart.core.axes.Circular;
import com.anychart.core.axes.Circular;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.exceptions.UnirestException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;



public class Home extends Fragment {
    TextView tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvAffectedCountries,tvTest;
    EditText edtSearch;
    ListView listView;
    Button newsbutton,hospitalbtn;
    String tabTitles[] = new String[]{"Confirm", "Recovered", "Deaths"};
    private int[] imageResId = {
            R.drawable.tabicon1,
            R.drawable.tabicon3,
            R.drawable.tabicon2
    };
 private LineChart chart;
    private TextView tvX, tvY;
    long alltodaydeaths[]=new long[37];
    long alltodayconfirmed[] =new long[37] ;
    long alltodayrecovered[]=new long[37];
    private static final int NUM_PAGES = 3;

    private ViewPager viewPager;
    private FragmentStateAdapter pagerAdapter;
   long indiatodayDeaths=0L,indiatodayRecovered=0L,indiatodayConfirm=0L;

      long total=0L,indiatotalDeaths1=0L,indiatotalConfirmed1=0L,indiatotalActive1=0L,indiatotalRecovered1=0L,indiatotalTests1=0L;
    public static List<CountryModel> countryModelsList=new ArrayList<>();
    CountryModel countryModel;
    MyCustomAdapter myCustomAdapter;
    ScrollView scrollView;

    public  Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.activity_home, container, false);
        tvCases = view.findViewById(R.id.tvCases);
        tvRecovered = view.findViewById(R.id.tvRecovered);
        tvCritical = view.findViewById(R.id.tvCritical);
        tvActive = view.findViewById(R.id.tvActive);
        tvTodayCases = view.findViewById(R.id.tvTodayCases);
        tvTotalDeaths = view.findViewById(R.id.tvTotalDeaths);
        tvTest=view.findViewById(R.id.tvTest);
        newsbutton=view.findViewById(R.id.newsbutton);
        tvTodayDeaths = view.findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = view.findViewById(R.id.tvAffectedCountries);
listView=(ListView) view.findViewById(R.id.listView);
        edtSearch=view.findViewById(R.id.edtSearch);
        scrollView = view.findViewById(R.id.scrollStats);
countryModelsList=new ArrayList<CountryModel>();
viewPager= view.findViewById(R.id.viewPager);

fetchData1();
            fetchData();
        fetchData2();
        graphs(view);
        tablayout(view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle =new Bundle();
                bundle.putString("positionCountry",String.valueOf(position));
                Fragment newFragment = new DetailActivity();
                // consider using Java coding conventions (upper first char class names!!!)
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        Button about=view.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new about();
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
newsbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Fragment newFragment = new news()
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
        // // Chart Style // //
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

 public void tablayout(View view)
{
    TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    // mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    //mRecyclerView.setLayoutManager(mLayoutManager);
    viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
    viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

}
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = getActivity().getApplicationContext().getResources().getDrawable(imageResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" "+tabTitles[position]);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new confirmGraph();
                case 1:
                    return new recoveredGraph();
                case 2:
                    return new deathGraph();

                default:
                    return new confirmGraph();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private  void graphs( View view)
    {
                AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        CircularGauge circularGauge = AnyChart.circular();
        StringRequest request = new StringRequest(Request.Method.GET, "https://corona.lmao.ninja/v3/covid-19/countries/India?yesterday=true&strict=true",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            String indiatotalDeaths="",indiatotalConfirmed="",indiatotalActive="",indiatotalRecovered="",indiatotalTests="";
                            JSONObject dataOBJ = new JSONObject(response);
                            indiatotalConfirmed1= Long.valueOf(dataOBJ.getString("cases"));
                            indiatotalRecovered1= Long.parseLong(dataOBJ.getString("recovered"));
                            indiatotalActive1= Long.parseLong(dataOBJ.getString("active"));
                            indiatotalDeaths1= Long.parseLong(dataOBJ.getString("deaths"));
                            indiatotalTests1= Long.parseLong(dataOBJ.getString("tests"));
                             indiatotalConfirmed1=(indiatotalConfirmed1/1000000);
                            indiatotalConfirmed=String.valueOf(indiatotalConfirmed1);

                            indiatotalActive1=(indiatotalActive1/1000000);
                            indiatotalActive=String.valueOf(indiatotalActive1);
                            indiatotalDeaths1=(indiatotalDeaths1/100000);
                            indiatotalDeaths=String.valueOf(indiatotalDeaths1);
                            indiatotalRecovered1=(indiatotalRecovered1/1000000);
                            indiatotalRecovered=String.valueOf(indiatotalRecovered1);
                            indiatotalTests1=(indiatotalTests1/10000000);
                            indiatotalTests=String.valueOf(indiatotalTests1);
                            circularGauge.data(new SingleValueDataSet(new String[]{indiatotalConfirmed,indiatotalRecovered,indiatotalDeaths,indiatotalActive,indiatotalTests,"100"}));

                        } catch (Exception exp) {

                        }



                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);

         circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(270d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(1d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(100d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);

        circularGauge.label(0d)
                .text("Confirmed <span style=\"\"></span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(0d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(100d + "%")
                .offsetX(0d);
        com.anychart.core.gauge.pointers.Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(100d);
        bar0.width(17d);
        bar0.fill(new SolidFill("#2196F3", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(5d);
        bar100.radius(100d);
        bar100.width(17d);
        bar100.fill(new SolidFill("#F5F4F4", 1d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);
String st1="70%";
        circularGauge.label(1d)
                .text("Recovered <span style=\"\"></span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(1d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(80d + "%")
                .offsetX(0d);
        com.anychart.core.gauge.pointers.Bar bar1 = circularGauge.bar(1d);
        bar1.dataIndex(1d);
        bar1.radius(80d);
        bar1.width(17d);
        bar1.fill(new SolidFill("#04bd19", 1d));
        bar1.stroke(null);
        bar1.zIndex(5d);
        com.anychart.core.gauge.pointers.Bar bar101 = circularGauge.bar(101d);
        bar101.dataIndex(5d);
        bar101.radius(80d);
        bar101.width(17d);
        bar101.fill(new SolidFill("#F5F4F4", 1d));
        bar101.stroke("1 #e5e4e4");
        bar101.zIndex(4d);

        circularGauge.label(2d)
                .text("Deaths <span style=\"\"></span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(2d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(60d + "%")
                .offsetX(0d);
        com.anychart.core.gauge.pointers.Bar bar2 = circularGauge.bar(2d);
        bar2.dataIndex(2d);
        bar2.radius(60d);
        bar2.width(17d);
        bar2.fill(new SolidFill("#ff0000", 1d));
        bar2.stroke(null);
        bar2.zIndex(5d);
        com.anychart.core.gauge.pointers.Bar bar102 = circularGauge.bar(102d);
        bar102.dataIndex(5d);
        bar102.radius(60d);
        bar102.width(17d);
        bar102.fill(new SolidFill("#F5F4F4", 1d));
        bar102.stroke("1 #e5e4e4");
        bar102.zIndex(4d);

        circularGauge.label(3d)
                .text("Active <span style=\"\"></span>")
                .useHtml(true)
                .hAlign(String.valueOf(HAlign.CENTER))
                .vAlign(String.valueOf(VAlign.MIDDLE));
        circularGauge.label(3d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(40d + "%")
                .offsetX(0d);
        com.anychart.core.gauge.pointers.Bar bar3 = circularGauge.bar(3d);
        bar3.dataIndex(3d);
        bar3.radius(40d);
        bar3.width(17d);
        bar3.fill(new SolidFill("#ffd54f", 1d));
        bar3.stroke(null);
        bar3.zIndex(5d);
        com.anychart.core.gauge.pointers.Bar bar103 = circularGauge.bar(103d);
        bar103.dataIndex(5d);
        bar103.radius(40d);
        bar103.width(17d);
        bar103.fill(new SolidFill("#F5F4F4", 1d));
        bar103.stroke("1 #e5e4e4");
        bar103.zIndex(4d);

        circularGauge.label(4d)
                .text("Tests <span style=\"\"></span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(4d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(20d + "%")
                .offsetX(0d);
        com.anychart.core.gauge.pointers.Bar bar4 = circularGauge.bar(4d);
        bar4.dataIndex(4d);
        bar4.radius(20d);
        bar4.width(17d);
        bar4.fill(new SolidFill("#455a64", 1d));
        bar4.stroke(null);
        bar4.zIndex(5d);
        com.anychart.core.gauge.pointers.Bar bar104 = circularGauge.bar(104d);
        bar104.dataIndex(5d);
        bar104.radius(20d);
        bar104.width(17d);
        bar104.fill(new SolidFill("#F5F4F4", 1d));
        bar104.stroke("1 #e5e4e4");
        bar104.zIndex(4d);

        circularGauge.margin(50d, 50d, 50d, 50d);
        circularGauge.title()
                .text("India Coronavirus Overview' +\n" +
                        "    '<br/><span style=\"color:#A6E91E63; font-size: 20px;\"></span>")
                .useHtml(true);
        circularGauge.title().enabled(true);
        circularGauge.title().hAlign(HAlign.CENTER);
        circularGauge.title()
                .padding(0d, 0d, 0d, 0d)
                .margin(0d, 0d, 20d, 0d);

        anyChartView.setChart(circularGauge);
    }


    private void fetchData() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://corona.lmao.ninja/v3/covid-19/countries/India?yesterday=true&strict=true",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                                JSONObject dataOBJ = new JSONObject(response);
                                indiatotalConfirmed1= Long.valueOf(dataOBJ.getString("cases"));
                                indiatotalRecovered1= Long.parseLong(dataOBJ.getString("recovered"));
                                indiatotalActive1= Long.parseLong(dataOBJ.getString("active"));
                                indiatotalDeaths1= Long.parseLong(dataOBJ.getString("deaths"));
                                indiatotalTests1= Long.parseLong(dataOBJ.getString("tests"));

                            tvCases.setText(dataOBJ.getString("cases"));
                            tvTotalDeaths.setText(dataOBJ.getString("deaths"));
                            tvCritical.setText(dataOBJ.getString("critical"));
                                tvActive.setText(dataOBJ.getString("active"));
                            tvRecovered.setText(dataOBJ.getString("recovered"));
                            tvTest.setText(dataOBJ.getString("tests"));
                            tvTodayDeaths.setText(String.valueOf(indiatodayDeaths));
                            tvTodayCases.setText(String.valueOf(indiatodayConfirm));

                            tvAffectedCountries.setText(String.valueOf(indiatodayRecovered));

                        } catch (Exception exp) {

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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
    private void fetchData2() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://api.covid19india.org/states_daily.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray1=jsonObject.getJSONArray("states_daily");
                                JSONObject obj1 = jsonArray1.getJSONObject(jsonArray1.length()-1);
                                alltodaydeaths[1] = Long.valueOf(obj1.getString("an"));
                                alltodaydeaths[2] = Long.valueOf(obj1.getString("ap"));
                                alltodaydeaths[3] = Long.valueOf(obj1.getString("ar"));
                                alltodaydeaths[4] = Long.valueOf(obj1.getString("as"));
                                alltodaydeaths[5] = Long.valueOf(obj1.getString("br"));
                                alltodaydeaths[6] = Long.valueOf(obj1.getString("ch"));
                                alltodaydeaths[7] = Long.valueOf(obj1.getString("ct"));
                                alltodaydeaths[8] = Long.valueOf(obj1.getString("dl"));
                                alltodaydeaths[9] = Long.valueOf(obj1.getString("dn"));
                                alltodaydeaths[10] = Long.valueOf(obj1.getString("ga"));
                                alltodaydeaths[11] = Long.valueOf(obj1.getString("gj"));
                                alltodaydeaths[12] = Long.valueOf(obj1.getString("hp"));
                                alltodaydeaths[13] = Long.valueOf(obj1.getString("hr"));
                                alltodaydeaths[14] = Long.valueOf(obj1.getString("jh"));
                                alltodaydeaths[15] = Long.valueOf(obj1.getString("jk"));
                                alltodaydeaths[16] = Long.valueOf(obj1.getString("ka"));
                                alltodaydeaths[17] = Long.valueOf(obj1.getString("kl"));
                                alltodaydeaths[18] = Long.valueOf(obj1.getString("la"));
                                alltodaydeaths[19] = Long.valueOf(obj1.getString("ld"));
                                alltodaydeaths[20] = Long.valueOf(obj1.getString("mh"));
                                alltodaydeaths[21] = Long.valueOf(obj1.getString("ml"));
                                alltodaydeaths[22] = Long.valueOf(obj1.getString("mn"));
                                alltodaydeaths[23] = Long.valueOf(obj1.getString("mp"));
                                alltodaydeaths[24] = Long.valueOf(obj1.getString("mz"));
                                alltodaydeaths[25] = Long.valueOf(obj1.getString("nl"));
                                alltodaydeaths[26] = Long.valueOf(obj1.getString("or"));
                                alltodaydeaths[27] = Long.valueOf(obj1.getString("pb"));
                                alltodaydeaths[28] = Long.valueOf(obj1.getString("py"));
                                alltodaydeaths[29] = Long.valueOf(obj1.getString("rj"));
                                alltodaydeaths[30] = Long.valueOf(obj1.getString("sk"));
                                alltodaydeaths[31] = Long.valueOf(obj1.getString("tg"));
                                alltodaydeaths[32] = Long.valueOf(obj1.getString("tn"));
                                alltodaydeaths[33] = Long.valueOf(obj1.getString("tr"));
                                alltodaydeaths[34] = Long.valueOf(obj1.getString("up"));
                                alltodaydeaths[35] = Long.valueOf(obj1.getString("ut"));
                                alltodaydeaths[36] = Long.valueOf(obj1.getString("wb"));
                                  indiatodayDeaths = Long.valueOf(obj1.getString("tt"));

                            JSONObject obj2=jsonArray1.getJSONObject(jsonArray1.length()-2);
                            alltodayrecovered[1] =Long.valueOf(obj2.getString("an"));
                            alltodayrecovered[2] =Long.valueOf(obj2.getString("ap"));
                            alltodayrecovered[3] =Long.valueOf(obj2.getString("ar"));
                            alltodayrecovered[4] =Long.valueOf(obj2.getString("as"));
                            alltodayrecovered[5] =Long.valueOf(obj2.getString("br"));
                            alltodayrecovered[6] =Long.valueOf(obj2.getString("ch"));
                            alltodayrecovered[7] =Long.valueOf(obj2.getString("ct"));
                            alltodayrecovered[8] =Long.valueOf(obj2.getString("dl"));
                            alltodayrecovered[9] =Long.valueOf(obj2.getString("dn"));
                            alltodayrecovered[10] =Long.valueOf(obj2.getString("ga"));
                            alltodayrecovered[11] =Long.valueOf(obj2.getString("gj"));
                            alltodayrecovered[12] =Long.valueOf(obj2.getString("hp"));
                            alltodayrecovered[13] =Long.valueOf(obj2.getString("hr"));
                            alltodayrecovered[14] =Long.valueOf(obj2.getString("jh"));
                            alltodayrecovered[15] =Long.valueOf(obj2.getString("jk"));
                            alltodayrecovered[16] =Long.valueOf(obj2.getString("ka"));
                            alltodayrecovered[17] =Long.valueOf(obj2.getString("kl"));
                            alltodayrecovered[18] =Long.valueOf(obj2.getString("la"));
                            alltodayrecovered[19] =Long.valueOf(obj2.getString("ld"));
                            alltodayrecovered[20] =Long.valueOf(obj2.getString("mh"));
                            alltodayrecovered[21] =Long.valueOf(obj2.getString("ml"));
                            alltodayrecovered[22] =Long.valueOf(obj2.getString("mn"));
                            alltodayrecovered[23] =Long.valueOf(obj2.getString("mp"));
                            alltodayrecovered[24] =Long.valueOf(obj2.getString("mz"));
                            alltodayrecovered[25] =Long.valueOf(obj2.getString("nl"));
                            alltodayrecovered[26] =Long.valueOf(obj2.getString("or"));
                            alltodayrecovered[27] =Long.valueOf(obj2.getString("pb"));
                            alltodayrecovered[28] =Long.valueOf(obj2.getString("py"));
                            alltodayrecovered[29] =Long.valueOf(obj2.getString("rj"));
                            alltodayrecovered[30] =Long.valueOf(obj2.getString("sk"));
                            alltodayrecovered[31] =Long.valueOf(obj2.getString("tg"));
                            alltodayrecovered[32] =Long.valueOf(obj2.getString("tn"));
                            alltodayrecovered[33] =Long.valueOf(obj2.getString("tr"));
                            alltodayrecovered[34] =Long.valueOf(obj2.getString("up"));
                            alltodayrecovered[35] =Long.valueOf(obj2.getString("ut"));
                            alltodayrecovered[36] =Long.valueOf(obj2.getString("wb"));
                      indiatodayRecovered=Long.valueOf(obj2.getString("tt"));
                            JSONObject obj3=jsonArray1.getJSONObject(jsonArray1.length()-3);
                            alltodayconfirmed[1] =Long.valueOf(obj3.getString("an"));
                            alltodayconfirmed[2] =Long.valueOf(obj3.getString("ap"));
                            alltodayconfirmed[3] =Long.valueOf(obj3.getString("ar"));
                            alltodayconfirmed[4] =Long.valueOf(obj3.getString("as"));
                            alltodayconfirmed[5] =Long.valueOf(obj3.getString("br"));
                            alltodayconfirmed[6] =Long.valueOf(obj3.getString("ch"));
                            alltodayconfirmed[7] =Long.valueOf(obj3.getString("ct"));
                            alltodayconfirmed[8] =Long.valueOf(obj3.getString("dl"));
                            alltodayconfirmed[9] =Long.valueOf(obj3.getString("dn"));
                            alltodayconfirmed[10] =Long.valueOf(obj3.getString("ga"));
                            alltodayconfirmed[11] =Long.valueOf(obj3.getString("gj"));
                            alltodayconfirmed[12] =Long.valueOf(obj3.getString("hp"));
                            alltodayconfirmed[13] =Long.valueOf(obj3.getString("hr"));
                            alltodayconfirmed[14] =Long.valueOf(obj3.getString("jh"));
                            alltodayconfirmed[15] =Long.valueOf(obj3.getString("jk"));
                            alltodayconfirmed[16] =Long.valueOf(obj3.getString("ka"));
                            alltodayconfirmed[17] =Long.valueOf(obj3.getString("kl"));
                            alltodayconfirmed[18] =Long.valueOf(obj3.getString("la"));
                            alltodayconfirmed[19] =Long.valueOf(obj3.getString("ld"));
                            alltodayconfirmed[20] =Long.valueOf(obj3.getString("mh"));
                            alltodayconfirmed[21] =Long.valueOf(obj3.getString("ml"));
                            alltodayconfirmed[22] =Long.valueOf(obj3.getString("mn"));
                            alltodayconfirmed[23] =Long.valueOf(obj3.getString("mp"));
                            alltodayconfirmed[24] =Long.valueOf(obj3.getString("mz"));
                            alltodayconfirmed[25] =Long.valueOf(obj3.getString("nl"));
                            alltodayconfirmed[26] =Long.valueOf(obj3.getString("or"));
                            alltodayconfirmed[27] =Long.valueOf(obj3.getString("pb"));
                            alltodayconfirmed[28] =Long.valueOf(obj3.getString("py"));
                            alltodayconfirmed[29] =Long.valueOf(obj3.getString("rj"));
                            alltodayconfirmed[30] =Long.valueOf(obj3.getString("sk"));
                            alltodayconfirmed[31] =Long.valueOf(obj3.getString("tg"));
                            alltodayconfirmed[32] =Long.valueOf(obj3.getString("tn"));
                            alltodayconfirmed[33] =Long.valueOf(obj3.getString("tr"));
                            alltodayconfirmed[34] =Long.valueOf(obj3.getString("up"));
                            alltodayconfirmed[35] =Long.valueOf(obj3.getString("ut"));
                            alltodayconfirmed[36] =Long.valueOf(obj3.getString("wb"));
                            indiatodayConfirm=Long.valueOf(obj3.getString("tt"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue1.add(request);
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
                                    JSONObject jsonObject = jsonChild.getJSONObject(k);
                                    // work to be done...
                                    totalConfirmed = totalConfirmed + Long.valueOf(jsonObject.getInt("confirmed"));
                                    totalDeaths = totalDeaths + Long.valueOf(jsonObject.getInt("deceased"));
                                    totalRecovered = totalRecovered + Long.valueOf(jsonObject.getInt("recovered"));
                                    totalActive = totalActive + Long.valueOf(jsonObject.getInt("active"));

                                }

                                countryModel = new CountryModel(state,"",totalConfirmed, alltodayconfirmed[i],totalDeaths,alltodaydeaths[i],totalRecovered,alltodayrecovered[i],totalActive);
                                countryModelsList.add(countryModel);
                            }


                            myCustomAdapter = new MyCustomAdapter(getActivity().getApplicationContext(),countryModelsList);
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
