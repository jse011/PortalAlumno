package com.consultoraestrategia.ss_portalalumno.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.consultoraestrategia.ss_portalalumno.R;

public class PreviewMorePlaceholder extends FrameLayout {
    private View view;
    private ImageView preview;
    private ImageView previewBackground;
    private View shimerPreview;
    private View btnVideo;
    private View morePreview;
    private TextView morePreviewCount;

    public PreviewMorePlaceholder(@NonNull Context context) {
        super(context);
        init();
    }

    public PreviewMorePlaceholder(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PreviewMorePlaceholder(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PreviewMorePlaceholder(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.layout_preview_more_placeholder, null);
        addView(view);
        preview = view.findViewById(R.id.preview);
        shimerPreview = view.findViewById(R.id.shimer_preview);
        btnVideo = view.findViewById(R.id.btn_video);
        previewBackground = view.findViewById(R.id.previewBackground);
        morePreview = view.findViewById(R.id.more_preview);
        morePreviewCount = view.findViewById(R.id.more_preview_count);


    }

    public void bindPreview(String imagePreview, boolean video) {
        morePreview.setVisibility(View.GONE);
        shimerPreview.setVisibility(View.VISIBLE);
        preview.setVisibility(View.INVISIBLE);
        preview.setImageDrawable(null);
        btnVideo.setVisibility(video ? View.VISIBLE : View.GONE);
        previewBackground.setVisibility(View.GONE);
        previewBackground.setImageDrawable(null);
        Glide.with(preview)
                .asBitmap()
                .load(imagePreview)
                //.load("")
                .transition(BitmapTransitionOptions.withCrossFade())
                .placeholder(new ColorDrawable(Color.TRANSPARENT))
                .apply(UtilsGlide.getGlideRequestOptionsSimple())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        shimerPreview.setVisibility(video? View.INVISIBLE : View.VISIBLE);
                        preview.setVisibility(View.INVISIBLE);
                        previewBackground.setVisibility(video? View.VISIBLE : View.INVISIBLE);
                        previewBackground.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.color_shimmer)));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        shimerPreview.setVisibility(View.GONE);
                        preview.setVisibility(View.VISIBLE);
                        previewBackground.setVisibility(View.VISIBLE);
                        previewBackground.setImageBitmap(getBlurImage(getBlurImage(getBlurImage((resource)))));
                        return false;
                    }
                })
                .into(preview);


    }

    public void bindMore(String imagePreview, boolean video,int count) {
        morePreview.setVisibility(View.VISIBLE);
        shimerPreview.setVisibility(View.VISIBLE);
        preview.setVisibility(View.INVISIBLE);
        preview.setImageDrawable(null);
        btnVideo.setVisibility(View.GONE);
        previewBackground.setVisibility(View.GONE);
        previewBackground.setImageDrawable(null);
        Glide.with(preview)
                .asBitmap()
                .load(imagePreview)
                //.load("")
                .transition(BitmapTransitionOptions.withCrossFade())
                .placeholder(new ColorDrawable(Color.TRANSPARENT))
                .apply(UtilsGlide.getGlideRequestOptionsSimple())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        shimerPreview.setVisibility(video? View.GONE : View.VISIBLE);
                        preview.setVisibility(View.INVISIBLE);
                        previewBackground.setVisibility(video? View.VISIBLE : View.INVISIBLE);
                        previewBackground.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.color_shimmer)));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        shimerPreview.setVisibility(View.GONE);
                        preview.setVisibility(View.VISIBLE);
                        previewBackground.setVisibility(View.VISIBLE);
                        previewBackground.setImageBitmap(getBlurImage(getBlurImage(getBlurImage((resource)))));
                        return false;
                    }
                })
                .into(preview);
        String more = "+"+count;
        morePreviewCount.setText(more);

    }



    public Bitmap getBlurImage(Bitmap imagenRAW){

        Bitmap salidaBitmap = Bitmap.createBitmap(imagenRAW);

        final RenderScript renderScript = RenderScript.create(getContext());

        Allocation entradaTemp = Allocation.createFromBitmap(renderScript, imagenRAW);
        Allocation salidaTemp = Allocation.createFromBitmap(renderScript,salidaBitmap);

        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        scriptIntrinsicBlur.setRadius(25f);
        //scriptIntrinsicBlur.setRadius(5f);
        scriptIntrinsicBlur.setInput(entradaTemp);
        scriptIntrinsicBlur.forEach(salidaTemp);

        salidaTemp.copyTo(salidaBitmap);

        return salidaBitmap;
    }

    public Bitmap toSquare(Bitmap bitmap){
        int side = Math.min(bitmap.getWidth(),bitmap.getHeight());

        // calculate the x and y offset
        int xOffset = (bitmap.getWidth() - side) /2;
        int yOffset = (bitmap.getHeight() - side)/2;

        // create a square bitmap
        // a square is closed, two dimensional shape with 4 equal sides
        return Bitmap.createBitmap(
                bitmap, // source bitmap
                xOffset, // x coordinate of the first pixel in source
                yOffset, // y coordinate of the first pixel in source
                side, // width
                side // height
        );
    }

    public void unbind(){
        previewBackground.setVisibility(View.GONE);
        Glide.with(previewBackground).clear(previewBackground);
        previewBackground.setImageDrawable(null);
        preview.setVisibility(View.GONE);
        Glide.with(preview).clear(preview);
        preview.setImageDrawable(null);
    }

}


