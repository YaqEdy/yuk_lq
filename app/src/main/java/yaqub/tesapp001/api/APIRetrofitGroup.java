package yaqub.tesapp001.api;

import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yaqub.tesapp001.activity.GroupActivity;
import yaqub.tesapp001.activity.KasActivity;
import yaqub.tesapp001.model.ResponseGroup;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class APIRetrofitGroup {

    public interface IAPIviewGroup {
        @POST("view_group.php")
        Call<List<ResponseGroup>> GetAllGroup();
    }

    public static void DoGetAllGroup() {
        IAPIviewGroup iapiPostGroup = RetrofitClient.getClient().create(IAPIviewGroup.class);
        Call call = iapiPostGroup.GetAllGroup();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                List<ResponseGroup> responseGroup = (List<ResponseGroup>) response.body();
                GroupActivity.groupActivity.showGroup(responseGroup);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(GroupActivity.groupActivity, "Masalah koneksi.!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public interface iAPIInsertGroup {
        @FormUrlEncoded
        @POST("insert_group.php")
        Call<Responseku> sendInsertGroup(
                                       @Field("id_group") String id_group,
                                       @Field("nama_group") String nama_group,
                                       @Field("group_desc") String group_desc
        );
    }

    public interface iAPIUpdateGroup {
        @FormUrlEncoded
        @POST("update_group.php")
        Call<Responseku> sendDataGroup(
                                        @Field("id_group") String id_group,
                                        @Field("nama_group") String nama_group,
                                        @Field("group_desc") String group_desc
        );
    }

    public interface iAPIDelGroup {
        @FormUrlEncoded
        @POST("delete_group.php")
        Call<Responseku> postDelGroup(@Field("id_group") String pid_kas);
    }


}
