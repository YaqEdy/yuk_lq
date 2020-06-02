package yaqub.tesapp001.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import yaqub.tesapp001.R;

public class QRCodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView iscannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);

        iscannerView=new ZXingScannerView(this);
        setContentView(iscannerView);
    }


        @Override
    protected void onResume() {
        super.onResume();
        iscannerView.setResultHandler(this);
        iscannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iscannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog ialertDialog=builder.create();
        ialertDialog.show();

        iscannerView.resumeCameraPreview(this);

        finish();
        Toast.makeText(MainActivity.mainActivity,result.getText(),Toast.LENGTH_LONG).show();

    }

}
