package com.jaminoss.android.ourschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaminoss.android.ourschool.model.User;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdminViewsAllUsers extends AppCompatActivity {

    //private static ListView usersLv;
    private static ArrayList<User> users = new ArrayList<User>();
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Bind(R.id.search_et)
    EditText searched;
    @Bind(R.id.search_btn)
    Button search;
    @Bind(R.id.mylv)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_views_all_users);
        ButterKnife.bind(this);

        //usersLv = findViewById(R.id.mylv);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        search.setOnClickListener(view -> {
            String s = searched.getText().toString();
            ArrayList<User> newUsers = new ArrayList<User>();

            for (int i = 0 ; i < users.size() ; i++)
                if(users.get(i).username.contains(s))
                    newUsers.add(users.get(i));

            Collections.reverse(users);
            mListView.setAdapter(new AdapterUsers(getApplicationContext(),newUsers));

        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot user : dataSnapshot.getChildren()){

                    final String id =  user.getKey();
                    final String fname = user.child("fullname").getValue().toString();
                    final String uname = user.child("username").getValue().toString();
                    final String email = user.child("email").getValue().toString();
                    final String pass = user.child("password").getValue().toString();
                    final String dept = user.child("department").getValue().toString();
                    final String phone = user.child("phone").getValue().toString();
                    final String courses = user.child("courses").getValue().toString();
                    final String role = user.child("role").getValue().toString();

                    users.add(new User(id,fname,uname,pass,phone,dept,courses,email,role));
                }
                progressDialog.dismiss();
                Collections.reverse(users);
                mListView.setAdapter(new AdapterUsers(getApplicationContext(),users));

                mListView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent j = new Intent(getApplicationContext(), AdminViewUserDetail.class);
                    j.putExtra("id",users.get(i).id );
                    startActivity(j);
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }



}


