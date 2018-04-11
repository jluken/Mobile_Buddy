package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuddyDisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuddyDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuddyDisplayFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public BuddyDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BuddyDisplayFragment.
     */
    public static BuddyDisplayFragment newInstance() {
        BuddyDisplayFragment fragment = new BuddyDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buddy_display, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        updateBuddy();


    }

    public void updateBuddy(){
        Log.e("update buddy", "mobilebuddy: start");
        ImageView mImageView;
        mImageView = (ImageView) (ImageView)getView().findViewById(R.id.buddyImage);
        Activity active = getActivity();
        int hunger = HomeScreenActivity.getStat(active, "hunger");
        int sleepiness = HomeScreenActivity.getStat(active,"sleepiness");
        int boredom = HomeScreenActivity.getStat(active,"boredom");
        int playfulness = HomeScreenActivity.getStat(active,"playfulness");
        int sadness = HomeScreenActivity.getStat(active,"sadness");
        int loneliness = HomeScreenActivity.getStat(active,"loneliness");

        int[] emotions = {hunger, sleepiness, boredom, playfulness, sadness, loneliness};
        int worstEmotion = -1;
        int biggest = 0;
        for (int i = 0; i < emotions.length; i++){
            if (emotions[i] >= biggest){
                biggest = emotions[i];
                worstEmotion = i;
            }
        }
        if (biggest > 80){
            if(worstEmotion == 0){
                mImageView.setImageResource(R.drawable.buddyhungry);
            }
            else if(worstEmotion == 1){
                mImageView.setImageResource(R.drawable.buddysleepy);
            }
            else if(worstEmotion == 2){
                mImageView.setImageResource(R.drawable.buddybored);
            }
            else if(worstEmotion == 3){
                mImageView.setImageResource(R.drawable.buddyplayful);
            }
            else if(worstEmotion == 4){
                mImageView.setImageResource(R.drawable.buddysad);
            }
            else if(worstEmotion == 5){
                mImageView.setImageResource(R.drawable.buddylonely);
            }
            else {
                mImageView.setImageResource(R.drawable.buddybase);
            }
        }
        else {
            mImageView.setImageResource(R.drawable.buddybase);
        }

    }

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
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
