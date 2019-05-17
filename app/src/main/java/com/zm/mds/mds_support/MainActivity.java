package com.zm.mds.mds_support;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.splunk.mint.Mint;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.order.CreateOrderActivity;
import com.zm.mds.mds_support.password.PasswordActivity;


public class MainActivity extends AppCompatActivity {

    private Intent intentPassword, intentCreateOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentPassword = new Intent(MainActivity.this, PasswordActivity.class);
        intentCreateOrder = new Intent(MainActivity.this, CreateOrderActivity.class);

        Mint.initAndStartSession(this.getApplication(), "199cfc19");
        isCheckPermission();

        goToIntent();
    }

    private boolean isCheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void goToIntent() {
        DataBaseHelper helper = new DataBaseHelper(this);
        if(helper.getCountShop() == 0) {
            startActivity(intentPassword);
        } else {
            startActivity(intentCreateOrder);
        }
        finish();
    }
}
