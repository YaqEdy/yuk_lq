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
import yaqub.tesapp001.activity.MainActivity;
import yaqub.tesapp001.activity.NotulenActivity;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.ResponseNotulen;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class APIRetrofitNotulen {

    public interface IAPIviewNotulen {
        @FormUrlEncoded
        @POST("view_notulen.php")
        Call<List<ResponseNotulen>> GetAllNotulen(@Field("id_group") String id_group);
    }

    public static void DoGetAllNotulen() {
        IAPIviewNotulen iapiPostKas = RetrofitClient.getClient().create(IAPIviewNotulen.class);
        Call call = iapiPostKas.GetAllNotulen(MainActivity.mainActivity.session.getSPidGroup());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                List<ResponseNotulen> responseNotes = (List<ResponseNotulen>) response.body();
                NotulenActivity.notulenActivity.showNotulen(responseNotes);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(NotulenActivity.notulenActivity, "Masalah koneksi.!", Toast.LENGTH_SHORT).show();
//                NotulenActivity.notulenActivity.showNotulen(null);

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
