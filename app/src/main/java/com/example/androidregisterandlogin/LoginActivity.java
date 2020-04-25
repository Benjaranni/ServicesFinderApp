package com.example.androidregisterandlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText Email,Password;
    private Button btn_login;
    private TextView link_regist;
    AlertDialog.Builder builder;
    private static String URL_LOGIN = "https://techdudesolutions.com/android_register_login/login.php";

    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        builder = new AlertDialog.Builder(LoginActivity.this);

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        btn_login = (Button)findViewById(R.id.btn_login);
        link_regist = (TextView)findViewById(R.id.link_regist);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mEmail = Email.getText().toString();
                final String mPassword = Password.getText().toString();

                if(!mEmail.isEmpty() || !mPassword.isEmpty()){

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");

                                        if(code.equals("login_failed")) {
                                            builder.setTitle("Login Error");
                                            displayAlert(jsonObject.getString("message"));
                                        }
                                        else if(code.equals("user_failed")){
                                            builder.setTitle("Login Error");
                                            displayAlert(jsonObject.getString("message"));


                                        }
                                        else{




                                            String username  =  jsonObject.getString("name");
                                            String useremail = jsonObject.getString("email");
                                            String userlastname = jsonObject.getString("lastname");
                                            String usersurname = jsonObject.getString("surname");
                                            String userphonenumber = jsonObject.getString("phonenumber");
                                            String userexpertise = jsonObject.getString("expertise");
                                            String userlocation = jsonObject.getString("location");

                                            sessionManager.createSession(username,useremail,userlastname,usersurname,
                                                    userphonenumber,userexpertise,userlocation);


                                               Intent intent = new Intent(LoginActivity.this,HomeActivity.class);

                                               startActivity(intent);

                                            }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();

                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Login Failed "+error.toString(),Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params  = new HashMap<>();
                            params.put("email",mEmail);
                            params.put("password",mPassword);
                            return params;
                        }
                    };
                    MySingleton.getInstance(LoginActivity.this).addToRequestque(stringRequest);





                }else{
                    Email.setError("Please insert email");
                    Password.setError("Please insert password");
                }
            }



        });


        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Email.setText("");
                Password.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }








}
