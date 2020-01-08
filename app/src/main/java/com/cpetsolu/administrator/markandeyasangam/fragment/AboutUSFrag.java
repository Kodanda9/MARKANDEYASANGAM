package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpetsolu.administrator.markandeyasangam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUSFrag extends Fragment {

    private View rootView;

    public AboutUSFrag() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_about_u2, container, false);
            getActivity().setTitle("About Us");

        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }
}
