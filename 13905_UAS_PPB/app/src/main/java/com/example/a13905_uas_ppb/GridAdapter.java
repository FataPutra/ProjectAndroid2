package com.example.a13905_uas_ppb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.myViewHolder> {

    ArrayList<modelMenu> arrayList;
    Context context;

    public GridAdapter(ArrayList<modelMenu> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public GridAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.format_menu,parent,false);
        return new GridAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cgambarbarang.setImageResource(arrayList.get(position).gambar_menuDashboard);
        holder.cnamabarang.setText(arrayList.get(position).nama_menuDashboard);
        holder.Relatiview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0) {
                    // Toast.makeText(context, "Anda Pilih \t" + arrayList.get(position).nama_menu,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (view.getContext(),Master.class);
                    view.getContext().startActivity(intent);
                }else if (position ==1) {
                    Intent intent = new Intent (view.getContext(),menuMakanan.class);
                    view.getContext().startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://192.168.1.6:8080/uas_ppb/CRUD/tampilpenjualan.php"));
                    view.getContext().startActivity(intent);
                }else {
                    Intent intent = new Intent (view.getContext(),AboutMe.class);
                    view.getContext().startActivity(intent);
                }
                //intent.putExtra("gambar",arrayList.get(position).gambar_menuDashboard);
              //  intent.putExtra("nama",arrayList.get(position).nama_menuDashboard);
               // view.getContext().startActivity(intent);
            }
            });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView cnamabarang;
        ImageView cgambarbarang;

        RelativeLayout Relatiview;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            cgambarbarang=itemView.findViewById(R.id.gambarMenu);
            cnamabarang=itemView.findViewById(R.id.namaMenu);
            Relatiview=itemView.findViewById(R.id.relativeformatMenu);
        }
    }
}

