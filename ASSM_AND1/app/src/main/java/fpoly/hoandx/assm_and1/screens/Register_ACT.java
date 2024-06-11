package fpoly.hoandx.assm_and1.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;

import fpoly.hoandx.assm_and1.R;

public class Register_ACT extends AppCompatActivity {

    Button btnregsister;
    TextView tv_login;
    EditText edt_username, edt_password, edt_password_comfrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnregsister = findViewById(R.id.regiter);
        tv_login = findViewById(R.id.txt_dn);
        edt_username = findViewById(R.id.edt_username_regiter);
        edt_password = findViewById(R.id.edt_password_regiter);
        edt_password_comfrim = findViewById(R.id.edt_password_comfrim);

        btnregsister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                String password_confirm = edt_password_comfrim.getText().toString();

                if (username.isEmpty() || password.isEmpty() || password_confirm.isEmpty()) {
                    Toast.makeText(Register_ACT.this, "Vui Lòng Không Để Trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(password_confirm)) {
                    Toast.makeText(Register_ACT.this, "Mật Khẩu Không Khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveAccountToInternalStorage(username, password);

                Toast.makeText(Register_ACT.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register_ACT.this, Login_ACT.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("password", password);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        tv_login.setOnClickListener(v -> {
            Intent intent = new Intent(Register_ACT.this, Login_ACT.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveAccountToInternalStorage(String username, String password) {
        String accountData = username + "," + password + "\n";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("account.txt", Context.MODE_PRIVATE | Context.MODE_APPEND);
            fos.write(accountData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving account", Toast.LENGTH_SHORT).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
