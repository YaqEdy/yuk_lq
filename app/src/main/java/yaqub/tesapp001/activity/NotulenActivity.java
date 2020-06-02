package yaqub.tesapp001.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.Adapter.AdapterPostNotulen;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitNotulen;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.ResponseNotulen;
import yaqub.tesapp001.model.Responseku;

public class NotulenActivity extends AppCompatActivity {
    public static NotulenActivity notulenActivity;
    public AdapterPostNotulen adapterPostNotulen;
    public RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    public String pidNotulen;
    public static int sParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notulen);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        notulenActivity = this;

        recyclerView = findViewById(R.id.rvNotes);
        fabAdd=findViewById(R.id.fabAddNote);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIRetrofitNotulen.iAPIUpdateNotulen iAPIInsertNotulen= RetrofitClient.getClient().create(APIRetrofitNotulen.iAPIUpdateNotulen.class);
                Call<Responseku> iApiresponsekuNote=iAPIInsertNotulen.sendUpdateNotulen("",
                        MainActivity.mainActivity.session.getSPidGroup(),
                        MainActivity.mainActivity.session.getSPnamaGroup(),
                        "",
                        MainActivity.mainActivity.session.getSPNip());
                iApiresponsekuNote.enqueue(new Callback<Responseku>() {
                    @Override
                    public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                        if(response.isSuccessful()){
                            pidNotulen=response.body().iremarks;
                            Toast.makeText(NotulenActivity.notulenActivity,"Add Notulen",Toast.LENGTH_SHORT).show();
                            intentSubNote();
                            sParam=1;
                        }else{
                            Toast.makeText(NotulenActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Responseku> call, Throwable t) {
                        Toast.makeText(NotulenActivity.this, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        APIRetrofitNotulen.DoGetAllNotulen();

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void intentSubNote(){
        Intent intentSubNote=new Intent(NotulenActivity.notulenActivity,SubNotulenActivity.class);
        startActivity(intentSubNote);
    }

    public void showNotulen(List<ResponseNotulen> data) {
        adapterPostNotulen = new AdapterPostNotulen(data); //
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapterPostNotulen);
    }


}
