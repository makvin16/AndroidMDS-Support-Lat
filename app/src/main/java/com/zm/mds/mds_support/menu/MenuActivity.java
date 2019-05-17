package com.zm.mds.mds_support.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.email.EmailActivity;
import com.zm.mds.mds_support.model.Shop;
import com.zm.mds.mds_support.report.ReportActivity;
import com.zm.mds.mds_support.support.SupportActivity;

/**
 * Created by moska on 10.10.2017.
 */

public class MenuActivity extends AppCompatActivity {

    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DataBaseHelper helper = new DataBaseHelper(this);
        if(helper.getCountShop() != 0) {
            shop = helper.getShop();
            if(shop != null) setActionBar();
        }
    }

    private void setActionBar() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rlBack);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        title.setText(shop.getName());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clickShowReport(View view) {
        startActivity(new Intent(MenuActivity.this, ReportActivity.class));
    }

    public void clickSupport(View view) {
        startActivity(new Intent(MenuActivity.this, SupportActivity.class));
    }

    public void clickAddress(View view) {
        startActivity(new Intent(MenuActivity.this, EmailActivity.class));
    }
}
