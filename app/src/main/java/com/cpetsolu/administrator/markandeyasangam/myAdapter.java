package com.cpetsolu.administrator.markandeyasangam;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.activity.MainActivity;

import java.util.List;

public class myAdapter extends BaseAdapter {

    FragmentActivity activity;
    List<Rowdata> rowdatas;

    public myAdapter(FragmentActivity activity, List<Rowdata> rowdatas) {
        this.activity = activity;
        this.rowdatas = rowdatas;
    }

    @Override
    public int getCount() {
        return rowdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return rowdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView id;
        TextView surname;
        TextView mobile;
        TextView email;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.roedata, null);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) view.findViewById(R.id.id);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.mobile = (TextView) view.findViewById(R.id.mobilesave);
            viewHolder.email = (TextView) view.findViewById(R.id.emailsave);

            Rowdata rowdata = rowdatas.get(i);
            viewHolder.id.setText(rowdata.getId());
            viewHolder.name.setText(rowdata.getName());
            viewHolder.mobile.setText(rowdata.getMobile());
            viewHolder.email.setText(rowdata.getEmail());
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Row was clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

     }

}
