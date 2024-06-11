package fpoly.hoandx.assm_and1.database;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fpoly.hoandx.assm_and1.models.NhanVien;
import fpoly.hoandx.assm_and1.models.PhongBan;

public class FileHelper {
    Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    public void WriteToFile_NHANVIEN(ArrayList<NhanVien> arrayList, String fileName) {
        File file = context.getFilesDir();
        File file_add = new File(file, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file_add);
            ObjectOutput objectOutput = new ObjectOutputStream(fileOutputStream);
            objectOutput.writeObject(arrayList);
            objectOutput.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NhanVien> ReadFromFile_NHANVIEN(String fileName) {
        ArrayList<NhanVien> arrayList = null;
        try {
            File file = context.getFilesDir();
            File file_read=new File(file,fileName);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file_read);
                ObjectInput objectInput = new ObjectInputStream(fileInputStream);
                arrayList = (ArrayList<NhanVien>) objectInput.readObject();
                objectInput.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public void WriteToFile_PHONGBAN(ArrayList<PhongBan> arrayList, String fileName) {
        File file = context.getFilesDir();
        File file_add = new File(file, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file_add);
            ObjectOutput objectOutput = new ObjectOutputStream(fileOutputStream);
            objectOutput.writeObject(arrayList);
            objectOutput.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PhongBan> ReadFromFile_PHONGBAN(String fileName) {
        ArrayList<PhongBan> arrayList = null;
        try {
            File file = context.getFilesDir();
            File file_read=new File(file,fileName);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file_read);
                ObjectInput objectInput = new ObjectInputStream(fileInputStream);
                arrayList = (ArrayList<PhongBan>) objectInput.readObject();
                objectInput.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
