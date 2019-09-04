package com.nzsoft.springcar.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final ApiRest API_REST;
    private static final String URL = "https://springcarback.herokuapp.com/";
    //private static final String URL = "http://localhost:8081/";

    static {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                //long milliseconds = json.getAsJsonPrimitive().getAsLong();
                String dateStr = json.getAsString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                Date date = null;

                try {
                    date = sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                String dateStr = sdf.format(src);
                long l = src.getTime();
                return new JsonPrimitive(dateStr);
            }
        });

        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        API_REST = retrofit.create(ApiRest.class);

    }

    public static ApiRest getApiRest(){
        return API_REST;
    }


}
