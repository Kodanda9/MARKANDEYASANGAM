package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.activity.BUSINESSDETAILS;
import com.cpetsolu.administrator.markandeyasangam.activity.EMPLOYMENTDETAILS;
import com.cpetsolu.administrator.markandeyasangam.activity.EditProfile;
import com.cpetsolu.administrator.markandeyasangam.activity.EducationalDetailsActivity;
import com.cpetsolu.administrator.markandeyasangam.activity.UploadImage;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragemnt extends Fragment {

    private View rootView;

    public ProfileFragemnt() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {

            rootView = inflater.inflate(R.layout.fragment_profile_fragemnt, container, false);
            // rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView, getActivity());

            TextView education=rootView.findViewById(R.id.educationaldetails);
            education.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),EducationalDetailsActivity.class);
                    startActivity(i);
                }
            });
            TextView edit=rootView.findViewById(R.id.editprofile);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),EditProfile.class);
                    startActivity(i);
                }
            });
            TextView business=rootView.findViewById(R.id.businessdetails1);
            business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),BUSINESSDETAILS.class);
                    startActivity(i);
                }
            });
            TextView employeement=rootView.findViewById(R.id.employementdetails1);
            employeement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),EMPLOYMENTDETAILS.class);
                    startActivity(i);
                }
            });
            TextView upload=rootView.findViewById(R.id.imageupload1);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),UploadImage.class);
                    startActivity(i);
                }
            });
        } catch (Exception e) {
        }
        return rootView;
    }

}
