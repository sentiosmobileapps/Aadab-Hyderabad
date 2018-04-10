package com.mydways.aadabhyd.network;

import com.mydways.aadabhyd.imagegallery.model.GalleryModel;
import com.mydways.aadabhyd.latestscrolling.model.DrawerItem;
import com.mydways.aadabhyd.latestscrolling.model.LatestNewsItem;
import com.mydways.aadabhyd.opnionpoll.model.ViwerPolling;
import com.mydways.aadabhyd.punches.model.PunchItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Kishore Adda on 31/3/18.
 */
public interface AppAPIServices {

    @GET("menus")
    Call<BaseAPIResponse<ArrayList<DrawerItem>>> getMenuItems();

    @GET("latest-news")
    Call<BaseAPIResponse<ArrayList<LatestNewsItem>>> fetchLatestScrolling();

    @GET("newslist/")
    Call<BaseAPIResponse<ArrayList<LatestNewsItem>>> getNewsByCategory(@Query("id") int categoryId);

    @GET("news/")
    Call<BaseAPIResponse> getArticleDetailsById(@Query("id") int articleId);

    @GET("districts/{stateCode}")
    Call<BaseAPIResponse> getDistrictsByState(@Path("stateCode") String stateCode);

    @GET("punch")
    Call<BaseAPIResponse<ArrayList<PunchItem>>> getAadabPunch();

    @GET("punches")
    Call<BaseAPIResponse<ArrayList<PunchItem>>> getAadabPunches();

    @GET("poll")
    Call<BaseAPIResponse<ViwerPolling>> getLatestPollingList();

    @POST("poll")
    Call<BaseAPIResponse<ViwerPolling>> postViewerPolling(@Query("poll_id") int pollId, @Query("opt") String
            option);

    @GET("gallery")
    Call<BaseAPIResponse<ArrayList<GalleryModel>>> getAadabGallery();

    @GET("gallery/{galleryId}")
    Call<BaseAPIResponse> getGalleryById(@Path("galleryId") int galleryId);

    @GET("videos")
    Call<BaseAPIResponse> getAadabVideos();

    @GET("social-links")
    Call<BaseAPIResponse> getAadabSocialLinks();

    @POST("comment")
    Call<BaseAPIResponse> postViewerComment();
}
