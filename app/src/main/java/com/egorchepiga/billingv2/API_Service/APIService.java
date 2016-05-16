package com.egorchepiga.billingv2.API_Service;

/**
 * Created by George on 16.03.2016.
 */
import com.egorchepiga.billingv2.POJO.Payment;
import com.egorchepiga.billingv2.POJO.Post;
import com.egorchepiga.billingv2.POJO.Repair;
import com.egorchepiga.billingv2.POJO.StockService;
import com.egorchepiga.billingv2.POJO.Type;
import com.egorchepiga.billingv2.POJO.User;
import com.egorchepiga.billingv2.POJO.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* Retrofit 2.0 */

public interface APIService {

    @GET("get_repair?")
    Call<Repair> getRepair(@Query("repair_id") String id);

    @GET("get_type")
    Call<List<Type>> getType();

    @GET("get_abonentj?")
    Call<User> getUserInfo(@Query("username") String username);

    @GET("get_uslugaj?")
    Call<List<StockService>> getUsluga();

    @GET("get_payments?")
    Call<List<Payment>> getPayments(@Query("user_id") String user_id);

    @GET("get_service2?")
    Call<List<UserService>> getAllServices(@Query("username") String username);

    @GET("get_repairs?")
    Call<List<Repair>> getRepairs(@Query("user_id") String user_id);

    @GET("get_login?")
    Call<User> getLogin(@Query("username") String username,@Query("password") String password);

    @GET("add_payment?")
    Call<Post> addPayment(@Query("user_id") String user_id,@Query("sum") String sum,@Query("balance") String balance,@Query("time") String time);

    @GET("add_repair?")
    Call<Post> addRepair(@Query("type") String type,@Query("telephone") String telephone,@Query("time") String time,@Query("abonent_id") String abonent_id,@Query("status") String status,@Query("description") String description);

    @GET("change_repair?")
    Call<Post> changeRepair(@Query("repair_id") String repair_id,@Query("type") String type,@Query("telephone") String telephone,@Query("time") String time,@Query("description") String description);

    @GET("delete_service?")
    Call<Post> deleteService(@Query("service") String service_id,@Query("user_id") String user_id,@Query("balance") String balance);

    @GET("change_abonent?")
    Call<Post> changeUserInfo(@Query("oldusername") String oldusername,@Query("username") String username,@Query("password") String password);

    @GET("add_service?")
    Call<Post> addService(@Query("ontime") String ontime,@Query("offtime") String offtime,@Query("user_id") String user_id,@Query("service") String service,@Query("balance") String balance);

}



