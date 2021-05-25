package com.example.mytodolist.entity;

public class User {
    private int    _id;
    private String phone;
    private String pwd;
    private String name;
    private String email;
    private String birth;
    static String phoneNow = ""; //当前登录的用户

    public User() {
    }

    public static String getPhoneNow() {
        return phoneNow;
    }

    public static void setPhoneNow(String phoneNow) {
        User.phoneNow = phoneNow;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + _id +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
