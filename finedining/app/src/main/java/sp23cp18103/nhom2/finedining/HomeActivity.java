package sp23cp18103.nhom2.finedining;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.fragment.BanCollectionFragment;
import sp23cp18103.nhom2.finedining.fragment.DoiMatKhauFragment;
import sp23cp18103.nhom2.finedining.fragment.HoaDonCollectionFragment;
import sp23cp18103.nhom2.finedining.fragment.HoaDonFragment;
import sp23cp18103.nhom2.finedining.fragment.HomeFragment;
import sp23cp18103.nhom2.finedining.fragment.MonCollectionFragment;
import sp23cp18103.nhom2.finedining.fragment.NhanVienCollectionFragment;
import sp23cp18103.nhom2.finedining.fragment.ThongKeDoanhThuFragment;
import sp23cp18103.nhom2.finedining.fragment.ThongKeKhachFragment;
import sp23cp18103.nhom2.finedining.fragment.ThongKeMonFragment;
import sp23cp18103.nhom2.finedining.utils.BetterActivityResult;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Màn hình chính chứa Fragment Home và sử dụng Navigation Drawer
 * */
public class HomeActivity extends AppCompatActivity {
    //Hỗ trợ GalleryHelper
    public final BetterActivityResult<Intent, ActivityResult> activityLauncher
            = BetterActivityResult.registerActivityForResult(this);

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anhXa();
        toolBar();
        contenHeader();
    }

    private void toolBar() {
        // tollbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // mởmenu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.naviOpen,R.string.naviClose);
        drawerLayout.addDrawerListener(toggle);
        upDateImg();
        toggle.syncState();

        //set icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Trang chủ");



        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.linear,new HomeFragment()).commit();

        navigationView.getMenu().findItem(R.id.mn_home).setChecked(true);
        /// mở navigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            MenuItem prevMenuItem = null;
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /// chọn item navigationView
                        switch (item.getItemId()){
                            case R.id.mn_home:
                                fragment = new HomeFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                toolbar.setTitle(item.getTitle());
                                break;

                            case R.id.mn_quanly_ban:
                                fragment =  new BanCollectionFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_quanly_monan:
                                fragment = new MonCollectionFragment();

                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_quanly_hoadon:
                                fragment = new HoaDonCollectionFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_quanly_nhanvien:
                                fragment = new NhanVienCollectionFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_bestseller:
                                fragment =  new ThongKeMonFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_doanhthu:
                                fragment = new ThongKeDoanhThuFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_tongkhach:
                                fragment = new ThongKeKhachFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_doimatkhau:
                                fragment = new DoiMatKhauFragment();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                        .replace(R.id.linear,fragment)
                                        .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                                toolbar.setTitle(item.getTitle());
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                break;

                            case R.id.mn_dangxuat:
                                navigationView.getMenu().findItem(R.id.mn_home).setChecked(false);
                                new AlertDialog.Builder(HomeActivity.this).setTitle("Bạn có muốn đăng xuất?")
                                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent =  new Intent(HomeActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                                                PreferencesHelper.clearId(getApplicationContext());
                                            }
                                        })
                                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).create().show();
                                break;
                        }

                        // kiểm tra set mầu cho item khi click
                        if (prevMenuItem != null) {
                            prevMenuItem.setChecked(false); // uncheck the previously selected item
                        }

                        item.setChecked(true); // check the newly selected item
                        prevMenuItem = item; // store the newly selected item

                    }
                }, 150);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
    // set logic ấn nút back thì tắt naviwiew có vấn đề mà cũng ko cần thiết
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    void contenHeader(){
        View view = navigationView.getHeaderView(0);
        TextView tvTenNV = view.findViewById(R.id.tv_tenNhanVien);
        TextView tvChucVu = view.findViewById(R.id.tv_chucvu);
        CircleImageView imgAvt = view.findViewById(R.id.img_avt);




        nhanVienDAO = new NhanVienDAO(this);
        int maNV = PreferencesHelper.getId(this);
        String hinh = nhanVienDAO.hinh(maNV);
        String name = nhanVienDAO.getTenNV(maNV);
        int chuVu = nhanVienDAO.getPhanQuyen(maNV);
        ImageHelper.loadAvatar(this,imgAvt,hinh);
        //set name header
        tvTenNV.setText(name);

        //set phân quyền
        if (chuVu == 1){
            tvChucVu.setText("Quản lý");
        }else {
            tvChucVu.setText("Nhân viên");
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mn_doanhthu).setVisible(false);
            menu.findItem(R.id.mn_quanly_nhanvien).setVisible(false);
            menu.findItem(R.id.mn_tongkhach).setVisible(false);
        }
    }

    private void   anhXa(){
        toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navi_menu);
    }

    void upDateImg(){
        View view = navigationView.getHeaderView(0);
        int maNV = PreferencesHelper.getId(this);
        nhanVienDAO = new NhanVienDAO(this);
        String hinh = nhanVienDAO.hinh(maNV);
        CircleImageView imgAvt = view.findViewById(R.id.img_avt);
        ImageHelper.loadAvatar(this,imgAvt,hinh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferencesHelper.clearId(getApplicationContext());
    }
}