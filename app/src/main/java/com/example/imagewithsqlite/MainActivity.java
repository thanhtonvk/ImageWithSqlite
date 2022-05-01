package com.example.imagewithsqlite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBContext dbContext;
    Button btn_mocamera, btn_mothuvien;
    ImageView imageView;
    private static final int REQUEST_CAMERA = 54;
    private static final int REQUEST_GALERY = 994;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbContext = new DBContext(getApplicationContext());
        btn_mocamera = findViewById(R.id.btn_mocamera);
        btn_mothuvien = findViewById(R.id.btn_mothuvien);
        imageView = findViewById(R.id.image);
        //chụp ảnh
        btn_mocamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        //đọc ảnh đầu tiên từ csdl
//        List<HinhAnh> hinhAnhList = dbContext.getHinhAnhs();
//        Bitmap bitmap = BitmapUtils.getImage(hinhAnhList.get(0).getAnh());
//        imageView.setImageBitmap(bitmap);

        //mở ảnh từ thư viện
        btn_mothuvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALERY);

            }
        });


        //load image tu link
        Glide.with(MainActivity.this).load("https://scontent.fhan15-1.fna.fbcdn.net/v/t39.30808-6/279700087_4057923214465783_7009461514321383623_n.png?_nc_cat=1&ccb=1-5&_nc_sid=730e14&_nc_ohc=0VbqFrtEV_UAX-A4NtC&_nc_ht=scontent.fhan15-1.fna&oh=00_AT_n1K1YL1a70x8QV_IEP4nBaT5psGmKB-y8GmUg_7kNxA&oe=627328D8").into(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            HinhAnh hinhAnh = new HinhAnh("Hihi", BitmapUtils.getBytes(bitmap));
            dbContext.add(hinhAnh);
        }
        if (requestCode == REQUEST_GALERY) {
            Uri uri = data.getData();
            try {
                InputStream stream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);
                //giảm dung lượng
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.2), (int) (bitmap.getHeight() * 0.2), true);
                byte[] anh = BitmapUtils.getBytes(resize);
                HinhAnh hinhAnh = new HinhAnh("Haha", anh);
                dbContext.add(hinhAnh);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}