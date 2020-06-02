package yaqub.tesapp001.api;

import android.widget.Toast;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import yaqub.tesapp001.activity.KasActivity;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class APIRetrofitUpload {

    public interface iAPIUploadProfil {
        @Multipart
        @POST("upload_foto_profil.php")
        Call<Responseku> sendUploadProfil(@Part MultipartBody.Part image,
                                          @Part("nip") String nip,
                                          @Part("upload_url")String upload_url);
    }
//
//    public interface iAPIUpdateKehadiran {
//        @FormUrlEncoded
//        @POST("update_kehadiran.php")
//        Call<Responseku> sendDataUpdate(@Field("pid_kehadiran") String pid_kehadiran,
//                                        @Field("absensi") String absensi,
//                                        @Field("ket") String ket,
//                                        @Field("last_update_by") String last_update_by
//        );
//    }
//
//    public interface iAPIDelKas {
//        @FormUrlEncoded
//        @POST("delete_kas.php")
//        Call<Responseku> postDelkas(@Field("pid_kas") String pid_kas);
//    }
//

}
