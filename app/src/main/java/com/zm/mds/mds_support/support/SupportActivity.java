package com.zm.mds.mds_support.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zm.mds.mds_support.General;
import com.zm.mds.mds_support.R;

/**
 * Created by moska on 11.10.2017.
 */

public class SupportActivity extends AppCompatActivity {
    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        setActionBar();

        etText = (EditText) findViewById(R.id.etText);
    }

    private void setActionBar() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rlBack);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        title.setText(getResources().getString(R.string.wsTitle));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clickSend(View view) {
        String text = etText.getText().toString().trim();
        if(text.equals("")) {
            Toast.makeText(this, getString(R.string.toast2), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(General.EMAIL_SUPPORT));
        intent.putExtra("subject", getResources().getString(R.string.wsSubject));
        intent.putExtra("body", text);
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.sendMail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SupportActivity.this, getString(R.string.toast3), Toast.LENGTH_SHORT).show();
        }
    }
}
