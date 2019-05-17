package com.zm.mds.mds_support.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.menu.MenuActivity;
import com.zm.mds.mds_support.model.Shop;
import com.zm.mds.mds_support.password.PasswordActivity;

/**
 * Created by moska on 09.10.2017.
 */

public class CreateOrderActivity extends AppCompatActivity {
    private static final String TAG = CreateOrderActivity.class.getSimpleName();
    private static final int REQUEST_SCANNER = 1;
    private static final int REQUEST_RESULT = 2;

    private Shop shop;
    private EditText etSum, etNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        DataBaseHelper helper = new DataBaseHelper(this);
        if(helper.getCountShop() != 0) {
            shop = helper.getShop();
            if(shop != null) setActionBar();
        }

        etSum = (EditText) findViewById(R.id.etSum);
        etNumber = (EditText) findViewById(R.id.etNumber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, getResources().getString(R.string.coToast2), Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                goToIntent(result.getContents());
            }
        } else {
            if(resultCode == RESULT_OK && data != null) {
                if(requestCode == REQUEST_SCANNER) {
                    String barcode = data.getStringExtra("barcode");
                    goToIntent(barcode);
                }
                if(requestCode == REQUEST_RESULT) {
                    etSum.setText("");
                    etNumber.setText("");
                    if(data.getBooleanExtra("error", false)) {
                        Toast.makeText(this, getText(R.string.toast1), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void setActionBar() {
        ImageView menu = (ImageView) findViewById(R.id.ivMenu);
        RelativeLayout exit = (RelativeLayout) findViewById(R.id.rlExit);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        title.setText(shop.getName());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                helper.clearShop();
                startActivity(new Intent(CreateOrderActivity.this, PasswordActivity.class));
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateOrderActivity.this, MenuActivity.class));
            }
        });
    }

    public void clickScan(View view) {
        if(etSum.getText().toString().trim().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.coToast1), Toast.LENGTH_SHORT).show();
            return;
        }
        //startActivityForResult(new Intent(CreateOrderActivity.this, ScannerActivity.class), REQUEST_SCANNER);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(getResources().getString(R.string.coScanText));
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    private void goToIntent(String barcode) {
        Intent intent = new Intent(CreateOrderActivity.this, ResultOrderActivity.class);
        intent.putExtra("barcode", barcode);
        intent.putExtra("sum", etSum.getText().toString().trim());
        intent.putExtra("number", etNumber.getText().toString().trim());
        startActivityForResult(intent, REQUEST_RESULT);
    }
}
