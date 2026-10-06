package com.example.upideeplinkintentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnPayNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", "payee@upi") // Payee UPI ID
                        .appendQueryParameter("pn", "Payee Name") // Payee Name
                        .appendQueryParameter("tn", "Transaction Note") // Transaction Note
                        .appendQueryParameter("am", "100.00") // Amount
                        .appendQueryParameter("cu", "INR") // Currency
                        .build();

                Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
                upiPayIntent.setData(uri);

// Present a chooser dialog to the user showing available UPI apps
                Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
                if (null != chooser.resolveActivity(getPackageManager())) {
                    startActivityForResult(chooser, 100);
                } else {
                    Toast.makeText(MainActivity.this, "No UPI app found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}