package com.zm.mds.mds_support.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zm.mds.mds_support.R;

/**
 * Created by moska on 09.10.2017.
 */

public class FinishOrderActivity extends AppCompatActivity {
    private static final String TAG = FinishOrderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);
    }
}
