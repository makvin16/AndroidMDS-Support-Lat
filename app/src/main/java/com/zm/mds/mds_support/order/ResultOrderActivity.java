package com.zm.mds.mds_support.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.api.ApiService;
import com.zm.mds.mds_support.api.Client;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.model.Card;
import com.zm.mds.mds_support.model.Report;
import com.zm.mds.mds_support.model.Shop;
import com.zm.mds.mds_support.password.PasswordActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moska on 09.10.2017.
 */

public class ResultOrderActivity extends AppCompatActivity {
    private static final String TAG = ResultOrderActivity.class.getSimpleName();

    private Shop shop;
    private TextView tvFullName, tvYear, tvSum, tvPercent, tvPayment;

    private String full_name;
    String barcode, number, phone, email, dateOfBirth;
    private int year, percentage;
    private double sum, payment;

    private DataBaseHelper helper;
    private RelativeLayout rlComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_order);

        helper = new DataBaseHelper(this);
        if (helper.getCountShop() != 0) {
            shop = helper.getShop();
            if (shop != null) setActionBar();
        }

        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvSum = (TextView) findViewById(R.id.tvSum);
        tvPercent = (TextView) findViewById(R.id.tvPercent);
        tvPayment = (TextView) findViewById(R.id.tvPayment);
        rlComplete = (RelativeLayout) findViewById(R.id.rlComplete);

        Intent intent = getIntent();
        if (intent != null) {
            barcode = intent.getStringExtra("barcode");
            String sSum = intent.getStringExtra("sum");
            sum = Double.parseDouble(sSum);
            number = intent.getStringExtra("number");

            checkBarcode();
        }
    }

    public void clickNext(View view){
        startTimerThread();
    }

    private void setActionBar() {
        ImageView menu = (ImageView) findViewById(R.id.ivMenu);
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rlBack);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        title.setText(shop.getName());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void checkBarcode() {
//        rlScreenLoad.setVisibility(View.VISIBLE);
        ApiService api = Client.getApiService();
        Log.d(TAG, shop.getOrganizationId() + " " + barcode);
        Call<Card> call = api.apiBarcode(shop.getOrganizationId(), barcode);
        if(call == null) {
            finish();
            showError("Ошибка подключения");
//            rlScreenLoad.setVisibility(View.GONE);
            return;
        }
        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.isSuccessful()) {
                    Card result = response.body();
                    if(result == null) {
//                        rlScreenLoad.setVisibility(View.GONE);
//                        finish();
                        Intent intent = new Intent();
                        intent.putExtra("error", true);
                        setResult(RESULT_OK, intent);
                        finish();
                        return;
                    }
                    full_name = result.getUserFullName();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                            Date date = format.parse(result.getShareItem().getDateInNew());
                            year = getYear(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    if(result.getShareItem().getPercentage() == null || result.getShareItem().getPercentage().equals("")) percentage = 0;
                    else {
                        String sPercentage = result.getShareItem().getPercentage().replace("%", "");
                        percentage = Integer.valueOf(sPercentage);
                    }
                    payment = getPayment(percentage, sum);
                    dateOfBirth = result.getDateOfBirth();
                    phone = result.getPhone();
                    email = result.getEmail();


                    tvFullName.setText(full_name);
                    tvYear.setText(year + " gadiem");
                    tvSum.setText(sum + " eiro");
                    tvPercent.setText("Atlaide " + percentage + "%");
                    tvPayment.setText(payment + " eiro");

//                    if(!result.getResult()) {
//                        Intent intent = new Intent();
//                        intent.putExtra("error", true);
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    } else {
//                        full_name = result.getUserFullName();
//
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                        try {
//                            Date date = format.parse(result.getShareItem().getDateInNew());
//                            year = getYear(date);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        String sPercentage = result.getShareItem().getPercentage().replace("%", "");
//                        percentage = Integer.getInteger(sPercentage);
//                        payment = getPayment(percentage, sum);
//
//                        tvFullName.setText(full_name);
//                        tvYear.setText(year + " года");
//                        tvSum.setText(sum + " рублей");
//                        tvPercent.setText("Скидка " + percentage + "%");
//                        tvPayment.setText(payment + " рублей");
//                    }
//                    rlScreenLoad.setVisibility(View.GONE);
                } else {
                    showError("Ошибка сервера");
                    finish();
//                    rlScreenLoad.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                t.printStackTrace();
                showError("Ошибка подключения");
//                rlScreenLoad.setVisibility(View.GONE);
            }
        });
    }

    private void showError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void startTimerThread() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rlComplete.setVisibility(View.VISIBLE);
                        Report report = new Report();
                        report.setFullName(full_name);
                        report.setPercentage(percentage);
                        report.setSum(sum);
                        report.setPayment(payment);
                        report.setDate(getCurrentDate());
                        report.setTime(getCurrentTime());
                        report.setNumber(number);
                        report.setDateOfBirth(dateOfBirth);
                        report.setPhone(phone);
                        report.setEmail(email);

                        helper.createReport(report);
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rlComplete.setVisibility(View.GONE);
                        Intent intent = new Intent();
                        intent.putExtra("error", false);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    private int getYear(Date date) {
        Calendar currentDate = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(date);
        int year = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if(birthDate.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH)) return year - 1;
        else if(birthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)) {
            if(birthDate.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH)) return year - 1;
            else return year;
        }
        return year;
    }

//    private int getPercent(User user, String barcode) {
//        if(user.getDiscountCards() != null) {
//            for (DiscountCard discountCard : user.getDiscountCards()) {
//                if (discountCard.getUniqueCode().equals(barcode))
//                    return percentToInt(discountCard.getPercentage());
//            }
//        }
//        if(user.getCoupons() != null) {
//            for (Coupon coupon : user.getCoupons()) {
//                if (coupon.getUniqueCode().equals(barcode))
//                    return percentToInt(coupon.getPercentage());
//            }
//        }
//        if(user.getSpecialOffers() != null) {
//            for (SpecialOffer specialOffer : user.getSpecialOffers()) {
//                if (specialOffer.getUniqueCode().equals(barcode))
//                    return percentToInt(specialOffer.getPercentage());
//            }
//        }
//        if(user.getCertificates() != null) {
//            for (Certificate certificate : user.getCertificates()) {
//                if (certificate.getUniqueCode().equals(barcode))
//                    return percentToInt(certificate.getPercentage());
//            }
//        }
//        return 0;
//    }

    private double getPayment(int p, double s) {
        double d = s * p / 100;
        return s - d;
    }

    private int percentToInt(String code) {
        Log.d(TAG, "percent " + code);
        String str = code.replace("%", "");
        return Integer.parseInt(str);
    }

    private String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = dateFormat.format(date);
        return dateTime;
    }

    private String getCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String dateTime = dateFormat.format(date);
        return dateTime;
    }
}
