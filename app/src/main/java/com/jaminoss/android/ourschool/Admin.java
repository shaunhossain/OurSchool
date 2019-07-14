package com.jaminoss.android.ourschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jaminoss.android.ourschool.views.ImageHelper;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
@SuppressWarnings("deprecation")
public class Admin extends AppCompatActivity {

    @Bind(R.id.dp)
    ImageView avi;
    @Bind(R.id.name)
    TextInputEditText full_name;
    @Bind(R.id.dept)
    TextInputEditText studentClass;
    @Bind(R.id.Courses)
    TextInputEditText subjects;
    @Bind(R.id.ph)
    TextInputEditText phone;
    @Bind(R.id.email)
    TextInputEditText email;
    @Bind(R.id.pass)
    TextInputEditText pass;
    @Bind(R.id.uname)
    TextInputEditText uname;
    @Bind(R.id.add_user)
    FloatingActionButton save;
    @Bind(R.id.view_users)
    FloatingActionButton view;
    @Bind(R.id.role)
    RadioGroup rGroup;
    @Bind(R.id.rb_std)
    RadioButton radio_student;

    static Uri uri;

    ProgressDialog progressDialog;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating account...");
        progressDialog.setCancelable(false);

        radio_student.setChecked(true);

        firebaseAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference().child("Photos");


        view.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),AdminViewsAllUsers.class);
            startActivity(i);
        });

        avi.setOnClickListener(view -> {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setAction("android.intent.action.PICK");
            cropIntent.setType("image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            cropIntent.putExtra("outputY", ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 2);
        });

        save.setOnClickListener(view -> {
            final String n, p, d, e, c, un, pas;
            n = full_name.getText().toString();
            p = phone.getText().toString();
            d = studentClass.getText().toString();
            e = email.getText().toString();
            c = subjects.getText().toString();
            un = uname.getText().toString();
            pas = pass.getText().toString();

            if (n.equals("")) {
                full_name.setError("Required");
                return;
            }
            if (p.equals("")) {
                phone.setError("Required");
                return;
            }
            if (e.equals("")) {
                email.setError("Required");
                return;
            }
            if (d.equals("")) {
                studentClass.setError("Required");
                return;
            }
            if (c.equals("")) {
                subjects.setError("Required");
                return;
            }
            if (un.equals("")) {
                uname.setError("Required");
                return;
            }
            if (pas.equals("")) {
                pass.setError("Required");
                return;
            }

            RadioButton checkedRadioButton = rGroup.findViewById(rGroup.getCheckedRadioButtonId());
            final String role = checkedRadioButton.getText().toString();


            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(un+"@gmail.com", pas).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = users.child(user_id);
                    current_user_db.child("fullname").setValue(n);
                    current_user_db.child("username").setValue(un);
                    current_user_db.child("password").setValue(pas);
                    current_user_db.child("phone").setValue(p);
                    current_user_db.child("department").setValue(d);
                    current_user_db.child("courses").setValue(c);
                    current_user_db.child("email").setValue(e);
                    current_user_db.child("role").setValue(role);

                    storageReference.child(un + ".jpeg").putFile(uri).
                            addOnSuccessListener(taskSnapshot -> {
                                Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }).addOnFailureListener(e1 -> {
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            });
        });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            this.avi.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            uri = Uri.parse(MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "Title", null));
        }
    }
    }
