package sp23cp18103.nhom2.finedining;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/*
 * Hiển thị màn hình chờ
 * */
@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    ImageView img_logo,img_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img_logo = findViewById(R.id.img_logo);
        img_load = findViewById(R.id.img_load);


        Glide.with(this).load(R.drawable.img_loading).into(img_load);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
            }
        },3000);
    }
}