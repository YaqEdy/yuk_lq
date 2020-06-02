package yaqub.tesapp001.api;

import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yaqub.tesapp001.activity.AnggotaActivity;
import yaqub.tesapp001.activity.KehadiranActivity;
import yaqub.tesapp001.activity.MainActivity;
import yaqub.tesapp001.model.ResponseAnggota;
import yaqub.tesapp001.model.ResponseKehadiran;
import yaqub.tesapp001.model.ResponseLogin;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class APIRetrofitAnggota {

    public interface IAPIviewAnggota {
        @FormUrlEncoded
        @POST("view_anggota.php")
        Call<List<ResponseAnggota>> GetAllAnggota(@Field("id_group") String id_group);
    }

    public static void DoGetAllAnggota() {
        IAPIviewAnggota iapiPostAnggota = RetrofitClient.getClient().create(IAPIviewAnggota.class);
        Call call = iapiPostAnggota.GetAllAnggota(MainActivity.mainActivity.session.getSPidGroup());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                List<ResponseAnggota> responseAnggota = (List<ResponseAnggota>) response.body();
                AnggotaActivity.anggotaActivity.showAnggota(responseAnggota);
//                AnggotaFragment.anggotaFragment.showAnggota(responseAnggota);
//                AnggotaFragment.adapterPostAnggota=new AdapterPostAnggota(responseAnggota);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(AnggotaActivity.anggotaActivity, "Masalah koneksi.!", Toast.LENGTH_SHORT).show();
//                AnggotaActivity.anggotaActivity.showAnggota(null);
//                AnggotaFragment.anggotaFragment.showAnggota(null);
//                AnggotaFragment.adapterPostAnggota=new AdapterPostAnggota(null);

            }
        });
    }

    public interface iAPIInsertAnggota {
        @FormUrlEncoded
        @POST("update_anggota.php")
        Call<Responseku> sendInsertAnggota_(@Field("pid_persons") String pid_persons,
                                            @Field("nip") String nip,
                                            @Field("nama_person") String nama_person,
                                            @Field("jenis_kelamin") String jenis_kelamin,
                                            @Field("no_hp") String no_hp,
                                            @Field("email") String email,
                                            @Field("alamat") String alamat,
                                            @Field("ket") String ket,
                                            @Field("last_update_by") String last_update_by
        );
    }

    public interface iAPIUpdateAnggota {
        @FormUrlEncoded
        @POST("update_anggota.php")
        Call<Responseku> sendUpdateAnggota(@Field("pid_persons") String pid_persons,
                                           @Field("nip") String nip,
                                           @Field("nama_person") String nama_person,
                                           @Field("jenis_kelamin") String jenis_kelamin,
                                           @Field("no_hp") String no_hp,
                                           @Field("email") String email,
                                           @Field("alamat") String alamat,
                                           @Field("ket") String ket,
                                           @Field("last_update_by") String last_update_by
        );
    }

    public interface iAPIDelAnggota {
        @FormUrlEncoded
        @POST("delete_anggota.php")
        Call<Responseku> postDelAnggota(@Field("pid_persons") String pid_persons);
    }


    /**
     * Created by Yaqub on 12/15/2017.
     */

    public static class APIRetrofit {

        public interface IAPILogin {
            @FormUrlEncoded
            @POST("validasi_user_login.php")
            Call<ResponseLogin> sendLogin(@Field("nip") String nip,
                                          @Field("password") String password
            );
        }

        public interface IAPIRegister {
            @FormUrlEncoded
            @POST("register_user.php")
            Call<Responseku> sendRegister(@Field("nip") String nip,
                                          @Field("account") String account,
                                          @Field("password") String password,
                                          @Field("replace") String replace
            );
        }

        public interface IAPIPostKehadiranAll {
            @FormUrlEncoded
            @POST("view_kehadiran.php")
            Call<List<ResponseKehadiran>> GetPostKehadiranAll(@Field("param") String param,
                                                              @Field("id_group") String id_group);
        }

        public interface IAPIPost {
            @FormUrlEncoded
            @POST("view_kehadiran.php")
            Call<List<ResponseKehadiran>> GetAllPost(@Field("param") String param,
                                                     @Field("id_group") String id_group);
        }

        public static void DoPostAllKehadiran(String sParam) {
            Call call;
            String iIdGroup;
            if (MainActivity.mainActivity.session.getSPidGroup().equals("")) {
                iIdGroup = "";
            } else {
                iIdGroup = MainActivity.mainActivity.session.getSPidGroup();
            }
            if (sParam.equals("1")) {
                IAPIPostKehadiranAll iapiPostKehadiranAll = RetrofitClient.getClient().create(IAPIPostKehadiranAll.class);
                call = iapiPostKehadiranAll.GetPostKehadiranAll(sParam, iIdGroup);
            } else {
                IAPIPost iapiPost = RetrofitClient.getClient().create(IAPIPost.class);
                call = iapiPost.GetAllPost(sParam, iIdGroup);
            }
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    List<ResponseKehadiran> responseKehadirans = (List<ResponseKehadiran>) response.body();
                    KehadiranActivity.kehadiranActivity.showData(responseKehadirans);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    KehadiranActivity.kehadiranActivity.showData(null);

                }
            });
        }

        public interface iAPIGenerateKehadiran {
            @FormUrlEncoded
            @POST("generate_kehadiran.php")
            Call<Responseku> postGenerateKehadiran(@Field("generate") String generate);
        }

        public interface iAPIDelGenerateKehadiran {
            @FormUrlEncoded
            @POST("delete_generate_kehadiran.php")
            Call<Responseku> postDelGenerateKehadiran(@Field("generate") String generate);
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

    }
}
