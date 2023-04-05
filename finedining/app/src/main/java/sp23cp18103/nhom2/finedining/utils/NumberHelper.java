package sp23cp18103.nhom2.finedining.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberHelper {

    /*
    * Trả về định dạng số có dâu phẩy phân cách
    * VD: 1000000 (int/string) > 1,000,000 (string)
    * */
    public static String getNumberWithDecimal(long money){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###", symbols);
        return df.format(money);
    }

    /*
     * Trả về định dạng số không có dấu phẩy phân cách
     * VD: 1,000,000 > 1000000
     * */
    public static int getMoneyWithoutDecimal(String moneyWithDecimal){
        return Integer.parseInt(moneyWithDecimal.replace(",", ""));
    }

    /*
     * Trả về định dạng số điện thoại có cách
     * VD: 09330300888 -> 0933 0300 888
     * */
    public static String getReadablePhoneNumber(String phoneNumber){
        return phoneNumber.replaceFirst("(\\d{4})(\\d{3})(\\d{3})", "$1 $2 $3");   //1230-567-900
    }

    /*
     * Trả về định dạng số điện thoại không có cách
     * VD: 0933 0300 888 -> 09330300888
     * */
    public static String getPhoneNumber(String readablePhoneNumber){
        return readablePhoneNumber.replace(" ", "");
    }
}
