package com.example.a13905_uas_ppb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailMakanan extends AppCompatActivity {
    EditText editnama, editsatuan, editstok, editharga;
    TextView editkode;
    Button tblsave, tblexit, tbldelete;
    ProgressDialog progressDialog;
    DatabaseReference dbr;
    ModelMenuMakanan2 modelMenuMakanan2;
    ModelPenjualan2 modelPenjualan2;

    String xkey = "key";
  //  String xkey2 = "keybarang";
    String xkd = "kode";
    String xnm = "nama_barang";
    String xsat = "satuan";
    String xstok = "jumlah";
    String xhrg = "harga";
    String xtrjl = "terjual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);

        editkode = findViewById(R.id.inputkode);
        editnama = findViewById(R.id.inputnama);
        editsatuan = findViewById(R.id.inputsatuan);
        editstok = findViewById(R.id.inputjmlstok);
        editharga = findViewById(R.id.inputharga);
        tblsave = findViewById(R.id.tmblsave);
        tblexit = findViewById(R.id.tmblexit);
        tbldelete = findViewById(R.id.tmbldelete);
        modelMenuMakanan2 = new ModelMenuMakanan2();
        modelPenjualan2 = new ModelPenjualan2();
        dbr = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();

        xkd = bundle.getString(xkd);
        xnm = bundle.getString(xnm);
        xsat = bundle.getString(xsat);
        xstok = bundle.getString(xstok);
        xhrg = bundle.getString(xhrg);
        xkey = bundle.getString(xkey);
        xtrjl = bundle.getString(xtrjl);
       // xkey2 = bundle.getString(xkey2);

        editkode.setText(xkd);
        editnama.setText(xnm);
        editsatuan.setText(xsat);
        editstok.setText(xstok);
        editharga.setText(xhrg);

        progressDialog = new ProgressDialog(DetailMakanan.this);

        tblexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailMakanan.this , Master.class);
                startActivity(intent);
            }
        });

        tblsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyy = xkey;
         //       String keyBarang = xkey2;
                String kd = xkd;
                String nm_brg = editnama.getText().toString();
                String sat = editsatuan.getText().toString();
                String stk = editstok.getText().toString();
                String hrg = editharga.getText().toString();
                String trjl = xtrjl;


                modelMenuMakanan2.setKey(keyy);
                modelMenuMakanan2.setKode(kd);
                modelMenuMakanan2.setNama_barang(nm_brg);
                modelMenuMakanan2.setSatuan(sat);
                modelMenuMakanan2.setJumlah(stk);
                modelMenuMakanan2.setHarga(hrg);

                modelPenjualan2.setKey(keyy);
                modelPenjualan2.setKode(kd);
                modelPenjualan2.setNama_barang(nm_brg);
                modelPenjualan2.setSatuan(sat);
                modelPenjualan2.setHarga(hrg);
                modelPenjualan2.setJumlah(stk);
                modelPenjualan2.setTerjual(trjl);


                dbr.child("Penjualan").child(keyy).setValue(modelPenjualan2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DetailMakanan.this, "Update Data Sukses", Toast.LENGTH_SHORT).show();
                        dbr.child("Barang").child(keyy).setValue(modelMenuMakanan2);
                        updateDataToServer(kd,nm_brg,sat,stk,hrg);
                        updateDataToServer2(kd,nm_brg,sat,hrg,stk,trjl);
                        Intent intent = new Intent(DetailMakanan.this , Master.class);
                        startActivity(intent);
                    }
                });

            }
        });

        tbldelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kd = xkd;

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailMakanan.this);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbr = FirebaseDatabase.getInstance().getReference();
                        dbr.child("Penjualan").child(xkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dbr.child("Barang").child(xkey).removeValue();
                                Toast.makeText(DetailMakanan.this, "Hapus Berhasil !" , Toast.LENGTH_SHORT).show();
                                deleteDataToServer(kd);
                                deleteDataToServer2(kd);
                                Intent intent = new Intent(DetailMakanan.this , Master.class);
                                startActivity(intent);
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage("Apakah Yakin Data " + kd + " akan dihapus ?");
                builder.show();

//                if (kd != null ){
//                    deleteDataToServer(kd);
//                    Intent intent = new Intent(DetailMakanan.this , Master.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(),"Data Harus Lengkap", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    public void updateDataToServer(String xkd, String xnm, String xsat,String xstok, String xhrg) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_PUT_BARANG_URL,
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
                    return params;
                }
            };

            VolleySingleton.getInstance(DetailMakanan.this).addToRequestQue(stringRequest);

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

    public void updateDataToServer2(String xkd, String xnm, String xsat, String xhrg, final String xstok, final String terjual2) {
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

            VolleySingleton.getInstance(DetailMakanan.this).addToRequestQue(stringRequest);

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


    public void deleteDataToServer(final String kode) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_DELETE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("OK")) {
                                    Toast.makeText(getApplicationContext(), "Berhasil delete Data", Toast.LENGTH_SHORT).show();
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
                    params.put("kode", kode);
                    return params;
                }
            };

            VolleySingleton.getInstance(DetailMakanan.this).addToRequestQue(stringRequest);

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

    public void deleteDataToServer2(final String kode) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_DELETE_PENJUALAN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("OK")) {
                                    Toast.makeText(getApplicationContext(), "Berhasil delete Data", Toast.LENGTH_SHORT).show();
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
                    params.put("kode", kode);
                    return params;
                }
            };

            VolleySingleton.getInstance(DetailMakanan.this).addToRequestQue(stringRequest);

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


    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}