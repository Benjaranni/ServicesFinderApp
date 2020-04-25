package com.example.androidregisterandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_URL;
import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_expertise;
import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_firstname;
import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_lastname;
import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_location;
import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_mobile;
import static com.example.androidregisterandlogin.RecyclerActivity.EXTRA_surname;

public class DetailActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
     String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String firstname = intent.getStringExtra(EXTRA_firstname);
        String lastname = intent.getStringExtra(EXTRA_lastname);
        String surname = intent.getStringExtra(EXTRA_surname);
        final String mobile = intent.getStringExtra(EXTRA_mobile);
        String expertise = intent.getStringExtra(EXTRA_expertise);
        String location = intent.getStringExtra(EXTRA_location);

        ImageView imagecall = findViewById(R.id.image_call);



        ImageView imageView = findViewById(R.id.imageView_detail);
        TextView textViewfirstname = findViewById(R.id.textfname_details);
        TextView textViewlastname = findViewById(R.id.textlname_details);
        TextView textViewsurname = findViewById(R.id.textsname_details);
        final TextView textViewmobile = findViewById(R.id.textphone_details);
        TextView textViewexpertise = findViewById(R.id.textexpertise_details);
        TextView textViewlocation  = findViewById(R.id.textlocation_details);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewfirstname.setText(firstname);
        textViewlastname.setText(lastname);
        textViewsurname.setText(surname);
        textViewmobile.setText(mobile);
        textViewexpertise.setText(expertise);
        textViewlocation.setText(location);

        imagecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();

            }
        });

        num = textViewmobile.getText().toString();





    }

    public void makePhoneCall(){
        if (num.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(DetailActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DetailActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);

            } else {
                String dial = "tel:" + num;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }

        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                makePhoneCall();

            }else {
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
