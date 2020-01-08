package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.activity.ABoutActivity;
import com.cpetsolu.administrator.markandeyasangam.activity.ContactUsActivity;
import com.cpetsolu.administrator.markandeyasangam.activity.MainActivity;
import com.cpetsolu.administrator.markandeyasangam.activity.ServiceActivity;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private View rootView;

    public DetailsFragment() {
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

            rootView = inflater.inflate(R.layout.fragment_details, container, false);
            // rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView, getActivity());
            TextView tv1=rootView.findViewById(R.id.about);
            TextView tv2=rootView.findViewById(R.id.contact);
            TextView tv3=rootView.findViewById(R.id.servicce);
            TextView tv4=rootView.findViewById(R.id.home11);

            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),ABoutActivity.class);
                    startActivity(i);
                }
            });
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),ContactUsActivity.class);
                    startActivity(i);
                }
            });
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),ServiceActivity.class);
                    startActivity(i);
                }
            });
            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),MainActivity.class);
                    startActivity(i);
                }
            });
        } catch (Exception e) {
        }
        return rootView;
    }
}
