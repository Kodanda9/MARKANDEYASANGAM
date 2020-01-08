package com.cpetsolu.administrator.markandeyasangam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View rootView;
    private TextView Id,Name,Surname,Gender,Mobile,Email,Bloodgroup;
    private ImageView image,camera_action;
    public HomeFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {

                if (rootView != null) {
                    ViewGroup parent = (ViewGroup) rootView.getParent();
                    if (parent != null)
                        parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            AppHelper.setupHideleyboard(rootView, getActivity());

            Id=rootView.findViewById(R.id.id);
            image=rootView.findViewById(R.id.memberphoto);
            camera_action=rootView.findViewById(R.id.camera_action);
            //Surname=rootView.findViewById(R.id.surName);
            Name=rootView.findViewById(R.id.name);
            Gender=rootView.findViewById(R.id.gender);
            Mobile=rootView.findViewById(R.id.mobile);
            Email=rootView.findViewById(R.id.email);
            Bloodgroup=rootView.findViewById(R.id.bloodGroup);

            Id.setText(SessinSave.getsessin("id",getActivity()));
            Name.setText(SessinSave.getsessin("name",getActivity()));
            Gender.setText(SessinSave.getsessin("gender",getActivity()));
            Mobile.setText(SessinSave.getsessin("mobile",getActivity()));
            Email.setText(SessinSave.getsessin("email",getActivity()));
            Bloodgroup.setText(SessinSave.getsessin("bloodGroup",getActivity()));

/*
            camera_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),UploadInServer.class);
                    startActivity(intent);
                }
            });
*/

            Id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"hi",Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }
    }


