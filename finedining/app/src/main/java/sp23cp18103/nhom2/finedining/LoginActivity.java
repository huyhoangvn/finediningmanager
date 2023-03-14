package sp23cp18103.nhom2.finedining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
* Đăng nhập
* Và chuyển qua màn hình chính nếu đã đăng nhập rồi
* */
public class LoginActivity extends AppCompatActivity {
    Button btn_dangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        login();
    }
    private void login() {
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }

    private void anhXa() {
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
    }
}