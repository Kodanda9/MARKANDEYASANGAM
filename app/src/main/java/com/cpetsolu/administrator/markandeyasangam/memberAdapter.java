package com.cpetsolu.administrator.markandeyasangam;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class memberAdapter extends BaseAdapter {
    private final ArrayList<memberModel> userItems;
    private memberAdapter activity;
    private LayoutInflater inflater;

public String name;


    public memberAdapter(memberAdapter memberAdapter, ArrayList<memberModel> rowItems) {
        activity=memberAdapter;
        userItems = rowItems;
    }
    public memberAdapter(FragmentActivity activity, ArrayList<memberAdapter> rowItems, ArrayList<memberModel> userItems) {
        this.userItems = userItems;
    }

    public memberAdapter(FragmentActivity activity, ArrayList<memberModel> rowItems, ListView members1, ArrayList<memberModel> userItems) {

        this.userItems = userItems;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
