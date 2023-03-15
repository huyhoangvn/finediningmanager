package sp23cp18103.nhom2.finedining.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
* Hỗ trợ chuyển đổi định dạng ngày từ SQLite sang Việt Nam và ngược lại
* Và hỗ trợ tạo dialog để chọn ngày giờ
* */
public class DateHelper {

    /*
     * Trả về ngày và giờ hiện tại theo chuẩn cơ sở dữ liệu SQL
     * VD: 2000-12-30 09:01
     * */
    public static String getDateTimeSQLNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    /*
     * Trả về ngày và giờ hiện tại theo chuẩn Việt Nam
     * VD: 30-12-2000 09:01
     * */
    public static String getDateTimeVietnamNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    /*
     * Trả về ngày hiện tại theo chuẩn cơ sở dữ liệu SQL
     * VD: 2000-12-30
     * */
    public static String getDateSQLNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    /*
     * Trả về ngày hiện tại theo chuẩn Việt Nam
     * VD: 30-12-2000
     * */
    public static String getDateVietnamNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    /*
     * Chuyển đổi ngày giờ từ định dạng việt nam sang định dạng sql
     * VD: 30-12-2000 09:01 > 2000-12-30 09:01
     * */
    public static String getDateTimeSql(String dateTimeVietnam){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(dateTimeVietnam);
    }

    /*
     * Chuyển đổi ngày giờ từ định dạng sql sang định dạng việt nam
     * VD: 2000-12-30 09:01 > 30-12-2000 09:01
     * */
    public static String getDateTimeVietnam(String dateTimeSql){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return sdf.format(dateTimeSql);
    }

    /*
     * Chuyển đổi ngày từ định dạng việt nam sang định dạng sql
     * VD: 30-12-2000 > 2000-12-30
     * */
    public static String getTimeSql(String dateVietnam){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(dateVietnam);
    }

    /*
     * Chuyển đổi ngày từ định dạng sql sang định dạng việt nam
     * VD: 2000-12-30 > 30-12-2000
     * */
    public static String getTimeVietnam(String dateSql){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(dateSql);
    }

    /*
     * Hiển thị hộp thoại để chọn ngày-tháng-năm
     * Đặt ngày theo định dạng dd-mm-YYYY trên textview đã thêm vào
     * */
    public static void showDatePickerVietnam(Context context, TextView textView) {
        final Calendar c = Calendar.getInstance();
        new DatePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (datePicker, i, i1, i2) -> {
                    String date = i2 + "/" + (i1+1) + "/" + i;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    textView.setText((String)sdf.format(date));
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)){
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }.show();
    }
}
