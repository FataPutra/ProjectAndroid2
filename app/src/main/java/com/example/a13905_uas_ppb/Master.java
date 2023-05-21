package com.example.a13905_uas_ppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Master extends AppCompatActivity {

    Button buttontmbh , tblexit;
    RecyclerView recyclerView;
    ArrayList<modelPenjualan> menuMakananArrayList = new ArrayList<>();
    ArrayList<ModelPenjualan2> menuMakananArrayList2 = new ArrayList<>();
    ArrayList<ModelMenuMakanan2> menuMakananArrayList3 = new ArrayList<>();
    String DATA_JSON_STRING, data_json_string;
    ProgressDialog progressDialog;
    ListAdapterMenuMakananMaster adapter;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference dbr;
    int countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        tblexit = findViewById(R.id.buttonexit);
        buttontmbh = findViewById(R.id.tbltmbh);
        recyclerView = findViewById(R.id.recyclerviewMenuMakanan);
        recyclerView.setHasFixedSize(true);
        adapter = new ListAdapterMenuMakananMaster(menuMakananArrayList, this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(Master.this);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(Master.this);
        dbr = FirebaseDatabase.getInstance().getReference();

        tampilBarang();
       // tampilBarang2();

        tblexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Master.this , Dashboard.class);
                startActivity(intent);
            }
        });

        //READ DATA
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readDataFromServer();
            }
        }, 1000);

        buttontmbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),TambahData.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void getJSON(){
        new BackgroundTask().execute();
    }

    public void readDataFromServer(){
        if(checkNetworkConnection()){
            menuMakananArrayList.clear();
            try {
                JSONObject object = new JSONObject(data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String kode ;
                String nama_barang;
                String satuan;
                String jumlah;
                String harga;
                String terjual;

                while (countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    kode = jsonObject.getString("kode");
                    nama_barang = jsonObject.getString("nama_barang");
                    satuan = jsonObject.getString("satuan");
                    jumlah = jsonObject.getString("jumlah");
                    harga = jsonObject.getString("harga");
                    terjual = jsonObject.getString("terjual");

                    menuMakananArrayList.add(new modelPenjualan(kode,nama_barang,satuan,jumlah,harga,terjual));
                    countData++;
                }
                countData = 0;
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    class BackgroundTask extends AsyncTask<Void , Void, String>{

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DbContract.SERVER_GET_PENJUALAN_URL ;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((DATA_JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(DATA_JSON_STRING+ "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            data_json_string = result;
        }
    }

    public void tampilBarang(){
        dbr.child("Penjualan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ModelPenjualan2 modelPenjualan2 = dataSnapshot.getValue(ModelPenjualan2.class);
                    modelPenjualan2.setKey(dataSnapshot.getKey());
                    menuMakananArrayList2.add(modelPenjualan2);
                }

                ListAdapterMenuMakananMaster2 adapter2 = new ListAdapterMenuMakananMaster2(menuMakananArrayList2,Master.this);
                recyclerView.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void tampilBarang2(){
        dbr.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ModelMenuMakanan2 modelMenuMakanan2 = dataSnapshot.getValue(ModelMenuMakanan2.class);
                    modelMenuMakanan2.setKey(dataSnapshot.getKey());
                    menuMakananArrayList3.add(modelMenuMakanan2);
                }

                ListAdapterMenuMakananMaster3 adapter3 = new ListAdapterMenuMakananMaster3(menuMakananArrayList3,Master.this);
                recyclerView.setAdapter(adapter3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}