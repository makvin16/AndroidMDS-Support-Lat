package com.zm.mds.mds_support.email;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zm.mds.mds_support.General;
import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.model.Shop;

import java.util.ArrayList;

/**
 * Created by moska on 10.10.2017.
 */

public class EmailActivity extends AppCompatActivity {

    private Shop shop;
    private DataBaseHelper helper;

    private RelativeLayout[] arrField;
    private EditText[] editText;
    private ArrayList<String> arrEmail;

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        helper = new DataBaseHelper(this);
        if (helper.getCountShop() != 0) {
            shop = helper.getShop();
            if (shop != null) setActionBar();
        }

        container = (LinearLayout) findViewById(R.id.container);

        arrEmail = helper.getEmail();
        addFields();
    }

    private void setActionBar() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rlBack);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        RelativeLayout save = (RelativeLayout) findViewById(R.id.rlSave);

        title.setText(shop.getName());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkField()) {
                    helper.clearEmail();
                    for(EditText et : editText) {
                        helper.createEmail(et.getText().toString().trim());
                    }
                    finish();
                }
            }
        });
    }

    private void addFields() {
        arrField = new RelativeLayout[arrEmail.size()];
        editText = new EditText[arrEmail.size()];
        for(int i = 0; i < arrField.length; i++) {
            arrField[i] = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.row_email, container, false);
            ImageView img = (ImageView) arrField[i].findViewById(R.id.ivRemove);
            editText[i] = (EditText) arrField[i].findViewById(R.id.etEmail);
            img.setId(i);
            editText[i].setId(i);
            editText[i].setText(arrEmail.get(i));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    updateField(id);
                }
            });
            container.addView(arrField[i]);
        }
    }

    private boolean checkField() {
        for(int i = 0; i < arrField.length; i++) {
            if(!General.validate(editText[i].getText().toString().trim())) {
                Toast.makeText(this, getString(R.string.toast4), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void updateField(int id) {
        container.removeAllViews();
        arrEmail.remove(id);
        addFields();
    }

    public void clickAdd(View view) {
        container.removeAllViews();
        arrEmail.add("");
        addFields();
    }
}
