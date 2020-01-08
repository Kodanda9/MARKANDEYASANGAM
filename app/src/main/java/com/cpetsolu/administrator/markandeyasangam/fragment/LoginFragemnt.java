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
public class LoginFragemnt extends Fragment {


    public LoginFragemnt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_fragemnt, container, false);
    }

}
