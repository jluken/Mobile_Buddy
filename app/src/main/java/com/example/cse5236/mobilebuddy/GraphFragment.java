package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.*;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GraphFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphFragment extends Fragment {
    private int hunger;
    private int sleepiness;
    private int boredom;
    private int playfulness;
    private int sadness;
    private int loneliness;

    private OnFragmentInteractionListener mListener;

    public GraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphFragment newInstance() {
        Log.e("test: ","mobilebuddy newInstance");
        GraphFragment fragment = new GraphFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("test: ","mobilebuddy onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("test: ","mobilebuddy onCreateView");
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateGraph();
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//    public static void updateGraph(){
//        updateGraphData();
//    }

    public void updateGraph(){
        HorizontalBarChart barChart = (HorizontalBarChart) getView().findViewById(R.id.barchart);
        ArrayList<String> emotions = new ArrayList<String>();
        //emotions.add("Hunger");
        emotions.add("Sleepiness");
        emotions.add("Boredom");
        emotions.add("Playfulness");
        emotions.add("Sadness");
        emotions.add("Loneliness");

        Activity active = getActivity();
        //hunger = HomeScreenActivity.getStat(active, "hunger");
        sleepiness = HomeScreenActivity.getStat(active,"sleepiness");
        boredom = HomeScreenActivity.getStat(active,"boredom");
        playfulness = HomeScreenActivity.getStat(active,"playfulness");
        sadness = HomeScreenActivity.getStat(active,"sadness");
        loneliness = HomeScreenActivity.getStat(active,"loneliness");


        List<BarEntry> emotionStats = new ArrayList<BarEntry>();
        //emotionStats.add(new BarEntry(hunger, 0));
        emotionStats.add(new BarEntry(sleepiness, 0));
        emotionStats.add(new BarEntry(boredom, 1));
        emotionStats.add(new BarEntry(playfulness, 2));
        emotionStats.add(new BarEntry(sadness, 3));
        emotionStats.add(new BarEntry(loneliness, 4));

        BarDataSet bardataset = new BarDataSet(emotionStats, "");
        BarData data = new BarData(emotions, bardataset);
        barChart.setData(data);
        barChart.setDescription("");
        YAxis yRight = barChart.getAxisRight();
        yRight.setAxisMaxValue(100);
        YAxis yLeft = barChart.getAxisLeft();
        yLeft.setAxisMaxValue(100);

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
