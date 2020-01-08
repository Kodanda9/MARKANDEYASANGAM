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
import com.cpetsolu.administrator.markandeyasangam.activity.ChangePasswordOtpCheck;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFrag extends Fragment {

    private View rootView;

    private EditText currentpass;
    private EditText newpass;
    private TextView error2;
    private EditText confirmnewpass;
    private String   curpass,npass,conpass;
    private Button changepassword1button;

    public ChangePasswordFrag() {
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

            rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
            AppHelper.setupHideleyboard(rootView, getActivity());
            String pass = SessinSave.getsessin("password", getActivity());



            changepassword1button = rootView.findViewById(R.id.change1button11);
            error2 = rootView.findViewById(R.id.error2);

            changepassword1button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    currentpass =rootView. findViewById(R.id.changepassword1);
                    newpass =rootView. findViewById(R.id.changepassword2);
                    confirmnewpass =rootView. findViewById(R.id.changepassword3);

                    if (currentpass.getText().toString() != null && !currentpass.getText().toString().isEmpty()) {

                        if (newpass.getText().toString() != null && !newpass.getText().toString().isEmpty()) {

                            if (confirmnewpass.getText().toString() != null && !confirmnewpass.getText().toString().isEmpty()) {

                                String c = currentpass.getText().toString();
                                String a = newpass.getText().toString();
                                String b = confirmnewpass.getText().toString();

                                if (a.equals(b)) {
                                    ChangepasswordAsyncTaskRunner changenumberAsyncTaskRunner = new ChangepasswordAsyncTaskRunner();
                                    changenumberAsyncTaskRunner.execute();
                                } else {
                                    Toast.makeText(getActivity(), "password not match", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), "Please enter Confirm Password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please enter new Password", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Enter Current password", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }
        private class ChangepasswordAsyncTaskRunner extends AsyncTask<String,String,String> {

            private String currentPw, newPassword,confirmPw;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                currentPw = currentpass.getText().toString();
                newPassword = newpass.getText().toString();
                confirmPw = confirmnewpass.getText().toString();
            }
            @Override
            protected String doInBackground(String... strings) {

                JSONObject obj1 = null;
                String content=null;
                try {
                    obj1 = new JSONObject();
                    obj1.accumulate("currentPw", currentPw);
                    obj1.accumulate("newPassword", newPassword);
                    obj1.accumulate("confirmPw", confirmPw);

                    Log.i("Obj-->", obj1.toString());
                    content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/changePassword/"+SessinSave.getsessin("id",getActivity() ), "POST", obj1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return content;
            }

            @Override
            protected void onPostExecute(String content) {

                try {
                    JSONObject mainObj = new JSONObject(content);
                    if (mainObj.getString("status").equalsIgnoreCase("SUCCESS")) {

                        Toast.makeText(getActivity(), "Otp Sent to your Number", Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Enter Otp for Change Password", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getActivity(), ChangePasswordOtpCheck.class);
                        startActivity(i);
                    } else {
                        error2.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Otp Not Sent", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onPostExecute(content);
            }
    }
}
