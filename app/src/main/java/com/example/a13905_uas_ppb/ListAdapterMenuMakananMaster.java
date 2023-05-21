package com.example.a13905_uas_ppb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAdapterMenuMakananMaster extends RecyclerView.Adapter<ListAdapterMenuMakananMaster.myViewHolder> {
    ArrayList<modelPenjualan> arrayList;
    Context context;

    public ListAdapterMenuMakananMaster(ArrayList<modelPenjualan> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapterMenuMakananMaster.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.format_menu_master,parent,false);
        return new ListAdapterMenuMakananMaster.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterMenuMakananMaster.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ckode.setText(arrayList.get(position).kode);
        holder.cnamabarang.setText(arrayList.get(position).nama_barang);
        holder.csatuan.setText(arrayList.get(position).satuan);
        holder.cjumlah.setText(arrayList.get(position).jumlah);
        holder.charga.setText(arrayList.get(position).harga);
        holder.Relatiview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, "Anda Pilih \t" + arrayList.get(position).nama_menu,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(view.getContext(),DetailMakanan.class);
                intent.putExtra("kode",arrayList.get(position).kode);
                intent.putExtra("nama_barang",arrayList.get(position).nama_barang);
                intent.putExtra("satuan",arrayList.get(position).satuan);
                intent.putExtra("jumlah",arrayList.get(position).jumlah);
                intent.putExtra("harga",arrayList.get(position).harga);
                intent.putExtra("terjual",arrayList.get(position).terjual);
                //intent.putExtra("jmlpesan",)
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView ckode,cnamabarang,csatuan,cjumlah,charga;
        RelativeLayout Relatiview;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            ckode = itemView.findViewById(R.id.kodemakanan);
            cnamabarang=itemView.findViewById(R.id.namamakanan);
            csatuan=itemView.findViewById(R.id.satuanmakanan);
            cjumlah=itemView.findViewById(R.id.stokmakanan);
            charga=itemView.findViewById(R.id.hargamakanan);
            Relatiview=itemView.findViewById(R.id.relativeformatMenuMakanan);
        }
    }
}

