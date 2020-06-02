package yaqub.tesapp001.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.Adapter.AdapterPostKehadiran;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.ResponseKehadiran;
import yaqub.tesapp001.model.Responseku;

public class KehadiranActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public AdapterPostKehadiran adapterPostKehadiran;
    public static KehadiranActivity kehadiranActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        kehadiranActivity = this;

        recyclerView = findViewById(R.id.rvKehadiran); //

        showLangsung();

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);

        if (!MainActivity.mainActivity.session.isUserLoggedIn()) {
            MainActivity.mainActivity.menuItemGenerateKehadiran = menu.findItem(R.id.itmGenerateKehadiran);
            MainActivity.mainActivity.menuItemGenerateKehadiran.setVisible(false);
            MainActivity.mainActivity.menuItemDelGen = menu.findItem(R.id.itmDelGen);
            MainActivity.mainActivity.menuItemDelGen.setVisible(false);
            MainActivity.mainActivity.menuItemAllKehadiran = menu.findItem(R.id.itmShowAllKehadiran);
            MainActivity.mainActivity.menuItemAllKehadiran.setVisible(false);

        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        switch (menuItem) {
            case R.id.itmKehadiran:
//                MainActivity.mainActivity.loading= ProgressDialog.show(this,null,"Harap Tunggu",true,false);
                Toast.makeText(this, "Kehadiran", Toast.LENGTH_SHORT).show();
                APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");

//                Intent intentKehadiran = new Intent(KehadiranActivity.kehadiranActivity, KehadiranActivity.class);
//                startActivity(intentKehadiran);
                break;
            case R.id.itmGenerateKehadiran:
                APIRetrofitAnggota.APIRetrofit.iAPIGenerateKehadiran iapiGenerateKehadiran = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.iAPIGenerateKehadiran.class);
                Call<Responseku> igenerateKehadiran = iapiGenerateKehadiran.postGenerateKehadiran("1");
                igenerateKehadiran.enqueue(new Callback<Responseku>() {
                    @Override
                    public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        }
//                        APIRetrofit.DoGetAllPost();
                        APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");
                    }

                    @Override
                    public void onFailure(Call<Responseku> call, Throwable t) {
                        Toast.makeText(KehadiranActivity.kehadiranActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(this, "Generate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itmDelGen:
                APIRetrofitAnggota.APIRetrofit.iAPIDelGenerateKehadiran iapiDelGenerateKehadiran = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.iAPIDelGenerateKehadiran.class);
                Call<Responseku> iDelgenerateKehadiran = iapiDelGenerateKehadiran.postDelGenerateKehadiran("1");
                iDelgenerateKehadiran.enqueue(new Callback<Responseku>() {
                    @Override
                    public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        }
                        APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");
//                        APIRetrofit.DoGetAllPost();
                    }

                    @Override
                    public void onFailure(Call<Responseku> call, Throwable t) {
                        Toast.makeText(KehadiranActivity.kehadiranActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(this, "Delete Generate Kehadiran", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itmShowAllKehadiran:
                APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("1");
                Toast.makeText(this, "All Kehadiran", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itmKas:
                Toast.makeText(this, "Kas/Infaq", Toast.LENGTH_SHORT).show();
                Intent intentKas = new Intent(KehadiranActivity.this, KasActivity.class);
                startActivity(intentKas);
                break;
            case R.id.itmNotulen:
                Toast.makeText(this, "Notulen", Toast.LENGTH_SHORT).show();
                Intent intentNote = new Intent(KehadiranActivity.this, NotulenActivity.class);
                startActivity(intentNote);
                break;
            case R.id.itmSetting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
//                Intent intentKehadiran=new Intent(MainActivity.this,KehadiranActivity.class);
//                startActivity(intentKehadiran);
                break;
            case R.id.itmLogout:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                MainActivity.mainActivity.session.logOutUser();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void showLangsung() {
        APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");
//        APIRetrofit.DoGetAllPost();
    }

    //open langsung
    public void showData(List<ResponseKehadiran> data) {
        adapterPostKehadiran = new AdapterPostKehadiran(data); //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPostKehadiran);
    }

//    public void showEdit(){
//        Intent intent=new Intent(KehadiranActivity.kehadiranActivity,AddDataActivity.class);
//        startActivity(intent);
//    }


}
