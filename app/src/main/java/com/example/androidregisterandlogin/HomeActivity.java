package com.example.androidregisterandlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
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
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {


    private EditText NAME, LASTNAME, SURNAME, PHONENUMBER, EXPERTISE, LOCATION;
    private Button btn_logout, btn_photUpload;
    SessionManager sessionManager;
    String getEmail;
    private Menu action;
    private Bitmap bitmap;
    CircleImageView profileImage;
    private static String URL_READ = "https://techdudesolutions.com/android_register_login/read_detail.php";
    private static String URL_EDIT = "https://techdudesolutions.com/android_register_login/edit_detail.php";
    private static String URL_UPLOAD = "https://techdudesolutions.com/android_register_login/upload.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();


        NAME = findViewById(R.id.fname);
        btn_photUpload = findViewById(R.id.btn_photo);
        profileImage = findViewById(R.id.profile_image);
        LASTNAME = findViewById(R.id.lname);
        SURNAME = findViewById(R.id.sname);
        PHONENUMBER = findViewById(R.id.phonenum);
        EXPERTISE = findViewById(R.id.exp);
        LOCATION = findViewById(R.id.loc);

        //try
        NAME.setFocusableInTouchMode(false);
        LASTNAME.setFocusableInTouchMode(false);
        SURNAME.setFocusableInTouchMode(false);
        PHONENUMBER.setFocusableInTouchMode(false);
        EXPERTISE.setFocusableInTouchMode(false);
        LOCATION.setFocusable(false);

        NAME.setFocusable(false);
        LASTNAME.setFocusable(false);
        SURNAME.setFocusable(false);
        PHONENUMBER.setFocusable(false);
        EXPERTISE.setFocusable(false);
        LOCATION.setFocusable(false);


        HashMap<String, String> user = sessionManager.getUserDetails();
        getEmail = user.get(SessionManager.EMAIL);


      /* btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });*/

        btn_photUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), 1);


            }
        });
    }


    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //  Log.i(TAG,response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");

                            if (code.equals("failed")) {
                                Toast.makeText(HomeActivity.this, "Error displaying details", Toast.LENGTH_SHORT).show();
                            } else {


                                String strName = jsonObject.getString("name").trim();
                                String strLastname = jsonObject.getString("lastname").trim();
                                String strSurname = jsonObject.getString("surname").trim();
                                String strPhonenumber = jsonObject.getString("phonenumber").trim();
                                String strExpertise = jsonObject.getString("expertise").trim();
                                String strLocation = jsonObject.getString("location").trim();
                                String imageurl = jsonObject.getString("photo");


                                NAME.setText(strName);
                                LASTNAME.setText(strLastname);
                                SURNAME.setText(strSurname);
                                PHONENUMBER.setText(strPhonenumber);
                                EXPERTISE.setText(strExpertise);
                                LOCATION.setText(strLocation);

                                Picasso.with(HomeActivity.this).load(imageurl).fit().centerInside().fit()
                                        .into(profileImage);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HomeActivity.this, "Error reading profile " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, "Error reading profile " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", getEmail);
                return params;
            }
        };

        MySingleton.getInstance(HomeActivity.this).addToRequestque(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);

        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:
                NAME.setFocusableInTouchMode(true);
                LASTNAME.setFocusableInTouchMode(true);
                SURNAME.setFocusableInTouchMode(true);
                PHONENUMBER.setFocusableInTouchMode(true);
                EXPERTISE.setFocusable(true);
                LOCATION.setFocusableInTouchMode(true);


                InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethod.showSoftInput(NAME, InputMethodManager.SHOW_IMPLICIT);
                inputMethod.showSoftInput(LASTNAME, InputMethodManager.SHOW_IMPLICIT);
                inputMethod.showSoftInput(SURNAME, InputMethodManager.SHOW_IMPLICIT);
                inputMethod.showSoftInput(PHONENUMBER, InputMethodManager.SHOW_IMPLICIT);
                inputMethod.showSoftInput(EXPERTISE, InputMethodManager.SHOW_IMPLICIT);
                inputMethod.showSoftInput(LOCATION, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                SaveEditDetail();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);


                NAME.setFocusableInTouchMode(false);
                LASTNAME.setFocusableInTouchMode(false);
                SURNAME.setFocusableInTouchMode(false);
                PHONENUMBER.setFocusableInTouchMode(false);
                EXPERTISE.setFocusableInTouchMode(false);
                LOCATION.setFocusableInTouchMode(false);

                NAME.setFocusable(false);
                LASTNAME.setFocusable(false);
                SURNAME.setFocusable(false);
                PHONENUMBER.setFocusable(false);
                EXPERTISE.setFocusable(false);
                LOCATION.setFocusable(false);


                return true;

            case R.id.menu_logout:

                sessionManager.logout();


                return true;



            default:
                return super.onOptionsItemSelected(item);


        }


    }

    //save edit details

    private void SaveEditDetail() {
        final String name = this.NAME.getText().toString().toUpperCase().trim();
        final String lastname = this.LASTNAME.getText().toString().toUpperCase().trim();
        final String surname = this.SURNAME.getText().toString().toUpperCase().trim();
        final String phonenumber = this.PHONENUMBER.getText().toString().trim();
        final String expertise = this.EXPERTISE.getText().toString().toUpperCase().trim();
        final String location = this.LOCATION.getText().toString().toUpperCase().trim();
        final String email = getEmail;


        if (name.isEmpty() || lastname.isEmpty() || surname.isEmpty() || phonenumber.isEmpty() ||
                expertise.isEmpty() || location.isEmpty()) {
            NAME.setError("Please enter your firstname");
            LASTNAME.setError("Please enter your lastname");
            SURNAME.setError("Please enter your surname");
            PHONENUMBER.setError("Please enter your phonenumber");
            EXPERTISE.setError("Please enter your expertise");
            LOCATION.setError("Please enter your location");


        } else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Saving...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(HomeActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    sessionManager.createSession(name, email, lastname, surname, phonenumber, expertise, location);
                                }


                            } catch (JSONException e) {

                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Error updating profile" + e.toString(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(HomeActivity.this, "Error updating profile" + error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("lastname", lastname);
                    params.put("surname", surname);
                    params.put("phonenumber", phonenumber);
                    params.put("expertise", expertise);
                    params.put("location", location);
                    params.put("email", email);


                    return params;
                }
            };

            MySingleton.getInstance(HomeActivity.this).addToRequestque(stringRequest);

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                profileImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(HomeActivity.this, "Could not fetch image" + e.toString(), Toast.LENGTH_SHORT).show();
            }

            UploadPicture(getEmail, getStringImage(bitmap));


        }


    }

    private void UploadPicture(final String email, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(HomeActivity.this, "Upload Successful", Toast.LENGTH_SHORT);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HomeActivity.this, "Try Again" + e.toString(), Toast.LENGTH_SHORT);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, "Try Again" + error.toString(), Toast.LENGTH_SHORT);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("photo", photo);
                return params;
            }
        };
        MySingleton.getInstance(HomeActivity.this).addToRequestque(stringRequest);

    }


    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;

    }
//



}




