package com.jaminoss.android.ourschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button signin;
    private EditText username, pass;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
        progressDialog.setCancelable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("users");

        signin = findViewById(R.id.sign_in);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);


        if (firebaseAuth.getCurrentUser() != null) {
            Intent i = new Intent(getApplicationContext(), UserMain.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
        }

        signin.setOnClickListener(view -> {


            String u, p;
            u = username.getText().toString().trim();
            p = pass.getText().toString();

            if (u.equals("")) {
                username.setError("Required");
                return;
            }
            if (p.equals("")) {
                pass.setError("Required");
                return;
            }

            if (u.equals("admin") && p.equals("qwerty"))
                loginAdmin();
            else {

                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(u + "@gmail.com", p).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), UserMain.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });
    }

    private void loginAdmin() {
        Intent i = new Intent(getApplicationContext(), Admin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }
}
