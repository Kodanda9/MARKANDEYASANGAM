package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.activity.Forgotpassword;
import com.cpetsolu.administrator.markandeyasangam.activity.NavigationActivity;
import com.cpetsolu.administrator.markandeyasangam.activity.SignupActivity;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private View rootView;
    private TextView fbook, acc, sin, forgetpassword,invalid;
    private View localView;
    private Button signin;
    private Button signup;
    public ListView listview;
    private Dialog mDialog;
            private TextInputEditText pswd,phoneno;
    private TextView forgot;
    String MobilePattern = "[0-9]{10}";
    String startpartten="[6,7,8,9]";

    public LoginFragment() {
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
        }try {

            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            getActivity().setTitle("Login/Registration");
            AppHelper.setupHideleyboard(rootView, getActivity());

            invalid=(TextView)rootView.findViewById(R.id.invalid);

            forgetpassword = rootView.findViewById(R.id.forgetpassword);
            forgetpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Forgotpassword.class);
                    startActivity(intent);
                }
            });
            signin = (Button)rootView.findViewById(R.id.signin);
            signup = (Button)rootView.findViewById(R.id.signup);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getActivity(), SignupActivity.class);
                    startActivity(it);
                }
            });

            acc = (TextView)rootView.findViewById(R.id.act);
            phoneno = (TextInputEditText) rootView.findViewById(R.id.phonenumber1);
            pswd = (TextInputEditText) rootView.findViewById(R.id.pswd1);

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (phoneno.getText().toString().trim().isEmpty()){
                        phoneno.setError("Enter Number");
                    }else {
                            if (phoneno.getText().toString().matches(MobilePattern)) {

                                if (pswd.getText().toString().trim().isEmpty()){
                                    pswd.setError("Enter Password");
                                }else {

                                    LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
                                    loginAsyncTask.execute();

                                    phoneno.setText("");
                                    pswd.setText("");
                                    phoneno.requestFocus();
                                }
                            } else if (!phoneno.getText().toString().matches(MobilePattern)) {
                                Toast.makeText(getActivity(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
                                phoneno.setText("");
                                phoneno.requestFocus();
                            }
                        }
                }
            });

        } catch (Exception e) {
        }
        return rootView;
    }
    private class LoginAsyncTask extends AsyncTask<String, String, String> {

        private String mobno, password;
        ProgressDialog progressDialog;
        String ProfileRole;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "loading...");
            mobno = phoneno.getText().toString();
            password = pswd.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj = null;
            String content=null;
            try {
                obj = new JSONObject();
                obj.accumulate("mobile", mobno);
                obj.accumulate("password", password);


                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/checkLogin", "POST", obj);

                Log.i("checkObj-->", content.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
        }
        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            try {
                JSONArray mainArray = new JSONArray(s);

                JSONObject stausObj = mainArray.getJSONObject(0);

                JSONObject detailsObj = mainArray.getJSONObject(1);

                if (stausObj.getString("Status").equalsIgnoreCase("Success")) {

                    Toast.makeText(getActivity(), "Welcome "+ SessinSave.getsessin("name",getActivity())+SessinSave.getsessin("surName",getActivity()), Toast.LENGTH_LONG).show();

                    SessinSave.saveSession("LogInObj", detailsObj.toString(), getActivity());

                    SessinSave.saveSession("id", detailsObj.getString("id"), getActivity());
                    SessinSave.saveSession("surName", detailsObj.getString("surName"), getActivity());
                    SessinSave.saveSession("name", detailsObj.getString("name"), getActivity());
                    SessinSave.saveSession("gender", detailsObj.getString("gender"), getActivity());
                    SessinSave.saveSession("bloodGroup", detailsObj.getString("bloodGroup"), getActivity());
                    SessinSave.saveSession("locality", detailsObj.getString("locality"), getActivity());
                    SessinSave.saveSession("assembly", detailsObj.getString("assembly"), getActivity());
                    SessinSave.saveSession("parliament", detailsObj.getString("parliament"), getActivity());
                    SessinSave.saveSession("mobile", detailsObj.getString("mobile"), getActivity());
                    SessinSave.saveSession("password", detailsObj.getString("password"), getActivity());
                    SessinSave.saveSession("addressLine1", detailsObj.getString("addressLine1"), getActivity());
                    SessinSave.saveSession("country", detailsObj.getString("country"), getActivity());
                    SessinSave.saveSession("district", detailsObj.getString("district"), getActivity());
                    SessinSave.saveSession("email", detailsObj.getString("email"), getActivity());
                    SessinSave.saveSession("state", detailsObj.getString("state"), getActivity());
                    SessinSave.saveSession("aboutYou", detailsObj.getString("aboutYou"), getActivity());

                    SessinSave.saveSession("emailView", detailsObj.getString("emailView"), getActivity());
                    SessinSave.saveSession("aboutYouView", detailsObj.getString("aboutYouView"), getActivity());
                    SessinSave.saveSession("adminComments", detailsObj.getString("adminComments"), getActivity());
                    SessinSave.saveSession("mobileView", detailsObj.getString("mobileView"), getActivity());
                    SessinSave.saveSession("status", detailsObj.getString("status"), getActivity());
                    SessinSave.saveSession("subDistrict", detailsObj.getString("subDistrict"), getActivity());
                    SessinSave.saveSession("role", detailsObj.getString("role"), getActivity());
                    SessinSave.saveSession("dob", detailsObj.getString("dob"), getActivity());
                    SessinSave.saveSession("memberStatus", detailsObj.getString("memberStatus"), getActivity());

                    Intent in = new Intent(getActivity(), NavigationActivity.class);
                    startActivity(in);

                    getActivity().finish();

                }else if(stausObj.getString("STATUS").equalsIgnoreCase("ERROR")&&
                        (stausObj.getString("Report").equalsIgnoreCase("InValid Credentials"))){

                    invalid.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Invalid Credential ", Toast.LENGTH_LONG).show();
                }
                else {
                    invalid.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Invalid Credential ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
