package com.consultoraestrategia.ss_portalalumno.youtube;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DialogFragmentYoutube extends DialogFragment {
    private static final String TAG = "DialogYoutubeTAG";
    @BindView(R.id.youtube_layout)
    FrameLayout youtubeLayout;
    @BindView(R.id.root)
    ConstraintLayout root;
    private Unbinder unbinder;
    private YoutubeConfig youtubeConfig;
    private String url = "https://www.youtube.com/watch?v=BI-GRSXLg1M";
    private boolean landscape;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_youtube, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().requestWindowFeature(STYLE_NO_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (youtubeConfig == null) youtubeConfig = new YoutubeConfig(getContext());
        Log.d(TAG, url);
        if (youtubeConfig != null){
            youtubeConfig.setDisabledRotation(true);
            youtubeConfig.initialize(YouTubeUrlParser.getVideoId(url), getChildFragmentManager(), R.id.youtube_layout, new YoutubeConfig.PlaybackEventListener() {
                @Override
                public void onPlaying() {
                    //imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_pause_youtube));
                }

                @Override
                public void onPaused() {
                    //imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_play_youtube));
                }

                @Override
                public void onLandscape() {

                }

                @Override
                public void onPortrait() {

                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        youtubeConfig = null;
    }


}
