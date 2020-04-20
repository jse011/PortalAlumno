package com.consultoraestrategia.ss_portalalumno.base.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LifecycleImpl extends FragmentManager.FragmentLifecycleCallbacks {


    private LifecycleListener listener;
    private int offscreenPageLimit;
    /**
     * Informs the listener about application lifecycle events.
     */
    public interface LifecycleListener {
        /**
         * Called right before the application is stopped.
         */
        void onChildsFragmentViewCreated();
        /**
         * Called right after the application has been started.
         */
        void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState);
        /**
         * Called right after the application has been resumed (come to the foreground).
         */
        void onFragmentResumed(Fragment f);
        /**
         * Called when the application is paused (but still awake).
         */
        void onFragmentViewDestroyed(Fragment f);

        void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState);

    }

    public LifecycleImpl(int offscreenPageLimit, LifecycleListener listener) {
        this.listener = listener;
        this.offscreenPageLimit = offscreenPageLimit;
    }

    @Override
    public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentPreAttached(fm, f, context);
        Log.d(getClass().getSimpleName(), "onFragmentPreAttached: " +f.getClass());
    }

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
        Log.d(getClass().getSimpleName(), "onFragmentAttached: " +f.getClass());
    }

    @Override
    public void onFragmentPreCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentPreCreated(fm, f, savedInstanceState);
        Log.d(getClass().getSimpleName(), "onFragmentPreCreated: " +f.getClass());
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        Log.d(getClass().getSimpleName(), "onFragmentCreated: " +f.getClass());
        Log.d(getClass().getSimpleName(), "offscreenPageLimit: " + offscreenPageLimit);
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        if(listener!=null)listener.onFragmentActivityCreated(f,savedInstanceState);
        Log.d(getClass().getSimpleName(), "onFragmentActivityCreated: " +f.getClass());
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        Log.d(getClass().getSimpleName(), "onFragmentViewCreated: " +f.getClass());
        if(offscreenPageLimit==0&&listener!=null)listener.onChildsFragmentViewCreated();
        offscreenPageLimit--;
        if(listener!=null)listener.onFragmentViewCreated(f,v,savedInstanceState );
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentStarted: " +f.getClass());
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentResumed: " +f.getClass());
        if(listener!=null)listener.onFragmentResumed(f );
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentPaused: " +f.getClass());
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentStopped: " +f.getClass());
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
        Log.d(getClass().getSimpleName(), "onFragmentSaveInstanceState: " +f.getClass());
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentViewDestroyed: " +f.getClass());
        if(listener!=null)listener.onFragmentViewDestroyed(f );
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentDestroyed: " +f.getClass());
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
        Log.d(getClass().getSimpleName(), "onFragmentDetached: " +f.getClass());
    }

}
