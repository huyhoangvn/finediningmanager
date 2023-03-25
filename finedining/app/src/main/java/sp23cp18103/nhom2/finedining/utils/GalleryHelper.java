package sp23cp18103.nhom2.finedining.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import sp23cp18103.nhom2.finedining.HomeActivity;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;

/*
* Chỉ dùng được trong HomeActivity và các lớp con của nó
* */
public class GalleryHelper{
    private Context context;
    private String currentImageUrl;

    public GalleryHelper(Context context) {
        this.context = context;
    }

    /*
    * Kiểm tra và xin quyền sử dụng gallery
    * */
    public boolean checkRequestForPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
                return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED;
            }
        }
        return true;
    }

    /*
    * Lấy ảnh từ gallery
    * Hiển thị hình ảnh lên
    * */
    public void getImageFromGallery(ImageView imageView) {
        if(checkRequestForPermission()){
            Toast.makeText(context, "Đã cho phép sử dụng ảnh...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            ((HomeActivity)context).activityLauncher.launch(intent, result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if(data != null){
                        imageView.setImageURI(data.getData());
                        uploadImageToFirebase(data.getData());
                    }
                }
            });
        } else {
            Toast.makeText(context, "Không cho phép sử dụng ảnh...", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * Tải ảnh lên firebase theo uri
    * rồi lấy url về lưu vào currentImageUrl
    * */
    public void uploadImageToFirebase(Uri uri) {
        if(uri == null)
            return;

        ProgressDialog prgLoad = new ProgressDialog(context);
        prgLoad.setMessage("Upload Image...");
        prgLoad.setCanceledOnTouchOutside(false);
        prgLoad.show();
        //Tạo filename lấy ngày giờ tạo
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = format.format(now);
        //Lưu vào images/
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference = storageReference
                .child("images/"+ fileName);
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        currentImageUrl = uri.toString();
                                        prgLoad.dismiss();
                                        Toast.makeText(context, "Chọn ảnh thành công...", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        prgLoad.dismiss();
                                        Toast.makeText(context, "Lấy đường dẫn không thành công...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        prgLoad.dismiss();
                        Toast.makeText(context, "Tải ảnh lên không thành công...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
    * Lấy url ảnh đã thêm vào
    * */
    public String getCurrentImageUrl() {
        return currentImageUrl;
    }
}
