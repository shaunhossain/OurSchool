package com.jaminoss.android.ourschool.model;

/**
 * Created by Talha on 12/11/2017.
 */

public class User {
    public String username;
    public String fullname;
    public String email;
    public String password;
    public String phone;
    public String department;
    public String courses;
    public String id;
    public String role;


    public User(String i ,String fn , String  un , String p, String ph , String d , String c , String e,String r)
    {
        fullname = fn;
        username = un;
        id = i;
        email = e;
        password = p;
        phone = ph;
        role = r;
        department = d;
        courses = c;
    }
}
