package fpoly.hoandx.assm_and1.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import fpoly.hoandx.assm_and1.MainActivity;
import fpoly.hoandx.assm_and1.R;

public class Login_ACT extends AppCompatActivity {

    Button btnLogin;
    TextView tv_dk;
    EditText edt_username, edt_password;

    CheckBox chk_remember_me;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLogin = findViewById(R.id.btn_login);
        tv_dk = findViewById(R.id.txt_dk);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        chk_remember_me = findViewById(R.id.checkBox);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

       // Đọc thông tin đăng nhập từ file
        loadLoginDetails();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_in = edt_username.getText().toString();
                String password_in = edt_password.getText().toString();

                if (validateLogin(username_in, password_in)) {
                    Toast.makeText(Login_ACT.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                    if (chk_remember_me.isChecked()) {
                        saveLoginDetails(username_in, password_in);
                    } else {
                        clearLoginDetails();
                    }
                    Intent intent = new Intent(Login_ACT.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_ACT.this, "Sai Tài Khoản Hoặc Mật Khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_ACT.this, Register_ACT.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveLoginDetails(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("rememberMe", true);
        editor.apply();
    }

    private void clearLoginDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.putBoolean("rememberMe", false);
        editor.apply();
    }

    private void loadLoginDetails() {
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            String username = sharedPreferences.getString("username", "");
            String password = sharedPreferences.getString("password", "");
            edt_username.setText(username);
            edt_password.setText(password);
            chk_remember_me.setChecked(true);
        }
    }

    private boolean validateLogin(String username_in, String password_in) {
        FileInputStream fis = null;
        try {
            fis = openFileInput("account.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] account = line.split(",");
                if (account.length == 2) {
                    String savedUsername = account[0];
                    String savedPassword = account[1];
                    if (savedUsername.equals(username_in) && savedPassword.equals(password_in)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}