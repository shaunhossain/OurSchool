package com.jaminoss.android.ourschool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.jaminoss.android.ourschool.model.User;
import com.jaminoss.android.ourschool.views.ImageHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdminViewUserDetail extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    //ProgressDialog progressDialog;
    private DatabaseReference users;
    private static User myUser;

    @Bind(R.id.user_main_dp)
    ImageView dp;
    @Bind(R.id.user_main_username)
    TextView username;
    @Bind(R.id.fname_main)
    TextView fullname;
    @Bind(R.id.pass_det)
    TextView password;
    @Bind(R.id.phone_main)
    TextView phoneNo;
    @Bind(R.id.courses_main)
    TextView subjects;
    @Bind(R.id.email_main)
    TextView emailId;
    @Bind(R.id.dept_main)
    TextView studentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("users");
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        final String key = getIntent().getExtras().getString("id");
        users = users.child(key);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot user) {

                final String fname = user.child("fullname").getValue().toString();
                final String uname = user.child("username").getValue().toString();
                final String email = user.child("email").getValue().toString();
                final String pass = user.child("password").getValue().toString();
                final String dept = user.child("department").getValue().toString();
                final String phone = user.child("phone").getValue().toString();
                final String courses = user.child("courses").getValue().toString();
                final String role = user.child("role").getValue().toString();

                myUser = new User(key, fname, uname, pass, phone, dept, courses, email, role);

                username.setText(myUser.username + " (" + myUser.role + ")");
                fullname.append(myUser.fullname);
                password.append(myUser.password);
                phoneNo.append(myUser.phone);
                studentClass.append(myUser.department);
                subjects.append(myUser.courses);
                emailId.append(myUser.email);

                String child = "Photos/" + uname + ".jpeg";

                final File localFile;
                try {
                    localFile = File.createTempFile("album2", "png");
                    FirebaseStorage.getInstance().getReference().child(child)
                            .getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                bitmap = ImageHelper.getRoundedCornerBitmap(bitmap, 200);
                                dp.setImageBitmap(bitmap);
                        Picasso.with(AdminViewUserDetail.this)
                                .load(localFile)
                                .networkPolicy(NetworkPolicy.OFFLINE)
                                .into(dp, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Picasso.with(AdminViewUserDetail.this)
                                                .load(localFile)
                                                .into(dp);
                                    }
                                });
                            }
                    );
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
