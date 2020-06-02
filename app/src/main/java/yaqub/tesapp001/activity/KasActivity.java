package yaqub.tesapp001.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.Adapter.AdapterPostKas;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitKas;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.Responseku;

public class KasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String pid_kas_update="";
    public EditText editTextJml, editTextKet;
    Button buttonSave,buttonClear;
   public Spinner spinnerStatus;
    String[] arrStatus = {"Kredit","Debit"};
    String iStatus;
    public static KasActivity kasActivity;
    public AdapterPostKas adapterPostKas;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kas);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        kasActivity = this;

        recyclerView = findViewById(R.id.rvKas); //

        editTextJml = findViewById(R.id.etJml);
        editTextKet = findViewById(R.id.etKet);
        spinnerStatus = findViewById(R.id.spnStatus);
        buttonSave = findViewById(R.id.btnSave);
        buttonClear = findViewById(R.id.btnClear);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveKas();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearKas();
            }
        });
        spinnerStatus.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrStatus);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerStatus.setAdapter(arrayAdapter);

//        show langsung
        APIRetrofitKas.DoGetAllKas();

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, arrStatus[i], Toast.LENGTH_SHORT).show();
        if (arrStatus[i] == "Debit") {
            iStatus = "1";
        } else if (arrStatus[i] == "Kredit") {
            iStatus = "0";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveKas() {
        String strJumlah = editTextJml.getText().toString();
        if (iStatus == "0") {
            strJumlah = "-" + editTextJml.getText().toString();
        }
        if (strJumlah.equals("-") || strJumlah.equals("")) {
            Toast.makeText(KasActivity.this, "Jumlah Kas/Infaq Tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {

            APIRetrofitKas.iAPIInsertKas iAPIInsertKas = RetrofitClient.getClient().create(APIRetrofitKas.iAPIInsertKas.class);
            Call<Responseku> iapiResponseKas = iAPIInsertKas.sendInsertKas(pid_kas_update,MainActivity.mainActivity.session.getSPidGroup(),
                    iStatus, strJumlah, editTextKet.getText().toString(),
                    MainActivity.mainActivity.session.getSPNip());
            iapiResponseKas.enqueue(new Callback<Responseku>() {
                @Override
                public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(KasActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        clearKas();
                    } else {
                        Toast.makeText(KasActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                    }
                    APIRetrofitKas.DoGetAllKas();
                }

                @Override
                public void onFailure(Call<Responseku> call, Throwable t) {
                    Toast.makeText(KasActivity.this, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void clearKas(){
        editTextJml.setText("");
        editTextKet.setText("");
        pid_kas_update="";
    }

    public void showKas(List<ResponseKas> data) {
        adapterPostKas = new AdapterPostKas(data); //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPostKas);
    }

}
