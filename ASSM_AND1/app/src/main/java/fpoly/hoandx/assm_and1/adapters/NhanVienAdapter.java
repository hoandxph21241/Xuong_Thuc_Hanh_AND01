package fpoly.hoandx.assm_and1.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.hoandx.assm_and1.R;
import fpoly.hoandx.assm_and1.database.FileHelper;
import fpoly.hoandx.assm_and1.models.NhanVien;
import fpoly.hoandx.assm_and1.models.PhongBan;
import fpoly.hoandx.assm_and1.screens.NhanVien_ACT;

public class NhanVienAdapter extends BaseAdapter {

    ArrayList<NhanVien> arrayList;
    Context context;

    public NhanVienAdapter(ArrayList<NhanVien> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (arrayList != null)
            return arrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_nhanvien, parent, false);
        }


        TextView manv = convertView.findViewById(R.id.item_manv);
        TextView ten = convertView.findViewById(R.id.item_hoten);
        TextView phongban = convertView.findViewById(R.id.item_phongban);
        ImageView img = convertView.findViewById(R.id.item_avatar);
        ImageView delete = convertView.findViewById(R.id.item_delete);
        ImageView edit = convertView.findViewById(R.id.item_edit);

        manv.setText(String.valueOf(arrayList.get(position).getManv()));
        ten.setText(String.valueOf(arrayList.get(position).getName()));
        img.setImageResource(arrayList.get(position).getImg());
        phongban.setText(String.valueOf(arrayList.get(position).getPhongban()));

        Log.d("DATA", manv + " " + ten + " " + phongban);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(position);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(position);
            }
        });

        return convertView;
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle("DELETE");
        b.setMessage("Xác Nhận Xóa");
        b.setPositiveButton("Yes", (dialog, which) -> {
            arrayList.remove(position);
            FileHelper fileHelper = new FileHelper(context);
            fileHelper.WriteToFile_NHANVIEN(arrayList, "NHANVIEN.txt");
            notifyDataSetChanged();
            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
        });
        b.setNegativeButton("No", null);
        b.show();
    }

    private void showEditDialog(final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_nhanvien, null);

        final TextInputEditText dialogManv = dialogView.findViewById(R.id.dialog_manv);
        final TextInputEditText dialogTennv = dialogView.findViewById(R.id.dialog_tennv);
        //final TextInputEditText dialogPhongban = dialogView.findViewById(R.id.dialog_phongban);
        final Spinner dialogPhongban = dialogView.findViewById(R.id.spinner_phongban);

        ArrayList<PhongBan> phongBanList = new ArrayList<>();
        phongBanList.add(new PhongBan("IT", R.drawable.baseline_add_home_work_24));
        phongBanList.add(new PhongBan("Hành Chính", R.drawable.baseline_add_home_work_24));
        phongBanList.add(new PhongBan("Dịch Vụ", R.drawable.baseline_add_home_work_24));



        ArrayAdapter<PhongBan> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phongBanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogPhongban.setAdapter(adapter);


        NhanVien nhanVien = arrayList.get(position);
        dialogManv.setText(nhanVien.getManv());
        dialogTennv.setText(nhanVien.getName());
        //dialogPhongban.setText(nhanVien.getPhongban());

        int index = -1;
        // Tìm position của Phong Ban trong danh sách
        for (int i = 0; i < phongBanList.size(); i++) {
            if (phongBanList.get(i).getTenPhongBan().equals(nhanVien.getPhongban())) {
                index = i;
                break;
            }
        }
        // Nếu tìm thấy return position
        if (index != -1) {
            dialogPhongban.setSelection(index);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("EDIT Nhân Viên")
                .setView(dialogView)
                .setPositiveButton("SAVE", (dialog, which) -> {
                    nhanVien.setManv(dialogManv.getText().toString());
                    nhanVien.setName(dialogTennv.getText().toString());
                   // nhanVien.setPhongban(dialogPhongban.getText().toString());
                    PhongBan selectedPhongBan = (PhongBan) dialogPhongban.getSelectedItem();
                    nhanVien.setPhongban(selectedPhongBan.getTenPhongBan());

                    FileHelper fileHelper = new FileHelper(context);
                    fileHelper.WriteToFile_NHANVIEN(arrayList, "NHANVIEN.txt");
                    notifyDataSetChanged();
                    Toast.makeText(context, "Chỉnh Sửa Thành Công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }
}