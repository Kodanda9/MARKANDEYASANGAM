package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.cpetsolu.administrator.markandeyasangam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private View rootView;
    private ViewFlipper viewFlipper;
    int imgae[] = {R.drawable.logo, R.drawable.ms, R.drawable.mss};

    public FirstFragment() {
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
            rootView = inflater.inflate(R.layout.fragment_first, container, false);
            viewFlipper=(ViewFlipper)rootView.findViewById(R.id.view);
            getActivity().setTitle("MARKANDEYA SANGAM");
            for (int image:imgae){
                flip(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }
    private void flip(int image){
        ImageView imageView=new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);
    }

}
