package yaqub.tesapp001.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitNotulen;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.Responseku;

public class SubNotulenActivity extends AppCompatActivity {
    public String pid_notulen;
    public static String strNote,strPidEdit;
    public EditText editTextNote;
    public static SubNotulenActivity subNotulenActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_notulen);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        subNotulenActivity = this;

        editTextNote = findViewById(R.id.etNote);
        if(NotulenActivity.notulenActivity.sParam==0){
            editTextNote.setText(strNote);
            pid_notulen=strPidEdit;
        }
        TextWatcher textWatcherNote = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (longTextNote()) {
                    saveNote();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            private boolean longTextNote() {
                return editTextNote.getText().toString().trim().length() > 0;
            }
        };
        editTextNote.addTextChangedListener(textWatcherNote);

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        APIRetrofitNotulen.DoGetAllNotulen();
        finish();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        APIRetrofitNotulen.DoGetAllNotulen();
        super.onBackPressed();
    }

    public void saveNote() {
        if(NotulenActivity.notulenActivity.sParam==0){
            pid_notulen=strPidEdit;
        }else {
            pid_notulen=NotulenActivity.notulenActivity.pidNotulen;
        }
        APIRetrofitNotulen.iAPIUpdateNotulen iAPIUpdateNotulen = RetrofitClient.getClient().create(APIRetrofitNotulen.iAPIUpdateNotulen.class);
        Call<Responseku> iApiresponsekuNote = iAPIUpdateNotulen.sendUpdateNotulen(pid_notulen,
                MainActivity.mainActivity.session.getSPidGroup(),
                MainActivity.mainActivity.session.getSPnamaGroup(),
                editTextNote.getText().toString(),
                MainActivity.mainActivity.session.getSPNip());
        iApiresponsekuNote.enqueue(new Callback<Responseku>() {
            @Override
            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
//                if(response.isSuccessful()){
//                    Toast.makeText(SubNotulenActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(SubNotulenActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<Responseku> call, Throwable t) {
//                Toast.makeText(SubNotulenActivity.this, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
