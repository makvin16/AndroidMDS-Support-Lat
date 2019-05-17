package com.zm.mds.mds_support.password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zm.mds.mds_support.General;
import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.api.ApiService;
import com.zm.mds.mds_support.api.Client;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.model.Shop;
import com.zm.mds.mds_support.order.CreateOrderActivity;
import com.zm.mds.mds_support.utils.InternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moska on 09.10.2017.
 */

public class PasswordActivity extends AppCompatActivity {
    private static final String TAG = PasswordActivity.class.getSimpleName();

    private RelativeLayout rlScreenLoad;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        rlScreenLoad = (RelativeLayout) findViewById(R.id.rlScreenLoad);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(etPassword.getText().toString().length() == General.MAX_LENGHT_PASSWORD) {
                    if(InternetConnection.checkConnection(getApplicationContext())) {
                        checkPassword(etPassword.getText().toString());
                    }else {
                        showError("Нет подключения к интернету");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void checkPassword(String password) {
        hideKeyboard();
        rlScreenLoad.setVisibility(View.VISIBLE);
        ApiService api = Client.getApiService();
        Call<Shop> call = api.apiShopPassword(password);
        if(call == null) {
            showError("Ошибка подключения");
            rlScreenLoad.setVisibility(View.GONE);
            return;
        }
        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (response.isSuccessful()) {
                    Shop result = response.body();
                    if(result == null) {
                        incorrectPassword();
                        rlScreenLoad.setVisibility(View.GONE);
                        return;
                    }
                    Log.d(TAG, "objId:" + result.getObjId());
                    Log.d(TAG, "password:" + result.getPassword());
                    Log.d(TAG, "name:" + result.getName());
                    Log.d(TAG, "orgId:" + result.getOrganizationId());
                    DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                    helper.createShop(result);
                    if(helper.getCountShop() != 0) {
                        Intent intent = new Intent(PasswordActivity.this, CreateOrderActivity.class);
                        startActivity(intent);
                        finish();
                    } else incorrectPassword();
                    rlScreenLoad.setVisibility(View.GONE);
                } else {
                    showError("Ошибка сервера");
                    rlScreenLoad.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                t.printStackTrace();
                showError("Ошибка подключения");
                rlScreenLoad.setVisibility(View.GONE);
            }
        });
    }

//    private void testPassword(String password) {
//        if(!password.equals("0000")) incorrectPassword();
//        else {
//            DataBaseHelper helper = new DataBaseHelper(this);
//            Shop shop = new Shop();
//            shop.setObjId(19);
//            shop.setAddress("Минск, пересечение Логойского тр-та и МКАД ТРЦ Экспобел");
//            shop.setPassword("0000");
//            shop.setName("Colin's");
//            shop.setCity("Минск");
//            shop.setPhone("+375 (17) 2379949");
//            shop.setShedule("пн-вс 10:00 - 22:00");
//            shop.setLatitude("53.96408952");
//            shop.setLongitude("27.62364149");
//            helper.createShop(shop);
//            if(helper.getCountShop() != 0) {
//                Intent intent = new Intent(PasswordActivity.this, CreateOrderActivity.class);
//                startActivity(intent);
//                finish();
//            } else incorrectPassword();
//        }
//    }

    private void incorrectPassword() {
        Toast.makeText(this, getResources().getString(R.string.pToast1), Toast.LENGTH_SHORT).show();
        etPassword.setText("");
    }

    private void showError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        etPassword.setText("");
    }

}
