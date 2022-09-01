package com.rakibofc.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {

    TextView tname, tcollege, tpassword, troll;
    Button bprev, bnext, bback;
    SQLiteDatabase db;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        tname = findViewById(R.id.tname);
        tcollege = findViewById(R.id.tcollege);
        tpassword = findViewById(R.id.tpassword);
        troll = findViewById(R.id.troll);
        bprev = findViewById(R.id.bprev);
        bnext = findViewById(R.id.bnext);
        bback = findViewById(R.id.bback);

        db = openOrCreateDatabase("Mydb", MODE_PRIVATE, null);

        @SuppressLint("Recycle")
        final Cursor c = db.rawQuery("select * from student", null);

        c.moveToFirst();

        tname.setText(c.getString(c.getColumnIndex("name")));
        tcollege.setText(c.getString(c.getColumnIndex("email")));
        tpassword.setText(c.getString(c.getColumnIndex("password")));
        troll.setText(c.getString(c.getColumnIndex("mobile")));

        bback.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        bnext.setOnClickListener(v -> {

            try {
                c.moveToNext();
                tname.setText(c.getString(c.getColumnIndex("name")));
                tcollege.setText(c.getString(c.getColumnIndex("email")));
                tpassword.setText(c.getString(c.getColumnIndex("password")));
                troll.setText(c.getString(c.getColumnIndex("mobile")));

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Last Record", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
        bprev.setOnClickListener(v -> {

            try {
                c.moveToPrevious();
                tname.setText(c.getString(c.getColumnIndex("name")));
                tcollege.setText(c.getString(c.getColumnIndex("email")));
                tpassword.setText(c.getString(c.getColumnIndex("password")));
                troll.setText(c.getString(c.getColumnIndex("mobile")));

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "First Record", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }
}