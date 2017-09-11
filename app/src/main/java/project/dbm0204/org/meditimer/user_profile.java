package project.dbm0204.org.meditimer;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;

/**
 * Created by dbm0204 on 9/10/17.
 */

public class user_profile extends Fragment {
    private static String ARG_PARAM1="param1";
    private static String ARG_PARAM2="param2";
    private String mParam1;
    private String mParam2;
    private onFragmentInteractionListener mListener;

    public static user_profile newInstance(String mParam1, String mParam2){
        user_profile fragment = new user_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,mParam1);
        args.putString(ARG_PARAM2,mParam2);
        fragment.setArguments(args);
        return fragment;
    }

    public user_profile(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_profile,container,false);
    }

    public void onButtonPressed(Uri uri){
        if(mListener !=null){
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener{
        public void onFragmentInteraction(Uri uri);

    }


}
