package com.example.CoronaMeter;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class deathGraph extends Fragment {
    LineChartView lineChartView;
    TextView chartDate,chartValue;
    int[] yAxisData = new int[30];
    String[] axisData =new String[30];

    public deathGraph() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_death_graph, container, false);

        lineChartView = view.findViewById(R.id.chart);
        chartDate=view.findViewById(R.id.chartDate);
        chartValue=view.findViewById(R.id.chartValue);
        lineChartView.setOnValueTouchListener(new ValueTouchListener());
        linegraph();
        return view;
    }
    private void linegraph()
    {

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        StringRequest request = new StringRequest(Request.Method.GET, "https://api.covid19india.org/states_daily.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray1=jsonObject.getJSONArray("states_daily");
                            int k=0;
                            for(int j=jsonArray1.length()-90;j<=jsonArray1.length()-1;j++) {
                                JSONObject obj1 = jsonArray1.getJSONObject(j);
                                if(obj1.getString("status").equalsIgnoreCase("Deceased"))
                                { yAxisData[k] = Integer.valueOf(obj1.getString("tt"));
                                    axisData[k]=obj1.getString("date").substring(0,6);
                                    k++;
                                }
                            }
                            Line line = new Line(yAxisValues).setColor(Color.parseColor("#fc0000"));
                            for (int i = 0; i < axisData.length; i++) {
                                axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                            }

                            for (int i = 0; i < yAxisData.length; i++) {
                                yAxisValues.add(new PointValue(i, yAxisData[i]));
                            }

                            List lines = new ArrayList();
                            lines.add(line);

                            LineChartData data = new LineChartData();
                            data.setLines(lines);

                            Axis axis = new Axis();
                            axis.setTextSize(16);
                            axis.setTextColor(Color.parseColor("#ffffff"));
                            data.setAxisXBottom(axis);

                            Axis yAxis = new Axis();
                            yAxis.setTextColor(Color.parseColor("#ffffff"));
                            yAxis.setTextSize(14);
                            data.setAxisYLeft(yAxis);
                            chartValue.setText("   "+String.valueOf(yAxisData[yAxisData.length-1]));
                            chartDate.setText(axisData[axisData.length-1]);

                            lineChartView.setLineChartData(data);
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
    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            chartValue.setText("   "+String.valueOf((int)value.getY()));
            chartDate.setText(axisData[(int)value.getX()]);
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}