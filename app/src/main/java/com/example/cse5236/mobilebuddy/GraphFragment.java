package com.example.cse5236.mobilebuddy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import android.widget.TextView;
import java.util.*;


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
    public void onStart(){
        super.onStart();
        hunger = updateStat("hunger");
        sleepiness = updateStat("sleepiness");
        boredom = updateStat("boredom");
        playfulness = updateStat("playfulness");
        sadness = updateStat("sadness");
        loneliness = updateStat("loneliness");

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

    private int updateStat(String stat){
        String filename = stat + ".dat";
        int statVal = 0;
        try{
            Log.e("file: ","mobilebuddy "+filename);
            FileInputStream in = getActivity().openFileInput(filename);
            Scanner scanner = new Scanner(in);
            String data = scanner.nextLine();
            Log.e("data: ","mobilebuddy " + data);
            statVal = Integer.parseInt(data);
            in.close();
            scanner.close();
        }
        catch(java.io.FileNotFoundException	e){
            statVal = 0;
            FileOutputStream outputStream;
            try {
                outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(Integer.toString(statVal).getBytes());
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }

        Map<String, Integer> idMap = new HashMap<String, Integer>()
        {
            {
                put("hunger", R.id.textHunger);
                put("sleepiness", R.id.textSleepy);
                put("boredom", R.id.textBored);
                put("playfulness", R.id.textPlayful);
                put("sadness", R.id.textSad);
                put("loneliness", R.id.textLonely);
            }
        };
        Log.e("stat: ","mobilebuddy "+stat);
        Log.e("id: ","mobilebuddy "+idMap.get(stat));
        Log.e("view", "mobilebuddy "+ getView());
        TextView statDisplay = (TextView)getView().findViewById(idMap.get(stat));
        Log.e("textview: ","mobilebuddy "+statDisplay);
        statDisplay.setText(stat+": "+statVal);

        return statVal;
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
