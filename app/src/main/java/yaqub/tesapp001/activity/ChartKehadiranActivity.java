package yaqub.tesapp001.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import yaqub.tesapp001.R;
import yaqub.tesapp001.api.APIRetrofitCharts;
import yaqub.tesapp001.model.ChartsGoogle;
import yaqub.tesapp001.model.General;
import yaqub.tesapp001.model.ResponseChart;

@SuppressLint("SetJavaScriptEnabled")
public class ChartKehadiranActivity extends AppCompatActivity {
    public static ChartKehadiranActivity chartKehadiranActivity;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_kehadiran);
        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        chartKehadiranActivity = this;
        APIRetrofitCharts.DoGetAllChart();

        //icon back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //action icon back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void showColumnChart(){
        webView = findViewById(R.id.wvLineKehadiran);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("file:///android_asset/",ChartsGoogle.pBarChart(),"text/html","utf-8",null);
//        webView.loadData(ChartsGoogle.pBarChart(), "text/html", "UTF-8");
//        webView.loadUrl("file:///android_asset/Gauge_Chart.html");
    }

    public void showChart(List<ResponseChart> data) {
        ChartsGoogle.iDataChart="";
        for (int i = 0; i < data.size(); i++) {
//            String[] iFirstName=data.get(i).iField1.split(" ");

            ChartsGoogle.iDataChart += "['" + data.get(i).iField1 + "'," + Float.valueOf(data.get(i).iField2) +",'"+data.get(i).iField2+ "%'],";

//            if (i == data.size() - 1) {
//                if (data.size() == 1) {
//                    General.iDataChart += "[" + data.get(i).iField1 + "," + data.get(i).iField2 + "]";
//                } else {
//                    General.iDataChart += "[" + data.get(i).iField1 + "," + Float.valueOf(data.get(i).iField2) + "]";
//                }
//            } else if (i == 0) {
//                General.iDataChart += "[" + data.get(i).iField1 + "," + data.get(i).iField2 + "],";
//            } else {
//                General.iDataChart += "[" + data.get(i).iField1 + "," + Float.valueOf(data.get(i).iField2) + "],";
//            }

        }
        showColumnChart();
    }

}
