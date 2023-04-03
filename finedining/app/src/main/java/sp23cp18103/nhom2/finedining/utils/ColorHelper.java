package sp23cp18103.nhom2.finedining.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import sp23cp18103.nhom2.finedining.R;

/*
 * Chọn màu cho các trạng thái
 * */
public class ColorHelper {

    public static int getNegativeColor(Context context){
        return getResourceColor(context, R.color.Negative);
    }

    public static int getPositiveColor(Context context){
        return getResourceColor(context, R.color.Positive);
    }

    public static int getNeutralColor(Context context){
        return getResourceColor(context, R.color.Neutral);
    }

    public static int getWaitingColor(Context context){
        return getResourceColor(context, R.color.Waiting);
    }

    public static int getAttrColor(Context context, int id){
        final TypedValue value = new TypedValue ();
        context.getTheme ().resolveAttribute (id, value, true);
        return value.data;
    }

    public static int getResourceColor(Context context, int id){
        return ContextCompat.getColor(context, id);
    }
}
