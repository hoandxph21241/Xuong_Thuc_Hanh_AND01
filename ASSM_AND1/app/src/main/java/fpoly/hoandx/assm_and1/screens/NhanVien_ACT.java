package fpoly.hoandx.assm_and1.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.hoandx.assm_and1.R;
import fpoly.hoandx.assm_and1.adapters.NhanVienAdapter;
import fpoly.hoandx.assm_and1.adapters.PhongBanAdapter;
import fpoly.hoandx.assm_and1.database.FileHelper;
import fpoly.hoandx.assm_and1.models.NhanVien;
import fpoly.hoandx.assm_and1.models.PhongBan;

public class NhanVien_ACT extends AppCompatActivity {

    Context context;
    ListView listview;
    NhanVienAdapter nhanVienAdapter;
    Button add_nhanvien;
    ArrayList<NhanVien> arrayList;

    FileHelper fileHelper = new FileHelper(NhanVien_ACT.this);

    SearchView srview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listview = findViewById(R.id.listView_nhanvien);
        add_nhanvien = findViewById(R.id.btn_add_nhanvien);

        context = this;
        fileHelper = new FileHelper(NhanVien_ACT.this);
        arrayList = fileHelper.ReadFromFile_NHANVIEN("NHANVIEN.txt");
        nhanVienAdapter = new NhanVienAdapter(arrayList, NhanVien_ACT.this);
        listview.setAdapter(nhanVienAdapter);


        srview = findViewById(R.id.searchView_nhanvien);

        add_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNhanVienDialog();
            }
        });



        srview = findViewById(R.id.searchView_nhanvien);

        srview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<NhanVien> newlist = new ArrayList<>();

                for (NhanVien s : arrayList) {
                    if (s.getName().toLowerCase().contains(newText.toLowerCase())) {
                        newlist.add(s);
                    }
                }

                nhanVienAdapter = new NhanVienAdapter(newlist, NhanVien_ACT.this);
                listview.setAdapter(nhanVienAdapter);
                return false;
            }
        });
    }

    private void showAddNhanVienDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_nhanvien, null);

        final TextInputEditText dialogManv = dialogView.findViewById(R.id.dialog_manv);
        final TextInputEditText dialogTennv = dialogView.findViewById(R.id.dialog_tennv);
        //   final TextInputEditText dialogPhongban = dialogView.findViewById(R.id.dialog_phongban);
        final Spinner dialogPhongban = dialogView.findViewById(R.id.spinner_phongban);

        ArrayList<PhongBan> phongBanList = new ArrayList<>();
        phongBanList.add(new PhongBan("IT", R.drawable.baseline_add_home_work_24));
        phongBanList.add(new PhongBan("Hành Chính", R.drawable.baseline_add_home_work_24));
        phongBanList.add(new PhongBan("Dịch Vụ", R.drawable.baseline_add_home_work_24));

        ArrayAdapter<PhongBan> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phongBanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogPhongban.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thêm Nhân Viên")
                .setView(dialogView)
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String manv = dialogManv.getText().toString();
                        String tensv = dialogTennv.getText().toString();
                        //String phongban = dialogPhongban.getText().toString();
                        PhongBan selectedPhongBan = (PhongBan) dialogPhongban.getSelectedItem() ;
                        String phongban = selectedPhongBan.getTenPhongBan();

                        int img = R.drawable.user_regular;
                        NhanVien nhanVien = new NhanVien(manv, img, tensv, phongban);

                        if (arrayList != null) {
                            arrayList.add(nhanVien);
                        } else {
                            arrayList = new ArrayList<>();
                            arrayList.add(nhanVien);
                        }

                        fileHelper.WriteToFile_NHANVIEN(arrayList, "NHANVIEN.txt");
                        nhanVienAdapter = new NhanVienAdapter(arrayList, NhanVien_ACT.this);
                        listview.setAdapter(nhanVienAdapter);
                        Toast.makeText(NhanVien_ACT.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Log.d("DATA", manv + " " + tensv + " " + phongban);
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }
}