package com.rakibofc.sqlitedatabase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText ename, email, epassword, emobile;
    Button binsert, bexit, bdisplay, bupdate, bdelete;
    String name, emailStr, password, mobile;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ename = findViewById(R.id.ename);
        email = findViewById(R.id.email);
        epassword = findViewById(R.id.epassword);
        emobile = findViewById(R.id.emobile);

        binsert = findViewById(R.id.binsert);
        bdisplay = findViewById(R.id.bdisplay);
        bupdate = findViewById(R.id.bupdate);
        bdelete = findViewById(R.id.bdelete);

        bexit = findViewById(R.id.bexit);

        db = openOrCreateDatabase("Mydb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(name VARCHAR UNIQUE, email VARCHAR, password VARCHAR, mobile VARCHAR UNIQUE); ");

        binsert.setOnClickListener(v -> {
            name = ename.getText().toString();
            emailStr = email.getText().toString();
            password = epassword.getText().toString();
            mobile = emobile.getText().toString();

            if (!name.isEmpty() && !emailStr.isEmpty() && !password.isEmpty() && !mobile.isEmpty()){

                try {
                    db.execSQL("INSERT INTO student VALUES('" + name + "','" + emailStr + "','" + password + "', '" + mobile + "');");
                    Toast.makeText(getApplicationContext(), "Row Inserted", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Name field can't be same", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Table Field Can't Be Empty", Toast.LENGTH_SHORT).show();
            }
        });

        bdisplay.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
            startActivity(intent);
            finish();
        });

        bupdate.setOnClickListener(view -> {
            name = ename.getText().toString();
            emailStr = email.getText().toString();
            mobile = emobile.getText().toString();

            if (!mobile.isEmpty()){
                if (!emailStr.isEmpty()){
                    db.execSQL("UPDATE student SET name = '" + name + "', password = '" + password + "', email = '" + emailStr + "' WHERE mobile = '" + mobile + "';");
                } else {
                    db.execSQL("UPDATE student SET name = '" + name + "' WHERE mobile = '" + mobile + "';");
                }
                Toast.makeText(getApplicationContext(), "Row Update", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Mobile field can't be empty", Toast.LENGTH_SHORT).show();
            }
        });

        bdelete.setOnClickListener(view -> {
            mobile = emobile.getText().toString();
            if (!mobile.isEmpty()){
                db.execSQL("DELETE FROM student WHERE mobile = '" + mobile + "';");
                Toast.makeText(getApplicationContext(), "Row Deleted", Toast.LENGTH_SHORT).show();
            }  else {
                Toast.makeText(getApplicationContext(), "Mobile field can't be empty", Toast.LENGTH_SHORT).show();
            }
        });

        bexit.setOnClickListener(v -> System.exit(0));

        Intent intent = new Intent(getApplicationContext(), Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 800000, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pendingIntent);

        this.registerReceiver(new MyReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}