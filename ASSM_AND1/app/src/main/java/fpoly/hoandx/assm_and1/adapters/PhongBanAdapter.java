package fpoly.hoandx.assm_and1.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.hoandx.assm_and1.R;
import fpoly.hoandx.assm_and1.database.FileHelper;
import fpoly.hoandx.assm_and1.models.NhanVien;
import fpoly.hoandx.assm_and1.models.PhongBan;

public class PhongBanAdapter extends BaseAdapter {
    Context context;
    ArrayList<PhongBan> arrayList;

    public PhongBanAdapter(Context context, ArrayList<PhongBan> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
            convertView = inflater.inflate(R.layout.item_phongban, parent, false);
        }


        TextView phongban = convertView.findViewById(R.id.item_phong);
        ImageView img = convertView.findViewById(R.id.item_avatar_phongban);
        ImageView delete = convertView.findViewById(R.id.item_delete_pb);
        ImageView edit = convertView.findViewById(R.id.item_edit_pb);

        phongban.setText(String.valueOf(arrayList.get(position).getTenPhongBan()));
        img.setImageResource(arrayList.get(position).getImgPhongBan());


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
            fileHelper.WriteToFile_PHONGBAN(arrayList, "PHONGBAN.txt");
            notifyDataSetChanged();
            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
        });
        b.setNegativeButton("No", null);
        b.show();
    }

    private void showEditDialog(final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_phongban, null);

        final TextInputEditText dialogTenpb = dialogView.findViewById(R.id.dialog_ten_phongban);
        PhongBan phongBan = arrayList.get(position);
        dialogTenpb.setText(phongBan.getTenPhongBan());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("EDIT Phòng Ban")
                .setView(dialogView)
                .setPositiveButton("SAVE", (dialog, which) -> {
                    phongBan.setTenPhongBan(dialogTenpb.getText().toString());
                    FileHelper fileHelper = new FileHelper(context);
                    fileHelper.WriteToFile_PHONGBAN(arrayList, "PHONGBAN.txt");
                    notifyDataSetChanged();
                    Toast.makeText(context, "Chỉnh Sửa Thành Công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }
}