package sp23cp18103.nhom2.finedining.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberHelper {

    /*
    * Trả về định dạng số có dâu phẩy phân cách
    * VD: 1000000 (int/string) > 1,000,000 (string)
    * */
    public static String getNumberWithDecimal(int money){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###", symbols);
        return df.format(money);
    }

    /*
     * Trả về định dạng số không có dấu phẩy phân cách
     * VD: 1,000,000 (String) > 1000000 (int)
     * */
    public static int getMoneyWithoutDecimal(String money){
        return Integer.parseInt(money.replace(",", ""));
    }
}
