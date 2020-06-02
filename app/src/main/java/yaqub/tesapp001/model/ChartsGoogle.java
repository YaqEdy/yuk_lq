package yaqub.tesapp001.model;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import yaqub.tesapp001.activity.ChartKehadiranActivity;

/**
 * Created by Yaqub on 2/23/2018.
 */

public class ChartsGoogle {
    public static String iDataChart = "";

    public static String pBarChart() {
        String pHtml = "";

        pHtml += "<script type='text/javascript' src='Js/Google_Chart_Loader.js'></script>";
        pHtml += "<div id='chart_div' style='width:100%;height:100%'></div>";

        pHtml += "<script>";
        pHtml += "google.charts.load('current', {packages: ['corechart', 'bar']});";
        pHtml += "google.charts.setOnLoadCallback(drawBasic);";

        pHtml += "function drawBasic() {";

        pHtml += "var data = google.visualization.arrayToDataTable([";
        pHtml += "['Nama Anggota', 'Percentage', { role: 'annotation' } ],";
        pHtml += iDataChart;
        pHtml += "]);";

        pHtml += "var options = {";
        pHtml += "title: 'Grafik Persentase Kehadiran',";
        pHtml += "legend: {position:'bottom',maxLines:3},";
//        pHtml += "width: 100%,";
        pHtml += "height: 550,";
        pHtml += "animation: {duration:1000,easing:'linear',startup:true},";
        pHtml += "chartArea: {width: '60%'},";
        pHtml += "hAxis: {";
        pHtml += "title: 'Persentase (0%-100%)',";
        pHtml += "minValue: 0,";
//        pHtml += "format: '0.00%'";
        pHtml += "},";
        pHtml += "vAxis: {";
        pHtml += "title: 'Nama Anggota'";
        pHtml += "}";
        pHtml += "};";

        pHtml += "      var chart = new google.visualization.BarChart(document.getElementById('chart_div'));";

        pHtml += "      chart.draw(data, options);";
        pHtml += "}";
        pHtml += "</script>";

        return pHtml;
    }

}
