package yaqub.tesapp001.api;

import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yaqub.tesapp001.activity.ChartKehadiranActivity;
import yaqub.tesapp001.activity.MainActivity;
import yaqub.tesapp001.activity.NotulenActivity;
import yaqub.tesapp001.model.ResponseChart;
import yaqub.tesapp001.model.ResponseNotulen;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class APIRetrofitCharts {

    public interface IAPIviewChart {
        @FormUrlEncoded
        @POST("view_persentase_kehadiran.php")
        Call<List<ResponseChart>> GetAllChart(@Field("id_group") String id_group);
    }

    public static void DoGetAllChart() {
        IAPIviewChart iapiPostKas = RetrofitClient.getClient().create(IAPIviewChart.class);
        Call call = iapiPostKas.GetAllChart(MainActivity.mainActivity.session.getSPidGroup());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                List<ResponseChart> responseNotes = (List<ResponseChart>) response.body();
                ChartKehadiranActivity.chartKehadiranActivity.showChart(responseNotes);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(ChartKehadiranActivity.chartKehadiranActivity, "Masalah koneksi.!", Toast.LENGTH_SHORT).show();
//                ChartKehadiranActivity.chartKehadiranActivity.showChart(null);

            }
        });
    }

//    public interface iAPIInsertNotulen_ {
//        @FormUrlEncoded
//        @POST("insert_kas.php")
//        Call<Responseku> sendInsertNotulen_(@Field("pid_notulen") String pid_notulen,
//                                           @Field("id_group") String id_group,
//                                           @Field("nama_group") String nama_group,
//                                           @Field("notulen") String notulen,
//                                           @Field("last_update_by") String last_update_by
//        );
//    }

    public interface iAPIUpdateNotulen {
        @FormUrlEncoded
        @POST("update_notulen.php")
        Call<Responseku> sendUpdateNotulen(@Field("pid_notulen") String pid_notulen,
                                           @Field("id_group") String id_group,
                                           @Field("nama_group") String nama_group,
                                           @Field("notulen") String notulen,
                                           @Field("last_update_by") String last_update_by
        );
    }

    public interface iAPIDelNotulen {
        @FormUrlEncoded
        @POST("delete_notulen.php")
        Call<Responseku> postDelNotulen(@Field("pid_notulen") String pid_notulen);
    }


}
