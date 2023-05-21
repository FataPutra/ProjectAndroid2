package com.example.a13905_uas_ppb;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    ArrayList<modelMenu> menuArrayList = new ArrayList<modelMenu>();
    RecyclerView recyclerView;

    int gmbr_menuDashBoard[]={R.drawable.master, R.drawable.transaksi, R.drawable.laporan , R.drawable.aboutme};
    String nm_menuDashBoard[]={"Master" , "Transaction" , "Report" , "About Me"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        recyclerView = findViewById(R.id.recyclerviewMenu);
        bacadata_menu();
        tampil_gridViewDashboard();
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
        recyclerView.setLayoutManager(new GridLayoutManager(Menu.this,2));
    }

}
