package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;


import com.example.quanlysach.Adapter.HoadonchitietAdapter;
import com.example.quanlysach.DAO.HoadonchitietDAO;
import com.example.quanlysach.Dulieu.Hoadon;
import com.example.quanlysach.Dulieu.Sach;


import java.util.ArrayList;
import java.util.List;

public class Hoadonchitiet extends AppCompatActivity {
    public List<com.example.quanlysach.Dulieu.Hoadonchitiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    HoadonchitietAdapter adapter = null;
    HoadonchitietDAO hoaDonChiTietDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("HOÁ ĐƠN CHI TIẾT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_hoadonchitiet);
        lvCart = (ListView) findViewById(R.id.lvHoaDonChiTiet);
        hoaDonChiTietDAO = new HoadonchitietDAO(Hoadonchitiet.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
        }
        adapter = new HoadonchitietAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}