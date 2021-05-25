package com.example.mytodolist.base;

import android.widget.EditText;
import android.widget.Toast;

import com.example.mytodolist.base.BaseActivity;

/**
 * @program: MyToDoList
 * @description:
 */
public class BaseRegisterActivity extends BaseActivity {

    public boolean checkRegister1(EditText phone, EditText pwd1, EditText pwd2) {
        if (checkEditTextEmpty(phone)) {
            Toast.makeText(this, "Please Enter Your Phone.", Toast.LENGTH_SHORT).show();
            phone.setText("");
            phone.requestFocus();
            return false;
        } else if (checkEditTextEmpty(pwd1)) {
            Toast.makeText(this, "Please Set Password.", Toast.LENGTH_SHORT).show();
            pwd1.setText("");
            pwd1.requestFocus();
            return false;
        } else if (checkEditTextEmpty(pwd2)) {
            Toast.makeText(this, "Please Enter Password Again.", Toast.LENGTH_SHORT).show();
            pwd2.setText("");
            pwd2.requestFocus();
            return false;
        } else if (!isTextEqual(pwd1, pwd2)) {
            Toast.makeText(this, "Entered Passwords do not match.", Toast.LENGTH_SHORT).show();
            pwd1.setText("");
            pwd2.setText("");
            pwd1.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public boolean checkRegister2(EditText name, EditText birth, EditText email) {
        if (checkEditTextEmpty(name)) {
            Toast.makeText(this, "Enter Your Name.", Toast.LENGTH_SHORT).show();
            name.setText("");
            name.requestFocus();
            return false;
//        } else if (checkEditTextEmpty(birth)) {
//            Toast.makeText(this, "Choose Your Birthday.", Toast.LENGTH_SHORT).show();
//            birth.setText("");
//            birth.requestFocus();
//            return false;
//        } else if (checkEditTextEmpty(email)) {
//            Toast.makeText(this, "Enter Your Email.", Toast.LENGTH_SHORT).show();
//            email.setText("");
//            email.requestFocus();
//            return false;
        } else {
            return true;
        }
    }

    private boolean checkEditTextEmpty(EditText editText) {
        return editText.getText().toString().length() == 0;
    }

    private boolean isTextEqual(EditText et1, EditText et2) {
        return et1.getText().toString().equals(et2.getText().toString());
    }

}
