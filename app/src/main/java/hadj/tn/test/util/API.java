package hadj.tn.test.util;

import java.util.List;

import hadj.tn.test.Model.AppUserRole;
import hadj.tn.test.Model.Post;
import hadj.tn.test.Model.User;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API {

    @POST("api/auth/signup")
    Call<ResponseBody> createUser (
            @Body User user
    );
    @POST("api/auth/login")
    Call<ResponseBody> checkUser (
            @Body User user
    );
    @GET("api/auth/{id}")
    Call<User> getUserById(@Path("id") long userId);

    @GET("api/auth/info/{email}")
    Call<User> getUser(@Path("email") String email);

    @GET("api/auth/user/{username}")
    Call<User> findUser(@Path("username") String username);

    @GET("/api/auth/users")
    Call<List<User>> getAllUsers();

    @POST("/api/auth/update")
    Call<ResponseBody> updateUser(@Body User user);


    @POST("/api/pub/addPublication")
    Call<ResponseBody> createPost(
            @Header("Authorization") String token, @Body Post post
    );

    @GET("api/pub/getPublications")
    Call<List<Post>> findAllPubs(  @Header("Authorization") String token);

    @DELETE("api/pub/deletePub/{id}")
    Call<Void> deletePubById(@Path("id") int id);

    @GET("api/auth/byRole/{role}")
    Call<List<User>> findByRole(@Path ("role") AppUserRole role);
}

