package com.zm.mds.mds_support.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.model.Report;

import java.util.ArrayList;

/**
 * Created by moska on 10.10.2017.
 */

public class ReportAdapter extends ArrayAdapter<Report> {
    private ArrayList<Report> reportList;
    private Context context;
    private LayoutInflater inflater;

    public ReportAdapter(Context context, ArrayList<Report> obj) {
        super(context, 0, obj);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.reportList = obj;
    }

    @Override
    public Report getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if(convertView == null) {
            View view = inflater.inflate(R.layout.row_report, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        Report item = getItem(position);

        vh.tvNamePercentage.setText(item.getFullName() + " " + item.getPercentage() + "%");
        vh.tvSum.setText("Summa " + item.getSum() + "€");
        vh.tvDate.setText(item.getDate() + " " + item.getTime());
        vh.tvPayment.setText("Apmaksai: " + item.getPayment() + "€");
        vh.tvNumber.setText("№" + item.getNumber());

        if(item.getNumber() != null)
            if(!item.getNumber().equals(""))
                vh.tvNumber.setVisibility(View.VISIBLE);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView tvNamePercentage;
        public final TextView tvSum;
        public final TextView tvDate;
        public final TextView tvPayment;
        public final TextView tvNumber;

        public ViewHolder(RelativeLayout rootView, TextView tvNamePercentage, TextView tvSum,
                          TextView tvDate, TextView tvPayment, TextView tvNumber) {
            this.rootView = rootView;
            this.tvNamePercentage = tvNamePercentage;
            this.tvSum = tvSum;
            this.tvDate = tvDate;
            this.tvPayment = tvPayment;
            this.tvNumber = tvNumber;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView tvNamePercentage = (TextView) rootView.findViewById(R.id.tvNamePercentage);
            TextView tvSum = (TextView) rootView.findViewById(R.id.tvSum);
            TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);
            TextView tvPayment = (TextView) rootView.findViewById(R.id.tvPayment);
            TextView tvNumber = (TextView) rootView.findViewById(R.id.tvNumber);
            return new ViewHolder(rootView, tvNamePercentage, tvSum, tvDate, tvPayment, tvNumber);
        }
    }
}
