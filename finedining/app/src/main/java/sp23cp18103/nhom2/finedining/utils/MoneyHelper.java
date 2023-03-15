package sp23cp18103.nhom2.finedining.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MoneyHelper {

    /*
    * Trả về định dạng tiền có dâu phẩy phân cách
    * VD: 1000000 (int/string) > 1,000,000 (string)
    * */
    public static String getMoneyWithDecimal(int money){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###", symbols);
        return df.format(money);
    }

    public static String getMoneyWithDecimal(String money){
        return getMoneyWithDecimal(money);
    }

    /*
     * Trả về định dạng tiền không có dấu phẩy phân cách
     * VD: 1,000,000 (String) > 1000000 (int)
     * */
    public static int getMoneyWithoutDecimal(String money){
        return Integer.parseInt(money.replace(",", ""));
    }
}
