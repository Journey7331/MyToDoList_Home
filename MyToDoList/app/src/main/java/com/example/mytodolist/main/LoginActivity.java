package com.example.mytodolist.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytodolist.R;
import com.example.mytodolist.base.BaseActivity;
import com.example.mytodolist.database.MyDatabaseHelper;
import com.example.mytodolist.database.UserDB;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText et_username, et_password;
    Button btn_login;
    TextView tv_register;

    String phone;
    String password;
    MyDatabaseHelper mysql = new MyDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyDatabaseHelper mysql = new MyDatabaseHelper(this);
        mysql.getWritableDatabase();

        et_username = findViewById(R.id.et_loginUsername);
        et_password = findViewById(R.id.et_loginPassword);
        tv_register = findViewById(R.id.tv_register);
        btn_login = findViewById(R.id.btn_login);
        phone = "";
        password = "";

        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            phone = et_username.getText().toString();
            password = et_password.getText().toString();
            if("".equals(phone)){
                Toast.makeText(this, "Please Enter Phone.", Toast.LENGTH_SHORT).show();
                return;
            }
            if("".equals(password)){
                Toast.makeText(this, "Please Enter Password.", Toast.LENGTH_SHORT).show();
                return;
            }
            int ret = UserDB.login(mysql, phone, password);
            if (ret == 0){
                Toast.makeText(this, "Password Wrong.", Toast.LENGTH_SHORT).show();
            }else if (ret == -1){
                Toast.makeText(this, "This Phone Hasn't Been Registered.", Toast.LENGTH_SHORT).show();
            }else if(ret == 1){
                Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        } else if (v.getId() == R.id.tv_register) {
            Intent intent = new Intent(this, RegisterActivity1.class);
            startActivity(intent);
            finish();
        }

    }
}
