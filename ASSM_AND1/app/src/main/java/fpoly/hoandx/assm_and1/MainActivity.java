package fpoly.hoandx.assm_and1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.hoandx.assm_and1.screens.NhanVien_ACT;
import fpoly.hoandx.assm_and1.screens.PhongBan_ACT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton img_phongban=findViewById(R.id.img_phongban);
        ImageButton img_nhanvien=findViewById(R.id.img_nhanvien);
        ImageButton img_thoat=findViewById(R.id.img_thoat);

        img_phongban.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, PhongBan_ACT.class);
            startActivity(intent);
        });

        img_nhanvien.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, NhanVien_ACT.class);
            startActivity(intent);
        });
        img_thoat.setOnClickListener(v -> {
         finish();
        });

    }
}