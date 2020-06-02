package yaqub.tesapp001.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import yaqub.tesapp001.Adapter.AdapterPostAnggota;
import yaqub.tesapp001.Adapter.AdapterPostKehadiran;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.model.ResponseAnggota;
import yaqub.tesapp001.model.ResponseKehadiran;

public class AnggotaActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public AdapterPostAnggota adapterPostAnggota;
    public static AnggotaActivity anggotaActivity;
    FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        anggotaActivity=this;
        recyclerView = findViewById(R.id.rvAnggota); //
        fabAdd=findViewById(R.id.fabAddAnggota);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnggota();
                UpdateAnggotaActivity.sParam=0;
            }
        });
        APIRetrofitAnggota.DoGetAllAnggota();

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void showAnggota(List<ResponseAnggota> data) {
        adapterPostAnggota = new AdapterPostAnggota(data); //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPostAnggota);
    }

    public void updateAnggota(){
        Intent intentUpdate=new Intent(this,UpdateAnggotaActivity.class);
        startActivity(intentUpdate);
    }

}
