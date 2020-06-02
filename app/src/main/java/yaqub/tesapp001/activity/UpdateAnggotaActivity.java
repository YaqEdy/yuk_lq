package yaqub.tesapp001.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.Responseku;

public class UpdateAnggotaActivity extends AppCompatActivity {
    public static UpdateAnggotaActivity updateAnggotaActivity;
    public static String strPid,strNip,strNama,strJenkel,strNoHp,strEmail,strAlamat,strKet;
    EditText editTextNip,editTextNama,editTextJenkel,editTextNoHp,editTextEmail,editTextAlamat,editTextKet;
    public static int sParam=0;
    Button btnSave,btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_anggota);
        updateAnggotaActivity=this;

        editTextNip=findViewById(R.id.etNip);
        editTextNama=findViewById(R.id.etNama);
        editTextJenkel=findViewById(R.id.etJenkel);
        editTextNoHp=findViewById(R.id.etNoHP);
        editTextEmail=findViewById(R.id.etEmail);
        editTextAlamat=findViewById(R.id.etAlamat);
        editTextKet=findViewById(R.id.etKet);
        btnSave=findViewById(R.id.btnSaveAnggota);
        btnUpdate=findViewById(R.id.btnUpdateAnggota);

        editTextNip.setEnabled(false);

        if (sParam==1){
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
//            editTextNip.setEnabled(false);

            editTextNip.setText(strNip);
            editTextNama.setText(strNama);
            editTextJenkel.setText(strJenkel);
            editTextNoHp.setText(strNoHp);
            editTextEmail.setText(strEmail);
            editTextAlamat.setText(strAlamat);
            editTextKet.setText(strKet);
        }else{
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
//            editTextNip.setEnabled(true);

            editTextNip.setText("");
            editTextNama.setText("");
            editTextJenkel.setText("");
            editTextNoHp.setText("");
            editTextEmail.setText("");
            editTextAlamat.setText("");
            editTextKet.setText("");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAnggota();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnggota();
            }
        });

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void insertAnggota(){
        APIRetrofitAnggota.iAPIInsertAnggota iAPIInsertAnggota= RetrofitClient.getClient().create(APIRetrofitAnggota.iAPIInsertAnggota.class);
        Call<Responseku> responsekuInsert=iAPIInsertAnggota.sendInsertAnggota_(
                "",
                editTextNip.getText().toString(),
                editTextNama.getText().toString(),
                editTextJenkel.getText().toString(),
                editTextNoHp.getText().toString(),
                editTextEmail.getText().toString(),
                editTextAlamat.getText().toString(),
                editTextKet.getText().toString(),
                MainActivity.mainActivity.session.getSPNip()
        );
        responsekuInsert.enqueue(new Callback<Responseku>() {
            @Override
            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                APIRetrofitAnggota.DoGetAllAnggota();
                if(response.isSuccessful()){
                    Toast.makeText(UpdateAnggotaActivity.updateAnggotaActivity,response.body().iremarks,Toast.LENGTH_SHORT).show();
                    Intent intentAnggota=new Intent(UpdateAnggotaActivity.this,AnggotaActivity.class);
                    startActivity(intentAnggota);
                }else{
                    Toast.makeText(UpdateAnggotaActivity.updateAnggotaActivity,response.body().iremarks,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responseku> call, Throwable t) {
                Toast.makeText(UpdateAnggotaActivity.updateAnggotaActivity,"Gagal mengirim request.!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateAnggota(){
        APIRetrofitAnggota.iAPIUpdateAnggota iAPIUpdateAnggota=RetrofitClient.getClient().create(APIRetrofitAnggota.iAPIUpdateAnggota.class);
        Call<Responseku> responsekuUpdate=iAPIUpdateAnggota.sendUpdateAnggota(
                strPid,
                editTextNip.getText().toString(),
                editTextNama.getText().toString(),
                editTextJenkel.getText().toString(),
                editTextNoHp.getText().toString(),
                editTextEmail.getText().toString(),
                editTextAlamat.getText().toString(),
                editTextKet.getText().toString(),
                MainActivity.mainActivity.session.getSPNip()
        );
        responsekuUpdate.enqueue(new Callback<Responseku>() {
            @Override
            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                APIRetrofitAnggota.DoGetAllAnggota();
                if (response.isSuccessful()){
                    Toast.makeText(UpdateAnggotaActivity.this,response.body().iremarks,Toast.LENGTH_SHORT).show();
                    Intent intentUpdate=new Intent(UpdateAnggotaActivity.this,AnggotaActivity.class);
                    startActivity(intentUpdate);
                }else{
                    Toast.makeText(UpdateAnggotaActivity.this,response.body().iremarks,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responseku> call, Throwable t) {
                Toast.makeText(UpdateAnggotaActivity.this,"Gagal mengirim request.!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
