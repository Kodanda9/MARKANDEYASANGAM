package com.cpetsolu.administrator.markandeyasangam.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.Rowdata;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;
import com.cpetsolu.administrator.markandeyasangam.myAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends Fragment {

    private View rootView;
    private TextView Name, Surname,id;
    private ListView members1;
    List<Rowdata> rowdatas;
    private SwipeRefreshLayout upload_swipe_refresh_layout;

    TextView members,tv;
    private ArrayAdapter<String> arrayAdapter;

    public MemberFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_member, container, false);

            AppHelper.setupHideleyboard(rootView, getActivity());

            members1= rootView.findViewById(R.id.member1);
            upload_swipe_refresh_layout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);

            upload_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    AddFRcAsyncTask runner=new AddFRcAsyncTask();
                    runner.execute();

                    upload_swipe_refresh_layout.setRefreshing(false);
                }
            });
            AddFRcAsyncTask runner=new AddFRcAsyncTask();
            runner.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
                return rootView;
    }
    public  class AddFRcAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            String content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/viewMembers","GET", null);

            return content;
        }
        @Override
        protected void onPostExecute(String content) {

            rowdatas = new ArrayList<Rowdata>();
            try {
                JSONArray jsonArray=new JSONArray(content);

                if (jsonArray.length()>0) {

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Rowdata rowdata = new Rowdata();

                        rowdata.setId(jsonObject.getString("id"));
                        rowdata.setName(jsonObject.getString("name"));
                        rowdata.setMobile(jsonObject.getString("mobile"));
                        rowdata.setEmail(jsonObject.getString("email"));
                        rowdatas.add(rowdata);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myAdapter ma = new myAdapter(getActivity(), rowdatas);
            ma.notifyDataSetChanged();
            members1.setAdapter(ma);
            super.onPostExecute(content);
        }
    }
}