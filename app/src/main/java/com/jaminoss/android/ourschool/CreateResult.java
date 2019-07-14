package com.jaminoss.android.ourschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;

public class CreateResult extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseRef;

    @Bind(R.id.save)
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_result);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child("results");

        save.setOnClickListener(v -> {
            final String maths, eng, phy, chm, bio;

        });
    }
}
