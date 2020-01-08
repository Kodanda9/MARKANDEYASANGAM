package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsusFragment extends Fragment {

    private View rootView;
    private EditText edName;
    private EditText edphonenumber;
    private EditText emailValidate ;
    private EditText  edmsg;
    private Button contact_submit;
    String MobilePattern = "[0-9]{10}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public ContactsusFragment() {
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
        } try {

            rootView = inflater.inflate(R.layout.fragment_contactsus, container, false);
            contact_submit=(Button) rootView.findViewById(R.id.contact_submit);

            getActivity().setTitle("Contact Us");
            AppHelper.setupHideleyboard(rootView, getActivity());

            contact_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  String eTmail = emailValidate .getText().toString().trim();
                    edName= rootView.findViewById(R.id.edName1);
                    edphonenumber= rootView.findViewById(R.id.edphonenumber1);
                    emailValidate = rootView.findViewById(R.id.edEmail1);
                    edmsg= rootView.findViewById(R.id.edmsg1);

                    if (edName.getText().toString() != null && !edName.getText().toString().isEmpty()) {
                        if (edphonenumber.getText().toString() != null && !edphonenumber.getText().toString().isEmpty()) {
                            if (emailValidate .getText().toString() != null && !emailValidate .getText().toString().isEmpty()) {
                                if (edmsg.getText().toString() != null && !edmsg.getText().toString().isEmpty()) {
                                    if (edphonenumber.getText().toString().matches(MobilePattern)) {
                                        if (emailValidate.getText().toString().matches(emailPattern)){

                                            ContactAsyncTask contactAsyncTask = new ContactAsyncTask();
                                            contactAsyncTask.execute();

                                        }else {
                                            Toast.makeText(getActivity(),"Invalid Email",Toast.LENGTH_LONG).show();
                                        }
                                    } else if (!edphonenumber.getText().toString().matches(MobilePattern)) {
                                        Toast.makeText(getActivity(), "Please enter valid 10 Digit Number", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Enter Your message or Question ", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getActivity(), "Enter Your Email Address", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
        }
        return rootView;
    }
    private class ContactAsyncTask  extends AsyncTask<String, String, String> {

        private String mobile, email,name,userQuery;

        @Override
        protected void onPreExecute() {
            mobile = edphonenumber.getText().toString();
            email = emailValidate.getText().toString();
            name = edName.getText().toString();
            userQuery = edmsg.getText().toString();

            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj1 = null;
            String content=null;
            try {
                obj1 = new JSONObject();
                obj1.accumulate("mobile", mobile);
                obj1.accumulate("email", email);
                obj1.accumulate("name", name);
                obj1.accumulate("userQuery", userQuery);

                Log.i("Obj-->", obj1.toString());
                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/saveUserQuery", "POST", obj1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
        }
        @Override
        protected void onPostExecute(String resp) {

            try {
                JSONObject jsonObject = new JSONObject(resp);
                if (jsonObject.getString("status").equalsIgnoreCase("Success")){
                    Toast.makeText(getActivity(),"Your Message sent Successfully..",Toast.LENGTH_LONG).show();
                    edName.setText("");
                    edphonenumber.setText("");
                    emailValidate.setText("");
                    edmsg.setText("");
                    edName.requestFocus();

                }else {
                    Toast.makeText(getActivity(),"Enter the Message clearly",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(resp);
        }
    }

}
