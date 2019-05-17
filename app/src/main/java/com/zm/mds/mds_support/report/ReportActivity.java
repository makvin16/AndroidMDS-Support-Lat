package com.zm.mds.mds_support.report;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zm.mds.mds_support.General;
import com.zm.mds.mds_support.R;
import com.zm.mds.mds_support.db.DataBaseHelper;
import com.zm.mds.mds_support.model.Report;
import com.zm.mds.mds_support.support.SupportActivity;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by moska on 10.10.2017.
 */

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = ReportActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION = 123;

    private DataBaseHelper helper;
    private String currentDate;
    private ArrayList<Report> arrReport;
    private ListView listView;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView tvTitle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        init();
        setActionBar();
    }

    private void setActionBar() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rlBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.report) + currentDate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        ReportActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void init() {
        currentDate = getCurrentDate();
        helper = new DataBaseHelper(this);
        listView = (ListView) findViewById(R.id.listView);

        updateList();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String sYear, sMonth, sDay;
                if(month < 10) sMonth = "0" + month;
                else sMonth = month + "";
                if(dayOfMonth < 10) sDay = "0" + dayOfMonth;
                else sDay = dayOfMonth + "";
                sYear = year + "";
                String date = sDay + "." + sMonth + "." + sYear;
                currentDate = date;
                tvTitle.setText("Отчет за " + currentDate);
                updateList();
            }
        };
    }

    public void clickClose(View view) {
        Toast.makeText(this, getString(R.string.toast5), Toast.LENGTH_SHORT).show();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        saveExcelFile(this, General.FILE_EXCEL);
    }

    private void updateList() {
        arrReport = helper.getReport(currentDate);
        ReportAdapter adapter = new ReportAdapter(ReportActivity.this, arrReport);
        listView.setAdapter(adapter);
        TextView tvSum = (TextView) findViewById(R.id.tvSum);
        TextView tvPercentage = (TextView) findViewById(R.id.tvPercentage);
        TextView tvPayment = (TextView) findViewById(R.id.tvPayment);

        double sum = 0.0;
        int percentage = 0;
        double payment = 0.0;
        for(Report report : arrReport) {
            sum += report.getSum();
            percentage += report.getPercentage();
            payment += report.getPayment();
        }

        if(arrReport.size() != 0) percentage /= arrReport.size();

        tvSum.setText(sum + "€");
        tvPercentage.setText(percentage + "%");
        tvPayment.setText(payment + "€");
    }

    private String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = dateFormat.format(date);
        return dateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void isCheck() {
        int hasSMSPermission = checkSelfPermission(Manifest.permission.READ_SMS);
        int hasExternalStoragePermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasCallPhonePermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
        if (hasSMSPermission != PackageManager.PERMISSION_GRANTED ||
                hasExternalStoragePermission != PackageManager.PERMISSION_GRANTED ||
                hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        }
    }

    private boolean saveExcelFile(Context context, String fileName) {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.d(TAG, "Storage not available or read only");
            return false;
        }

        boolean success = false;

        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet sheet = null;
        sheet = wb.createSheet("myOrder");

        Row row = sheet.createRow(0);

        c = row.createCell(0);
        c.setCellValue(General.ITEM_NAME);
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue(General.ITEM_PERCENTAGE);
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue(General.ITEM_SUM);
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue(General.ITEM_PAYMENT);
        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue(General.ITEM_DATE);
        c.setCellStyle(cs);

        c = row.createCell(5);
        c.setCellValue(General.ITEM_TIME);
        c.setCellStyle(cs);

        c = row.createCell(6);
        c.setCellValue(General.ITEM_NUMBER_CHECK);
        c.setCellStyle(cs);

        c = row.createCell(7);
        c.setCellValue(General.ITEM_DATE_OF_BIRTH);
        c.setCellStyle(cs);

        c = row.createCell(8);
        c.setCellValue(General.ITEM_PHONE);
        c.setCellStyle(cs);

        c = row.createCell(9);
        c.setCellValue(General.ITEM_EMAIL);
        c.setCellStyle(cs);

        sheet.setColumnWidth(0, (15 * 500));
        sheet.setColumnWidth(1, (15 * 500));
        sheet.setColumnWidth(2, (15 * 500));
        sheet.setColumnWidth(3, (15 * 500));
        sheet.setColumnWidth(4, (15 * 500));
        sheet.setColumnWidth(5, (15 * 500));
        sheet.setColumnWidth(6, (15 * 500));
        sheet.setColumnWidth(7, (15 * 500));
        sheet.setColumnWidth(8, (15 * 500));
        sheet.setColumnWidth(9, (15 * 500));

        //write from db
        ArrayList<Report> arrReport = helper.getReport();
        for(int i = 0; i < arrReport.size(); i++) {
            Report report = arrReport.get(i);
            Row rowItem = sheet.createRow(i + 1);
            Cell cell1 = rowItem.createCell(0);
            Cell cell2 = rowItem.createCell(1);
            Cell cell3 = rowItem.createCell(2);
            Cell cell4 = rowItem.createCell(3);
            Cell cell5 = rowItem.createCell(4);
            Cell cell6 = rowItem.createCell(5);
            Cell cell7 = rowItem.createCell(6);
            Cell cell8 = rowItem.createCell(7);
            Cell cell9 = rowItem.createCell(8);
            Cell cell10 = rowItem.createCell(9);
            cell1.setCellValue(report.getFullName());
            cell2.setCellValue(report.getPercentage());
            cell3.setCellValue(report.getSum());
            cell4.setCellValue(report.getPayment());
            cell5.setCellValue(report.getDate());
            cell6.setCellValue(report.getTime());

            cell7.setCellValue(report.getNumber());
            cell8.setCellValue(report.getDateOfBirth());
            cell9.setCellValue(report.getPhone());
            cell10.setCellValue(report.getEmail());
        }

        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w(TAG, "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w(TAG, "Error writing " + file, e);
        } catch (Exception e) {
            Log.w(TAG, "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }

        //send by email
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
//        intent.setType("vnd.android.cursor.dir/email");
        ArrayList<String> arrEmail = helper.getEmail();
        String to[] = new String[arrEmail.size()];
        for(int i = 0; i < to.length; i++) to[i] = arrEmail.get(i);
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_STREAM, path);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Отчет");
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.sendMail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReportActivity.this, getString(R.string.toast3), Toast.LENGTH_SHORT).show();
        }

        return success;
    }

    private boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
