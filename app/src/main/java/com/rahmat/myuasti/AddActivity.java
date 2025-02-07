package com.rahmat.myuasti;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText nama,npm,nohp,alamat,murl;
    Button btntmh,btnkbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nama=(EditText) findViewById(R.id.txtnama);
        npm=(EditText) findViewById(R.id.txtnpm);
        nohp=(EditText) findViewById(R.id.txtnohp);
        alamat=(EditText) findViewById(R.id.txtalamat);
        murl=(EditText) findViewById(R.id.txturl);
        btntmh=(Button) findViewById(R.id.btntbh);
        btnkbl=(Button) findViewById(R.id.btnkbl);

        btntmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertdata();
                clearall();

            }
        });

        btnkbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void insertdata(){
        Map<String,Object> map = new HashMap<>();
        map.put("nama",nama.getText().toString());
        map.put("npm",npm.getText().toString());
        map.put("nohp",nohp.getText().toString());
        map.put("alamat",alamat.getText().toString());
        map.put("murl",murl.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("mahasiswa").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data berhasil di tamabahkan", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(AddActivity.this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void clearall()
    {
        nama.setText("");
        npm.setText("");
        nohp.setText("");
        alamat.setText("");
        murl.setText("");
    }
}