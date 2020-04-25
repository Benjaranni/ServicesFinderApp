package com.example.androidregisterandlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {


    private EditText Name,Email,Password,C_Password,LastName,Surname,PhoneNumber,Expertise,Location;
    private Button btn;

    AlertDialog.Builder builder;
    private  static  String URL_REGIST = "https://techdudesolutions.com/android_register_login/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Name = findViewById(R.id.name);
        LastName = findViewById(R.id.lname);
        Surname = findViewById(R.id.sname);
        PhoneNumber = findViewById(R.id.phonenumber);
        Expertise = findViewById(R.id.expertise);
        Location = findViewById(R.id.location);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        C_Password = findViewById(R.id.c_password);
        builder = new AlertDialog.Builder(RegisterActivity.this);
        btn = findViewById(R.id.btn_regist);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String name = Name.getText().toString().toUpperCase();
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                final String cpassword = C_Password.getText().toString();
                final String lastname = LastName.getText().toString().toUpperCase();
                final String surname = Surname.getText().toString().toUpperCase();
                final String phonenumber = PhoneNumber.getText().toString();
                final String expertise = Expertise.getText().toString().toUpperCase();
                final String location = Location.getText().toString().toUpperCase();


                if (name.equals("") || email.equals("") || password.equals("") || cpassword.equals("") ||
                        lastname.equals("") || surname.equals("") || phonenumber.equals("")  || expertise.equals("") || location.equals("")) {
                    builder.setTitle("Error");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("input_error");

                } else if(phonenumber.length()<=9 || phonenumber.length()>10){
                    PhoneNumber.setError("Please enter a valid phone number");

                }else if(!Patterns.PHONE.matcher(phonenumber).matches()){
                    PhoneNumber.setError("Please enter a valid phone number");


                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Please enter a valid email address");


                }else if (!(password.equals(cpassword))){

                        builder.setTitle("Error");
                        builder.setMessage("Please fields do not match");
                        displayAlert("input_error");


                    }

                else {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Server Response");
                                            builder.setMessage(message);
                                            displayAlert(code);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT).show();


                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_SHORT).show();





                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("name", name);
                                params.put("email", email);
                                params.put("password", password);
                                params.put("lastname",lastname);
                                params.put("surname",surname);
                                params.put("phonenumber",phonenumber);
                                params.put("expertise",expertise);
                                params.put("location",location);
                                return params;
                            }

                        };

                        MySingleton.getInstance(RegisterActivity.this).addToRequestque(stringRequest);

                    }
                }

        });


    }


    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (code.equals("input_error")) {
                    Password.setText("");
                    C_Password.setText("");
                } else if (code.equals("reg_success")) {
                    finish();
                } else if (code.equals("reg_failed")) {
                    Name.setText("");
                    Email.setText("");
                    Password.setText("");
                    C_Password.setText("");
                    LastName.setText("");
                    Surname.setText("");
                    PhoneNumber.setText("");
                    Expertise.setText("");
                    Location.setText("");
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
