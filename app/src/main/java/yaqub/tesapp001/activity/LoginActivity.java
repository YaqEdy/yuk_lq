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
import yaqub.tesapp001.model.ResponseLogin;
import yaqub.tesapp001.model.Session;

public class LoginActivity extends AppCompatActivity {

    EditText editTextNip, editTextPassword;
    Button buttonLogin,buttonRegister;
    public static LoginActivity loginActivity;
    Session session;
    public String sesNIP,sesIDGroup,sesNamaGroup,sesGroupDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        session = new Session(getApplicationContext());

        editTextNip = findViewById(R.id.etNip);
        editTextPassword = findViewById(R.id.etPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister=findViewById(R.id.btnRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
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


    public void Login() {
        APIRetrofitAnggota.APIRetrofit.IAPILogin iapiLogin = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.IAPILogin.class);
        Call<ResponseLogin> iapiLoginUser = iapiLogin.sendLogin(editTextNip.getText().toString(), editTextPassword.getText().toString());
        iapiLoginUser.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    session.saveSPString(session.SP_NIP,response.body().nip);
                    session.saveSPString(session.SP_NAMA,response.body().nama_person);
                    session.saveSPString(session.SP_ID_GROUP,response.body().id_group);
                    session.saveSPString(session.SP_NAMA_GROUP,response.body().nama_group);
                    session.saveSPString(session.SP_GROUP_DESC,response.body().group_desc);
                    session.saveSPString(session.SP_FILE_PATH,response.body().file_path);
                    session.saveSPBoolean(session.SP_STATUS_LOGIN,true);


                    Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentMain);

//                    Intent intentMain=new Intent(LoginActivity.loginActivity,MainActivity.class);
//                    startActivity(intentMain);
                    Toast.makeText(MainActivity.mainActivity, response.body().nama_person, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password is incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(MainActivity.mainActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
