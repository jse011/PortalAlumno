package com.consultoraestrategia.ss_portalalumno.youtube;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class YoutubeConfig implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {
    public static final String API_KEY = "AIzaSyBg3blO9e6Q02C9V-58xh3eQhGy9TxirXA";
    private Fragment fragment;
    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @SuppressLint("InlinedApi")
    private static final int LANDSCAPE_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

    private YouTubePlayer mPlayer = null;
    private boolean mAutoRotation = false;
    private String idVideo;
    private PlaybackEventListener playbackEventListener;
    private boolean disabledRotation;

    public YoutubeConfig(Context context) {
        mAutoRotation = Settings.System.getInt(context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    public void initialize(String idVideo, FragmentManager fragmentManager, @IdRes int id, PlaybackEventListener playbackEventListener){
        this.idVideo = idVideo;
        this.playbackEventListener = playbackEventListener;
        Log.d("YoutubeConfig ", idVideo);

        fragment = (Fragment)((Object)YouTubePlayerSupportFragment.newInstance());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, fragment).commit();
        fragment.setRetainInstance(true);
        ((YouTubePlayerSupportFragment)(Object)fragment).initialize(YoutubeConfig.API_KEY, this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        /*if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (mPlayer != null)
                mPlayer.setFullscreen(true);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mPlayer != null)
                mPlayer.setFullscreen(false);
        }*/
    }

    private void reload(View view){
        if(view instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); i++) {
               reload(((ViewGroup)view).getChildAt(i));
            }
        }else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            this.mPlayer = youTubePlayer;
            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            //Tell the player you want to control the fullscreen change
            if(!disabledRotation){
                mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                //Tell the player how to control the change
                //reload(fragment.getView());
                youTubePlayer.setOnFullscreenListener(this);
            }

            youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                @Override
                public void onPlaying() {
                    Log.d("YoutubeConfig", "onPlaying");
                    if(playbackEventListener!=null)
                        playbackEventListener.onPlaying();
                }

                @Override
                public void onPaused() {
                    Log.d("YoutubeConfig", "onPaused");
                    if(playbackEventListener!=null)
                        playbackEventListener.onPaused();
                }

                @Override
                public void onStopped() {
                    Log.d("YoutubeConfig", "onStopped");
                    if(playbackEventListener!=null)
                    playbackEventListener.onPaused();
                }

                @Override
                public void onBuffering(boolean b) {
                    Log.d("YoutubeConfig", "onBuffering: " + b);
                }

                @Override
                public void onSeekTo(int i) {
                    Log.d("YoutubeConfig", "onSeekTo: "+i);
                }
            });

            youTubePlayer.loadVideo(idVideo);
            youTubePlayer.play();

        }

        if(!disabledRotation){
            if (mAutoRotation) {
                youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                        | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                        | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                        | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
            } else {
                youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                        | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                        | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);

            }
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onFullscreen(boolean fullsize) {
        if (fullsize) {
            fragment.getActivity().setRequestedOrientation(LANDSCAPE_ORIENTATION);
            if(this.playbackEventListener!=null)this.playbackEventListener.onLandscape();
        } else {
            fragment.getActivity().setRequestedOrientation(PORTRAIT_ORIENTATION);
            if(this.playbackEventListener!=null)this.playbackEventListener.onPortrait();
        }
    }

    public void closeVideo(FragmentManager fragmentManager, Activity activity) {
        if(fragment!=null&&fragmentManager!=null){
            try {
                FragmentTransaction trans = fragmentManager.beginTransaction();
                trans.remove(fragment);
                trans.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
            mPlayer = null;
            idVideo=null;
            fragment = null;
            playbackEventListener = null;
            if(activity!=null)
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }


    }

    public ViewGroup getYouTubePlayerView(){
        ViewGroup viewGroup = null;
        if(fragment!=null&&fragment.getView()!=null){
            Log.d("YoutubeConfig Class", fragment.getView().getClass().getSimpleName());
            viewGroup = (ViewGroup)fragment.getView();
        }
        return viewGroup;
    }

    public void hideButton(){
        try {
            if (mPlayer != null)
                mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }catch (Exception ignored){

        }

    }

    public void showButton(){
       try{
           if (mPlayer != null)
               mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
       }catch (Exception ignored){ }

    }

    public void onStartOrPaused() {
        try {
            if (mPlayer != null){
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                }else {
                    mPlayer.play();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setDisabledRotation(boolean disabled) {
        disabledRotation = disabled;
    }



    public interface PlaybackEventListener{
        void onPlaying();
        void onPaused();
        void onLandscape();
        void onPortrait();
    }



}
