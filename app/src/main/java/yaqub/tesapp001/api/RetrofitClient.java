package yaqub.tesapp001.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static String url = "http://yedys.000webhostapp.com/";
//    public static String url = "http://192.168.43.154:88/yuk_lq/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
