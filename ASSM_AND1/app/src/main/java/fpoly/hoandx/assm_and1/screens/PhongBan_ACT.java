package fpoly.hoandx.assm_and1.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class PhongBan_ACT extends AppCompatActivity {

    Context context;
    ListView listview;
    PhongBanAdapter phongBanAdapter;
    Button add_phongban;
    ArrayList<PhongBan> arrayList;
    FileHelper fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phong_ban);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        listview = findViewById(R.id.listView_phongban);
      //  add_phongban = findViewById(R.id.btn_add_phongban);

        ArrayList<PhongBan> arrayList = new ArrayList<>();
        arrayList.add(new PhongBan("IT", R.drawable.baseline_add_home_work_24));
        arrayList.add(new PhongBan("Hành Chính", R.drawable.baseline_add_home_work_24));
        arrayList.add(new PhongBan("Dịch Vụ", R.drawable.baseline_add_home_work_24));


//        fileHelper = new FileHelper(PhongBan_ACT.this);
//        arrayList = fileHelper.ReadFromFile_PHONGBAN("PHONGBAN.txt");
        phongBanAdapter = new PhongBanAdapter(PhongBan_ACT.this, arrayList);
        listview.setAdapter(phongBanAdapter);

//
//        add_phongban.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAddPhongBanDialog();
//            }
//        });
    }

    private void showAddPhongBanDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_phongban, null);

        final TextInputEditText dialogTenPhongban = dialogView.findViewById(R.id.dialog_ten_phongban);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ADD Phòng Ban")
                .setView(dialogView)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenpb = dialogTenPhongban.getText().toString();
                        int img = R.drawable.baseline_add_home_work_24;
                        PhongBan phongBan = new PhongBan(tenpb, img);

                        if (arrayList != null) {
                            arrayList.add(phongBan);
                        } else {
                            arrayList = new ArrayList<>();
                            arrayList.add(phongBan);
                        }

                        fileHelper.WriteToFile_PHONGBAN(arrayList, "PHONGBAN.txt");
                        phongBanAdapter = new PhongBanAdapter(PhongBan_ACT.this,arrayList);
                        listview.setAdapter(phongBanAdapter);
                        Toast.makeText(PhongBan_ACT.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Log.d("DATA", tenpb);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }
}