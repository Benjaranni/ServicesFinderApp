package com.example.androidregisterandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerActivity extends AppCompatActivity  implements MyAdapter.OnItemClickListener{


    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_firstname = "firstname";
    public static final String EXTRA_lastname = "lastname";
    public static final String EXTRA_surname = "surname";
    public static final String EXTRA_mobile = "mobile";
    public static final String EXTRA_expertise = "expertise";
    public static final String EXTRA_location = "location";

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ApiInterface apiInterface;
    FloatingActionButton fab;
    CoordinatorLayout rootlayout;
    SwipeRefreshLayout RefreshLayout;



    private List<ListItem> listItems;
    /* private static final String URL_DATA = "http://192.168.43.133/android_register_login/fetch_recycler.php";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        RefreshLayout = findViewById(R.id.refreshLayout);


        if(!isConnected(RecyclerActivity.this)) builDialog(RecyclerActivity.this).show();
        else {

        }

        recyclerView = findViewById(R.id.recyclerView);
        rootlayout = findViewById(R.id.root);
        fab = findViewById(R.id.fab);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchList("");

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(RecyclerActivity.this,HomeActivity.class);
                startActivity(a);
            }
        });

        getSupportActionBar().setTitle("Services");







        listItems = new ArrayList<>();

        /*loadRecyclerViewData();*/
        fetchList("");  // without keyword


    }

    private void fetchList(String key){
        RefreshLayout.setRefreshing(false);
        apiInterface =  ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<ListItem>> call = apiInterface.getList(key);
        call.enqueue(new Callback<List<ListItem>>() {
            @Override
            public void onResponse(Call<List<ListItem>> call, Response<List<ListItem>> response) {
                RefreshLayout.setRefreshing(false);
                listItems = response.body();
                adapter = new MyAdapter(listItems,RecyclerActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(RecyclerActivity.this);
            }

            @Override
            public void onFailure(Call<List<ListItem>> call, Throwable t) {
                RefreshLayout.setRefreshing(false);
                Toast.makeText(RecyclerActivity.this,"Error on :" + t.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchList(newText);
                return false;
            }
        });
        return true;
    }


    /*private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONArray array = new JSONArray(response);


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("name"),
                                        o.getString("phonenumber"),
                                        o.getString("photo"),
                                        o.getString("lastname"),
                                        o.getString("surname"),
                                        o.getString("expertise"),
                                        o.getString("location")
                                );
                                listItems.add(item);


                            }

                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            adapter.setOnItemClickListener(MainActivity.this);


                        } catch (JSONException e) {
                            progressDialog.dismiss();

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


    @Override
    public void onItemClick(int position) {

        Intent detailIntent = new Intent(this,DetailActivity.class);
        ListItem clickedItem =  listItems.get(position);

        detailIntent.putExtra(EXTRA_URL,clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_firstname,clickedItem.getHead());
        detailIntent.putExtra(EXTRA_lastname,clickedItem.getLname());
        detailIntent.putExtra(EXTRA_surname,clickedItem.getSname());
        detailIntent.putExtra(EXTRA_mobile,clickedItem.getDesc());
        detailIntent.putExtra(EXTRA_expertise,clickedItem.getExp());
        detailIntent.putExtra(EXTRA_location,clickedItem.getLocate());

        startActivity(detailIntent);

    }

    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();



        if(netinfo != null && netinfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        }else {
            return false;
        }
    }

    public AlertDialog.Builder builDialog(Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("NO INTERNET CONNECTION");
        builder.setMessage("Please connect to the internet.");


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;
    }







}


