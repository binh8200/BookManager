package com.example.quanlysach;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysach.DAO.NguoidungDAO;
import com.example.quanlysach.Dulieu.Nguoidung;

public class Login extends AppCompatActivity {
    EditText edUserName, edPassword;
    Button btnLogin, btnCancel;
    CheckBox chkRememberPass;
    String strUser, strPass;
    NguoidungDAO nguoiDungDAO;
    SharedPreferences luutru;

    RelativeLayout rellay1, rellay2;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        edUserName = (EditText) findViewById(R.id.ed_UserName);
        edPassword = (EditText) findViewById(R.id.ed_Password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        nguoiDungDAO = new NguoidungDAO(Login.this);

//        Boolean luuthongtin = luutru.getBoolean("save_information", false);
//        if (luuthongtin) {
//            edUserName.setText(luutru.getString("username", ""));
//            edPassword.setText(luutru.getString("password", ""));
//            chkRememberPass.setChecked(true);
//        }

    }

    public void checkLogin(View v) {
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();
        Nguoidung user = new Nguoidung(strUser, strPass);
        Boolean ds = nguoiDungDAO.Luu(strUser, strPass);
        rememberUser(strUser, strPass, ds);
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (strUser.equalsIgnoreCase("admin") && strPass.equalsIgnoreCase("123")) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            } else if (ds == true) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            //xoa tinh trang luu tru truoc do
            edit.clear();
        } else {
            //luu du lieu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        //luu lai toan bo
        edit.commit();
    }
}