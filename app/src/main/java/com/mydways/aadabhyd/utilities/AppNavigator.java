package com.mydways.aadabhyd.utilities;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.mydways.aadabhyd.latestscrolling.model.LatestNewsItem;
import com.mydways.aadabhyd.latestscrolling.view.LatestNewsActivity;
import com.mydways.aadabhyd.latestscrolling.view.NewsInDetailActivity;
import com.mydways.aadabhyd.onboarding.EpaperActivity;
import com.mydways.aadabhyd.onboarding.HomeScreenActivity;
import com.mydways.aadabhyd.opnionpoll.view.OpnionPollActivity;
import com.mydways.aadabhyd.punches.view.PunchActivity;
import com.mydways.aadabhyd.punches.view.PunchGaleryActivity;
import com.mydways.aadabhyd.quiz.view.QuizActivity;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class AppNavigator {
    public void navigateToHomeScreen(Context context){
        Intent intent = new Intent(context, HomeScreenActivity.class);
        context.startActivity(intent);
    }

    public void navigateToLatestScrollingNews(Context context){
        Intent intent = new Intent(context, LatestNewsActivity.class);
        context.startActivity(intent);
    }

    public void navigateToNewsDetails(Context context, LatestNewsItem item){
        Intent intent = new Intent(context, NewsInDetailActivity.class);
        intent.putExtra("data",new Gson().toJson(item));
        context.startActivity(intent);
    }

    public void navigateToEPaper(Context context){
        Intent intent = new Intent(context, EpaperActivity.class);
        context.startActivity(intent);
    }

    public void navigateToLatestPunch(Context context){
        Intent intent = new Intent(context, PunchActivity.class);
        context.startActivity(intent);
    }

    public void navigateToPunchGalleryActivity(Context context){
        Intent intent = new Intent(context, PunchGaleryActivity.class);
        context.startActivity(intent);
    }


    public void navigateToQuizPage(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);
        context.startActivity(intent);
    }

    public void navigateToPollingPage(Context context) {
        Intent intent = new Intent(context, OpnionPollActivity.class);
        context.startActivity(intent);
    }
}
