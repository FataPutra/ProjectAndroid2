package com.example.a13905_uas_ppb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
import java.util.HashMap;
import java.util.Map;

public class PesanMakanan extends AppCompatActivity {

    ArrayList<modelPenjualan> penjualanArrayList = new ArrayList<>();
    String DATA_JSON_STRING, data_json_string;


    TextView nmbrg,satbrg,hrgbrg,totalharga;
    EditText jmlhpesan;
    Button btnpsn;
    ProgressDialog progressDialog;
    int countData = 0;
    ModelPenjualan2 modelPenjualan2;
    DatabaseReference dbr;

    String xkey = "key";
    String xkd = "kode";
    String xnm = "nama_barang";
    String xsat = "satuan";
    String xstok = "jumlah";
    String xhrg = "harga";
    String xtrjl = "terjual";

    int jumlah_pesan;
    int hrg_brg;
    int hrgpsn=0;
    int terjuall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_makanan);

        nmbrg = findViewById(R.id.textnamaPesan);
        satbrg = findViewById(R.id.textSatuanMakanan);
        hrgbrg = findViewById(R.id.texthargaPesan);
        totalharga = findViewById(R.id.totalhargapesan);
        jmlhpesan = findViewById(R.id.inputjumlahpesan);
        btnpsn = findViewById(R.id.tmblpesan);
        progressDialog = new ProgressDialog(PesanMakanan.this);
        dbr = FirebaseDatabase.getInstance().getReference();
        modelPenjualan2 = new ModelPenjualan2();

        Bundle bundle = getIntent().getExtras();

        xkey = bundle.getString(xkey);
        xkd = bundle.getString(xkd);
        xnm = bundle.getString(xnm);
        xsat = bundle.getString(xsat);
        xstok = bundle.getString(xstok);
        xhrg = bundle.getString(xhrg);
        xtrjl = bundle.getString(xtrjl);

        nmbrg.setText(xnm);
        satbrg.setText(xsat);
        hrgbrg.setText("Rp " + xhrg + ",00");

        jmlhpesan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (jmlhpesan.getText().toString().isEmpty()){
                    jumlah_pesan =0;
                }else{
                    jumlah_pesan = Integer.parseInt(jmlhpesan.getText().toString());
                }
                hrg_brg = Integer.parseInt(xhrg);
                hrgpsn = Integer.parseInt(xhrg) * jumlah_pesan;
                totalharga.setText("Rp " + Integer.toString(hrgpsn) + ",00");
            }
        });

        btnpsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(PesanMakanan.this, menuMakanan.class);

                //READ DATA
 //               getJSON();
                // readDataFromServer2();
               // System.out.print(penjualanArrayList.get(0));

   //             boolean ditemukan = false;
  //              int index = -1;
  //              for(int i=0; i<penjualanArrayList.size(); i++){
  //                  if (penjualanArrayList.get(i).getKode() == xkd){
 //                       index = i;
 //                       ditemukan = true;
 //                   }
//                }

                if (jmlhpesan.getText().toString().isEmpty()){
                   jumlah_pesan =0;
                }else{
                    jumlah_pesan = Integer.parseInt(jmlhpesan.getText().toString());
                    //terjuall = Integer.parseInt(terjual) + jumlah_pesan;
                   // terjual2 = String.valueOf(terjuall);
                }
                xstok = String.valueOf(Integer.parseInt(xstok) - jumlah_pesan);
                xtrjl = String.valueOf(Integer.parseInt(xtrjl) + jumlah_pesan);
//                if (ditemukan = true) {

                    modelPenjualan2.setKey(xkey);
                    modelPenjualan2.setKode(xkd);
                    modelPenjualan2.setNama_barang(xnm);
                    modelPenjualan2.setSatuan(xsat);
                    modelPenjualan2.setHarga(xhrg);
                    modelPenjualan2.setJumlah(xstok);
                    modelPenjualan2.setTerjual(xtrjl);

                    dbr.child("Penjualan").child(xkey).setValue(modelPenjualan2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PesanMakanan.this, "Pembelian Sukses", Toast.LENGTH_SHORT).show();
                        //updateDataToServer(xkd, xnm, xsat, xhrg, xstok, xtrjl);
                        Intent intent = new Intent(PesanMakanan.this , menuMakanan.class);
                        startActivity(intent);
                    }
                });

                   // dbr.child("Penjualan").child(xkey).setValue(modelPenjualan2);
                   // updateDataToServer(xkd, xnm, xsat, xhrg, xstok, xtrjl);
      //          } else {
     //           }
               // startActivity(intent);
            }
        });
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
                                    updateDataToServer(kd, nm_brg, sat, hrg, stk, terjual);
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

            VolleyConnection.getInstance(PesanMakanan.this).addToRequestQue(stringRequest);
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


    public void updateDataToServer(String xkd, String xnm, String xsat, String xhrg, final String xstok, final String terjual2) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_PUT_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("OK")) {
                                    Toast.makeText(getApplicationContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", xkd);
                    params.put("nama_barang", xnm);
                    params.put("satuan", xsat);
                    params.put("harga", xhrg);
                    params.put("jumlah", xstok);
                    params.put("terjual", terjual2);
                    return params;
                }
            };

            VolleySingleton.getInstance(PesanMakanan.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void readDataFromServer2(){
        if(checkNetworkConnection()){
            penjualanArrayList.clear();
            try {
                JSONObject object = new JSONObject(data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String kodex ;
                String nama_barangx;
                String satuanx;
                String hargax;
                String jumlahx;
                String terjualx;

                while (countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    kodex = jsonObject.getString("kode");
                    nama_barangx = jsonObject.getString("nama_barang");
                    satuanx = jsonObject.getString("satuan");
                    hargax = jsonObject.getString("harga");
                    jumlahx = jsonObject.getString("jumlah");
                    terjualx = jsonObject.getString("terjual");
                    penjualanArrayList.add(new modelPenjualan(kodex, nama_barangx, satuanx, hargax, jumlahx, terjualx));
                    countData++;
                }
                countData = 0;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getJSON(){
        new PesanMakanan.BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void , Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DbContract.SERVER_GET_PENJUALAN_URL;
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


    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}