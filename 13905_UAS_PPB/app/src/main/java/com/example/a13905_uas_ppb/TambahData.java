package com.example.a13905_uas_ppb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TambahData extends AppCompatActivity {

     EditText tambahkode ,tambahnama ,tambahsatuan ,tambahstok ,tambahharga;
     Button tblsave, tblexit;
     ProgressDialog progressDialog;
     DatabaseReference dbr;
     DatabaseReference dbr2;
     ModelMenuMakanan modelMenuMakanan;
     ModelPenjualan2 modelPenjualan2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        tambahkode = findViewById(R.id.inputkode);
        tambahnama = findViewById(R.id.inputnama);
        tambahsatuan = findViewById(R.id.inputsatuan);
        tambahstok = findViewById(R.id.inputjmlstok);
        tambahharga = findViewById(R.id.inputharga);
        dbr = FirebaseDatabase.getInstance().getReference().child("Barang");
        dbr2 = FirebaseDatabase.getInstance().getReference().child("Penjualan");
        progressDialog = new ProgressDialog(TambahData.this);

        modelPenjualan2 = new ModelPenjualan2();

        tblsave = findViewById(R.id.tmblsave);
        tblexit = findViewById(R.id.tmblexit);

        tblexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TambahData.this , Master.class);
                startActivity(intent);
            }
        });

        tblsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kd = tambahkode.getText().toString();
                String nm_brg = tambahnama.getText().toString();
                String sat = tambahsatuan.getText().toString();
                String stk = tambahstok.getText().toString();
                String hrg = tambahharga.getText().toString();

                if (kd != null && nm_brg != null && sat != null && stk != null && hrg !=null ){
                    modelMenuMakanan = new ModelMenuMakanan(kd,nm_brg,sat,stk,hrg);
                    dbr.push().setValue(modelMenuMakanan);
                    modelPenjualan2.setKode(kd);
                    modelPenjualan2.setNama_barang(nm_brg);
                    modelPenjualan2.setSatuan(sat);
                    modelPenjualan2.setHarga(hrg);
                    modelPenjualan2.setJumlah(stk);
                    modelPenjualan2.setTerjual("0");
                    dbr2.push().setValue(modelPenjualan2);
                    CreateDatatoServer(kd,nm_brg,sat,stk,hrg);
                    CreateDatatoServer2(kd,nm_brg,sat,hrg,stk,"0");
                    Intent intent = new Intent(TambahData.this , Master.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Data Harus Lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void CreateDatatoServer(final String kd , final String nm_brg , final String sat, final String stk , final String hrg){
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_POST_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", kd);
                    params.put("nama_barang", nm_brg);
                    params.put("satuan", sat);
                    params.put("jumlah", stk);
                    params.put("harga", hrg);
                    return params;
                }
            };

            VolleyConnection.getInstance(TambahData.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void CreateDatatoServer2(final String kd , final String nm_brg , final String sat,final String hrg, final String stk ,final String terjual){
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_POST_PENJUALAN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", kd);
                    params.put("nama_barang", nm_brg);
                    params.put("satuan", sat);
                    params.put("harga", hrg);
                    params.put("jumlah", stk);
                    params.put("terjual", terjual);
                    return params;
                }
            };

            VolleyConnection.getInstance(TambahData.this).addToRequestQue(stringRequest);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}