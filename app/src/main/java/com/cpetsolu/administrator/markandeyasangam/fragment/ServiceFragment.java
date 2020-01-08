package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private View rootView;
    private TextView web;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);

        }
        try {

            rootView = inflater.inflate(R.layout.fragment_, container, false);
            getActivity().setTitle("Services");
            AppHelper.setupHideleyboard(rootView, getActivity());
        } catch (Exception e) {
        }
        return rootView;
    }
}


