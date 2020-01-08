package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.activity.ForgetOtp;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMobiFrag extends Fragment {

    private View rootView;
    private Button changenumber1button;
    private TextView error;
    private EditText changenumer1,changenumbernew;
    String MobilePattern = "[0-9]{10}";

    public ChangeMobiFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        } try {

            rootView = inflater.inflate(R.layout.fragment_change_mobi, container, false);
            AppHelper.setupHideleyboard(rootView, getActivity());
            changenumber1button = rootView.findViewById(R.id.changenumber1button);
            changenumer1 = rootView.findViewById(R.id.changenumer1);
            changenumbernew = rootView.findViewById(R.id.changenumernew);
            error = rootView.findViewById(R.id.error);

            changenumber1button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (changenumer1.getText().toString() != null && !changenumer1.getText().toString().isEmpty()) {

                        if (changenumer1.getText().toString().matches(MobilePattern)) {

                            ChangenumberAsyncTaskRunner changenumberAsyncTaskRunner = new ChangenumberAsyncTaskRunner();
                            changenumberAsyncTaskRunner.execute();

                        } else if (!changenumer1.getText().toString().matches(MobilePattern)) {
                            Toast.makeText(getActivity(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
                            changenumer1.setText("");
                            changenumer1.requestFocus();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Enter Phone number", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }
        private class ChangenumberAsyncTaskRunner extends AsyncTask<String, String, String> {

            String mobile;
            String mobilenew;

            @Override
            protected void onPreExecute() {
                mobile = changenumer1.getText().toString();
                mobilenew = changenumbernew.getText().toString();
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {

                JSONObject obj = null;
                String resp = null;
                try {
                    obj = new JSONObject();

                    obj.accumulate("mobileNo", mobilenew);
                    obj.accumulate("oldMobileNo", mobile);
                    resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/changeMobileNo", "POST", obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                    e.getMessage();
                }
                return resp;
            }

            @Override
            protected void onPostExecute(String resp) {
                Log.i("Response:->", resp);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resp);

                    if (jsonObject.getString("status").equalsIgnoreCase("SUCCESS")) {
                        Toast.makeText(getActivity(), "OTP sent Successfully", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getActivity(), ForgetOtp.class);
                        i.putExtra("changenumer1", changenumer1.getText().toString());
                        i.putExtra("changenumbernew", changenumbernew.getText().toString());

                        startActivity(i);
                    } else {
                        error.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "otp is not sent Please try again later.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onPostExecute(resp);
            }
        }
}
