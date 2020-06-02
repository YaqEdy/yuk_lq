package yaqub.tesapp001.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.Adapter.AdapterPostGroup;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitGroup;
import yaqub.tesapp001.api.APIRetrofitKas;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.ResponseGroup;
import yaqub.tesapp001.model.Responseku;

public class GroupActivity extends AppCompatActivity {
    public EditText editTextIdGroup, editTextNamaGroup,editTextAlias;
    Button buttonSave,buttonClear;
    public AdapterPostGroup adapterPostGroup;
    public RecyclerView recyclerView;
    public static GroupActivity groupActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        groupActivity=this;

        recyclerView = findViewById(R.id.rvGroup); //

        editTextIdGroup = findViewById(R.id.etIDGroup);
        editTextNamaGroup = findViewById(R.id.etNamaGroup);
        editTextAlias = findViewById(R.id.etAlias);
        buttonSave = findViewById(R.id.btnSave);
        buttonClear = findViewById(R.id.btnClear);

        editTextIdGroup.setEnabled(false);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroup();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearForm();
            }
        });

        //show langsung data
        APIRetrofitGroup.DoGetAllGroup();

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void saveGroup() {
        if (editTextNamaGroup.getText().toString().equals("") || editTextAlias.getText().toString().equals("")) {
            Toast.makeText(this, "Nama Group dan Alias Tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {

            APIRetrofitGroup.iAPIInsertGroup iAPIInsertGroup = RetrofitClient.getClient().create(APIRetrofitGroup.iAPIInsertGroup.class);
            Call<Responseku> iapiResponseGroup = iAPIInsertGroup.sendInsertGroup(editTextIdGroup.getText().toString(),editTextAlias.getText().toString(),
                    editTextNamaGroup.getText().toString());
            iapiResponseGroup.enqueue(new Callback<Responseku>() {
                @Override
                public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(GroupActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        clearForm();
                    } else {
                        Toast.makeText(GroupActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                    }
                    APIRetrofitGroup.DoGetAllGroup();

                }

                @Override
                public void onFailure(Call<Responseku> call, Throwable t) {
                    Toast.makeText(GroupActivity.this, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void clearForm(){
        editTextNamaGroup.setText("");
        editTextAlias.setText("");
        editTextIdGroup.setText("");
    }

    public void showGroup(List<ResponseGroup> data) {
        adapterPostGroup = new AdapterPostGroup(data); //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPostGroup);
    }



}
