package yaqub.tesapp001.api;

import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yaqub.tesapp001.activity.KasActivity;
import yaqub.tesapp001.activity.KehadiranActivity;
import yaqub.tesapp001.activity.MainActivity;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.ResponseKehadiran;
import yaqub.tesapp001.model.ResponseLogin;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class APIRetrofitKas {

    public interface IAPIviewKas {
        @POST("view_kas.php")
        Call<List<ResponseKas>> GetAllKas();
    }

    public static void DoGetAllKas() {
        IAPIviewKas iapiPostKas = RetrofitClient.getClient().create(IAPIviewKas.class);
        Call call = iapiPostKas.GetAllKas();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                List<ResponseKas> responseKas = (List<ResponseKas>) response.body();
                KasActivity.kasActivity.showKas(responseKas);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(KasActivity.kasActivity, "Masalah koneksi.!", Toast.LENGTH_SHORT).show();
//                KasActivity.kasActivity.showKas(null);

            }
        });
    }

    public interface iAPIInsertKas {
        @FormUrlEncoded
        @POST("insert_kas.php")
        Call<Responseku> sendInsertKas(@Field("pid_kas") String pid_kas,
                                       @Field("id_group") String id_group,
                                        @Field("status") String status,
                                        @Field("jumlah") String jumlah,
                                        @Field("ket") String ket,
                                        @Field("last_update_by") String last_update_by
        );
    }

    public interface iAPIUpdateKehadiran {
        @FormUrlEncoded
        @POST("update_kehadiran.php")
        Call<Responseku> sendDataUpdate(@Field("pid_kehadiran") String pid_kehadiran,
                                        @Field("absensi") String absensi,
                                        @Field("ket") String ket,
                                        @Field("last_update_by") String last_update_by
        );
    }

    public interface iAPIDelKas {
        @FormUrlEncoded
        @POST("delete_kas.php")
        Call<Responseku> postDelkas(@Field("pid_kas") String pid_kas);
    }


}
