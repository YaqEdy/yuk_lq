package yaqub.tesapp001.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.api.APIRetrofitCharts;
import yaqub.tesapp001.api.APIRetrofitUpload;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.ImageResizer;
import yaqub.tesapp001.model.Responseku;
import yaqub.tesapp001.model.Session;


public class MainActivity extends AppCompatActivity {
   public ProgressDialog loading;
    public static MainActivity mainActivity;
    Button buttonLogin,buttonCamera;
    public Session session;
    public MenuItem menuItemGenerateKehadiran, menuItemDelGen, menuItemAllKehadiran,
                    menuItemKas,menuItemKehadiran;
    public TextView textViewUsername;
    CircleImageView civUser;

    private int PICK_IMAGE_REQUEST=1;
    private static final int STORAGE_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        getSupportActionBar().setTitle("Liqo");
        getSupportActionBar().setSubtitle("Istiqomah");

        requestStoragePermission();

        buttonCamera=findViewById(R.id.btnCamera);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(MainActivity.mainActivity, LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"camera",Toast.LENGTH_SHORT).show();
                showGalery();
            }
        });

        session = new Session(this);
        if (session.isUserLoggedIn()) {
            buttonLogin.setVisibility(View.GONE);
            getSupportActionBar().setTitle(session.getSPnamaGroup());
            getSupportActionBar().setSubtitle(session.getSPGroupDesc());

            if (session.getSPFilePath() != null) {
                civUser=findViewById(R.id.civUser);
//                imageViewUser = findViewById(R.id.civUser);
                Picasso.with(getApplicationContext()).invalidate(session.getSPFilePath());
                Picasso.with(getApplicationContext()).invalidate("");
                Picasso.with(MainActivity.mainActivity)
                        .load(session.getSPFilePath())
//                    .placeholder(R.mipmap.ic_ikhwan1_foreground)
//                    .error(R.mipmap.ic_ikhwan1_foreground)
//                        .resize(800, 500)
//                        .fit()
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(civUser);
            }

            textViewUsername = findViewById(R.id.tvUsername);
            textViewUsername.setText(session.getSPNama());
        }else{
            Intent intentLogin=new Intent(this,LoginActivity.class);
            startActivity(intentLogin);
        }

    }

    private void showGalery(){
        Intent intentGalery=new Intent();
        intentGalery.setType("image/*");
        intentGalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGalery,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    private void requestStoragePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
//                Uri uri=data.getData();
//                String dd= String.valueOf(data.getData());
//                Bitmap iImgOri=BitmapFactory.decodeStream(getAssets().open(String.valueOf(data.getData())));
//                ByteArrayOutputStream out=new ByteArrayOutputStream();
//                iImgOri.compress(Bitmap.CompressFormat.JPEG,40,out);
//                byte[] bytes=out.toByteArray();
//                uploadFile(bytes);


                InputStream is=getContentResolver().openInputStream(data.getData());

                uploadFile(getBytes(is));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream is)throws IOException{
        ByteArrayOutputStream byteBuff=new ByteArrayOutputStream();

        int buffSize=1024;
        byte[] buff=new byte[buffSize];
        int len =0;
        while ((len=is.read(buff))!= -1){
            byteBuff.write(buff,0,len);
        }
        return byteBuff.toByteArray();
    }
    public void uploadFile(byte[] fileBytes){
        RequestBody requestFile=RequestBody.create(MediaType.parse("image/*"),fileBytes);
        MultipartBody.Part body=MultipartBody.Part.createFormData("image","image.jpg",requestFile);

        APIRetrofitUpload.iAPIUploadProfil iAPIUploadProfil=RetrofitClient.getClient().create(APIRetrofitUpload.iAPIUploadProfil.class);
        Call<Responseku> responseUpload=iAPIUploadProfil.sendUploadProfil(body,session.getSPNip(),RetrofitClient.url);
        responseUpload.enqueue(new Callback<Responseku>() {
            @Override
            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Upload Success.!",Toast.LENGTH_SHORT).show();
                    session.saveSPString(session.SP_FILE_PATH,response.body().iremarks);

                    Picasso.with(getApplicationContext()).invalidate(response.body().iremarks);
                    Picasso.with(MainActivity.mainActivity)
                            .load(response.body().iremarks)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .into(civUser);
                }
            }

            @Override
            public void onFailure(Call<Responseku> call, Throwable t) {
                Toast.makeText(MainActivity.this,"koneksi terputus",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);

        menuItemGenerateKehadiran = menu.findItem(R.id.itmGenerateKehadiran);
        menuItemGenerateKehadiran.setVisible(false);
        menuItemDelGen = menu.findItem(R.id.itmDelGen);
        menuItemDelGen.setVisible(false);
        menuItemAllKehadiran= menu.findItem(R.id.itmShowAllKehadiran);
        menuItemAllKehadiran.setVisible(false);
//        menuItemKehadiran=menu.findItem(R.id.itmKehadiran);
//        menuItemKehadiran.setVisible(false);
//        menuItemKas=menu.findItem(R.id.itmKas);
//        menuItemKas.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        switch (menuItem) {
            case R.id.itmKehadiran:
                Toast.makeText(this, "Kehadiran", Toast.LENGTH_SHORT).show();
                Intent intentKehadiran = new Intent(MainActivity.this, KehadiranActivity.class);
                startActivity(intentKehadiran);
                break;
            case R.id.itmGenerateKehadiran:
                APIRetrofitAnggota.APIRetrofit.iAPIGenerateKehadiran iapiGenerateKehadiran = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.iAPIGenerateKehadiran.class);
                Call<Responseku> igenerateKehadiran = iapiGenerateKehadiran.postGenerateKehadiran("1");
                igenerateKehadiran.enqueue(new Callback<Responseku>() {
                    @Override
                    public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.mainActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.mainActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                        }
                        APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");
//                        APIRetrofit.DoGetAllPost();
                    }

                    @Override
                    public void onFailure(Call<Responseku> call, Throwable t) {
                        Toast.makeText(MainActivity.mainActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(this, "Generate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itmKas:
                Toast.makeText(this, "Kas", Toast.LENGTH_SHORT).show();
                Intent intentKas = new Intent(MainActivity.this, KasActivity.class);
                startActivity(intentKas);
                break;
            case R.id.itmNotulen:
                Toast.makeText(this, "Notulen", Toast.LENGTH_SHORT).show();
                Intent intentNote = new Intent(MainActivity.this, NotulenActivity.class);
                startActivity(intentNote);
                break;
            case R.id.itmSetting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
//                Intent intentKehadiran=new Intent(MainActivity.this,KehadiranActivity.class);
//                startActivity(intentKehadiran);
                break;
//            case R.id.itmQRCode:
//                Toast.makeText(this, "Scanner", Toast.LENGTH_SHORT).show();
//                Intent intentQRCode=new Intent(MainActivity.this,QRCodeScannerActivity.class);
//                startActivity(intentQRCode);
//                break;
            case R.id.itmAnggota:
                Toast.makeText(this, "Anggota", Toast.LENGTH_SHORT).show();
                Intent intentAnggota=new Intent(MainActivity.this,AnggotaActivity.class);
                startActivity(intentAnggota);
                break;
            case R.id.itmGroup:
                Toast.makeText(this, "Group", Toast.LENGTH_SHORT).show();
                Intent intentGroup=new Intent(MainActivity.this,GroupActivity.class);
                startActivity(intentGroup);
                break;
            case R.id.itmLineChartKehadiran:
                Toast.makeText(this, "Line Chart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,ChartKehadiranActivity.class);
                startActivity(intent);
                break;
            case R.id.itmAnggotaGroup:
                Toast.makeText(this, "Group", Toast.LENGTH_SHORT).show();
                Intent intentAnggotaGroup=new Intent(MainActivity.this,AnggotaGroupActivity.class);
                startActivity(intentAnggotaGroup);
                break;
            case R.id.itmLogout:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                session.logOutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
