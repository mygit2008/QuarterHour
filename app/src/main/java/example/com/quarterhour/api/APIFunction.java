package example.com.quarterhour.api;

import example.com.myexample.bean.FoodsBean;
import example.com.myexample.bean.HomePagerBean;
import example.com.myexample.bean.RestaurantBean;
import example.com.myexample.bean.UpdateNickNameBean;
import example.com.myexample.bean.UploadBean;
import example.com.myexample.bean.UserInfo;
import example.com.myexample.bean.VersionBean;
import example.com.myexample.login.bean.LoginBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author zhangjunyou
 * @date 2018/7/8
 * @description
 * @Copyright 版权所有, 未经授权不得转载其他 .
 */

public interface APIFunction {
    /**
     * 登录
     *
     * @param mobile
     * @param password
     * @return
     */
    @GET("user/login")
    Observable<LoginBean> login(@Query("mobile") String mobile, @Query("password") String password);

    /**
     * 获取用户信息
     *
     * @param uid
     * @return
     */
    @GET("user/getUserInfo")
    Observable<UserInfo> getUserInfo(@Query("uid") String uid);

    /**
     * 第一种方式，都是part对象进行传递
     *
     * @param uid
     * @param file
     * @return
     */
    @POST("file/upload")
    @Multipart
    Observable<UploadBean> uploadFile(@Part MultipartBody.Part uid, @Part MultipartBody.Part file);

    /**
     * 第二种方式，普通参数作为requestbody传递
     *
     * @param uid
     * @param file
     * @return
     */
    @POST("file/upload")
    @Multipart
    Observable<UploadBean> uploadFile(@Part("uid") RequestBody uid, @Part MultipartBody.Part file);

    /**
     * 第三种方式，字符串参数直接作为get请求参数，放到url后面
     *
     * @param uid
     * @param file
     * @return
     */
    @POST("file/upload")
    @Multipart
    Observable<UploadBean> uploadFile(@Query("uid") String uid, @Part MultipartBody.Part file);

    /**
     * 修改昵称
     *
     * @param uid
     * @param nickname
     * @return
     */
    @GET("user/updateNickName")
    Observable<UpdateNickNameBean> updateNickName(@Query("uid") String uid, @Query("nickname") String nickname);

    /**
     * 首页
     *
     * @return
     */
    @GET("restaurant-list")
    Observable<FoodsBean> getHome();

    /**
     * @return
     */
    @GET("restaurants_offset_4_limit_4")
    Observable<RestaurantBean> getRestaurant();

    /**
     * 版本更新
     *
     * @return
     */
    @GET("quarter/getVersion")
    Observable<VersionBean> getVersion();
}
