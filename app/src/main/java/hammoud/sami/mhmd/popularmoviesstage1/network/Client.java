package hammoud.sami.mhmd.popularmoviesstage1.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public final static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;
    public static Retrofit client(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


}
