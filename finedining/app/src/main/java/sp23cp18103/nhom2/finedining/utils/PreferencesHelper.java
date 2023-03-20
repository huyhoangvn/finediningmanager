package sp23cp18103.nhom2.finedining.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

/*
* Lưu sharedPreferences để ghi nhớ tài khoản, mật khẩu
* Và truy cập sharedPreferences để lấy id, vai trò người dùng hiện tại nhanh hơn
* */
public class PreferencesHelper {
    private static final String MY_SHARED_PREFERENCES = "mySharedPreference";
    private static final String ID_TAG = "maNguoiDung";
    private static final String ACCOUNT_TAG = "tenDangNhap";
    private static final String PASSWORD_TAG = "matKhau";
    private static final String REMEMBER_TAG = "ghiNho";

    public static SharedPreferences getSharedPref(Context context){
        return context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    /*
     * Lưu mã người dùng khi đã đăng nhập thành công
     * */
    public static void saveIdSharedPref(Context context, Integer maNguoiDung){
        SharedPreferences sharedPreferences = getSharedPref(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID_TAG, maNguoiDung);
        editor.apply();
    }

    /*
    * Lưu khi đã đăng nhập thành công
    * Tài khoản, mật khẩu sẽ được ghi nhớ nếu ghiNho == true
    * */
    public static void saveSharedPref(Context context, String taiKhoan, String maKhau, boolean ghiNho) {
        SharedPreferences sharedPreferences = getSharedPref(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(ghiNho){
            editor.putString(ACCOUNT_TAG, taiKhoan);
            editor.putString(PASSWORD_TAG, maKhau);
            editor.putBoolean(REMEMBER_TAG, true);
        }
        else{
            editor.putString(ACCOUNT_TAG, "");
            editor.putString(PASSWORD_TAG, "");
            editor.putBoolean(REMEMBER_TAG, false);
        }
        editor.apply();
    }

    /*
     * Lấy ghi nhớ tài khoản
     * */
    public  static String getTaiKhoan(Context context){
        SharedPreferences sharedPreferences = getSharedPref(context);
        return sharedPreferences.getString(ACCOUNT_TAG, "");
    }

    /*
     * Lấy ghi nhớ mật khẩu
     * */
    public  static String getMatKhau(Context context){
        SharedPreferences sharedPreferences = getSharedPref(context);
        return sharedPreferences.getString(PASSWORD_TAG, "");
    }

    /*
     * Lấy ghi nhớ đăng nhập
     * */
    public  static boolean getGhiNho(Context context){
        SharedPreferences sharedPreferences = getSharedPref(context);
        return sharedPreferences.getBoolean(REMEMBER_TAG, false);
    }

    /*
    * Lấy mã người dùng hiện tại
    * */
    public  static int getId(Context context){
        SharedPreferences sharedPreferences = getSharedPref(context);
        return sharedPreferences.getInt(ID_TAG, -1);
    }

    /*
    * Khi người dùng thoát khỏi chương trình thì sẽ giải phóng mã người dùng
    * */
    public  static void clearId(Context context){
        SharedPreferences sharedPreferences = getSharedPref(context);
        sharedPreferences.edit().putInt("maThuThu", -1).apply();
    }
}
