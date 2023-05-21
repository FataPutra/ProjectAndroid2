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

import java.util.ArrayList;

public class ListAdapterMenuMakanan2 extends RecyclerView.Adapter<ListAdapterMenuMakanan2.myViewHolder> {

    ArrayList<ModelPenjualan2> arrayList;
    Context context;

    public ListAdapterMenuMakanan2(ArrayList<ModelPenjualan2> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapterMenuMakanan2.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.format_menu_makanan,parent,false);
        return new ListAdapterMenuMakanan2.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterMenuMakanan2.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cnamabarang.setText(arrayList.get(position).nama_barang);
        holder.csatuan.setText(arrayList.get(position).satuan);
        holder.cjumlah.setText(arrayList.get(position).jumlah);
        holder.Relatiview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, "Anda Pilih \t" + arrayList.get(position).nama_menu,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(view.getContext(),PesanMakanan.class);
                intent.putExtra("key", arrayList.get(position).key);
                intent.putExtra("kode",arrayList.get(position).kode);
                intent.putExtra("nama_barang",arrayList.get(position).nama_barang);
                intent.putExtra("satuan",arrayList.get(position).satuan);
                intent.putExtra("harga",arrayList.get(position).harga);
                intent.putExtra("jumlah",arrayList.get(position).jumlah);
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
        TextView cnamabarang,csatuan,cjumlah;
        RelativeLayout Relatiview;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            cnamabarang=itemView.findViewById(R.id.namamakanan);
            csatuan=itemView.findViewById(R.id.satuanmakanan);
            cjumlah=itemView.findViewById(R.id.stokmakanan);
            Relatiview=itemView.findViewById(R.id.relativeformatMenuMakanan);
        }
    }
}
