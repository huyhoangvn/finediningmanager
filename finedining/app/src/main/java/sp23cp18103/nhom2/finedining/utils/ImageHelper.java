package sp23cp18103.nhom2.finedining.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sp23cp18103.nhom2.finedining.R;

public class ImageHelper {
    /*
    * Hiển thị hình ảnh đại diện
    * */
    public static void loadAvatar(Context context, ImageView view, String url){
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_avatar)
                .override(250, 250)
                .fitCenter()
                .transform(new RoundedCorners(5))
                .into(view);
    }

    /*
    * Hiển thị hình ảnh băng rôn
    * */
    public static void loadBanner(Context context, ImageView view, String url){
        Glide.with(context)
                .load(url)
                .override(1000, 1000)
                .placeholder(R.drawable.default_banner)
                .centerCrop()
                .into(view);
    }
}
