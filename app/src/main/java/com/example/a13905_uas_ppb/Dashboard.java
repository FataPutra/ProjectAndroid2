package com.example.a13905_uas_ppb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
        ArrayList<modelMenu> menuArrayList = new ArrayList<modelMenu>();
        RecyclerView recyclerView;
        Button buttonKeluar;

        int gmbr_menuDashBoard[]={R.drawable.master, R.drawable.transaksi, R.drawable.laporan , R.drawable.aboutme};
        String nm_menuDashBoard[]={"Master" , "Transaction" , "Report" , "About Me"};

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);

            recyclerView = findViewById(R.id.recyclerviewMenu);
            buttonKeluar = findViewById(R.id.buttonKeluar);
            bacadata_menu();
            tampil_gridViewDashboard();
            buttonKeluar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (view.getContext(),Login.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void bacadata_menu() {
            for (int i = 0; i < gmbr_menuDashBoard.length; i++) {
                menuArrayList.add(new modelMenu(gmbr_menuDashBoard[i],
                        nm_menuDashBoard[i]));
            }
        }

        private void tampil_gridViewDashboard() {
            GridAdapter gridAdapter =new GridAdapter(menuArrayList,this);
            recyclerView.setAdapter(gridAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(Dashboard.this,2));
        }

    }