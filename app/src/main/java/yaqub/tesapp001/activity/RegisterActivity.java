package yaqub.tesapp001.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class RegisterActivity extends AppCompatActivity {
    EditText editTextNip, editTextAccount, editTextPassword;
    Button buttonRegister;
    public RegisterActivity registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerActivity = this;

        editTextNip = findViewById(R.id.etNip);
        editTextAccount = findViewById(R.id.etNamaAccount);
        editTextPassword = findViewById(R.id.etPassword);
        buttonRegister = findViewById(R.id.btnRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
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

    public void Register() {
        APIRetrofitAnggota.APIRetrofit.IAPIRegister iapiRegister = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.IAPIRegister.class);
        Call<Responseku> iapiRegisterUser = iapiRegister.sendRegister(editTextNip.getText().toString(), editTextAccount.getText().toString(), editTextPassword.getText().toString(), "0");
        iapiRegisterUser.enqueue(new Callback<Responseku>() {
            @Override
            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                if (response.isSuccessful()) {
                    if (response.body().istatus == 1) {
                        Toast.makeText(LoginActivity.loginActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                    } else if (response.body().istatus == 0) {
                        KonfirmasiRegister(response.body().iremarks);
                    } else {
                        Toast.makeText(LoginActivity.loginActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Nip/Account is incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Responseku> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void KonfirmasiRegister(final String sPid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Pesan Konfirmasi.!");
        builder.setMessage("User Account sudah terdaftar, Replace Account.?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                APIRetrofitAnggota.APIRetrofit.IAPIRegister iapiRegister = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.IAPIRegister.class);
                Call<Responseku> iapiRegisterUser = iapiRegister.sendRegister(editTextNip.getText().toString(), editTextAccount.getText().toString(), editTextPassword.getText().toString(), sPid);
                iapiRegisterUser.enqueue(new Callback<Responseku>() {
                    @Override
                    public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                        if (response.isSuccessful()) {
                            Intent intentLogin=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intentLogin);
                            Toast.makeText(RegisterActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Responseku> call, Throwable t) {
                        Toast.makeText(KehadiranActivity.kehadiranActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(RegisterActivity.this, "Account Not Replace.", Toast.LENGTH_SHORT).show();
//                                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

}
