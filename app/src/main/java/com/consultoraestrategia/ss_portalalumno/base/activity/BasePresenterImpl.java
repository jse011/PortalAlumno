package com.consultoraestrategia.ss_portalalumno.base.activity;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;


/**
 * Created by @stevecampos on 15/01/2018.
 */

public abstract class BasePresenterImpl<V extends com.consultoraestrategia.ss_portalalumno.base.activity.BaseView> implements BasePresenter<V> {

    protected abstract String getTag();

    protected V view;
    protected UseCaseHandler handler;
    protected Resources res;

    public BasePresenterImpl(UseCaseHandler handler, Resources res) {
        this.handler = handler;
        this.res = res;
    }

    public Bundle getExtras() {
        return extras;
    }

    @Override
    public void attachView(V view) {
        Log.d(getTag(), "attachView");
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(getTag(), "onCreate");
    }

    @Override
    public void onStart() {
        Log.d(getTag(), "onStart");
    }

    @Override
    public void onResume() {
        Log.d(getTag(), "onResume");
    }

    @Override
    public void onPause() {
        Log.d(getTag(), "onPause");
    }

    @Override
    public void onStop() {
        Log.d(getTag(), "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(getTag(), "onDestroy");
        this.view = null;
    }

    protected Bundle extras;

    @Override
    public void setExtras(Bundle extras) {
        Log.d(getTag(), "setExtras");
        this.extras = extras;
    }


    @Override
    public void onBackPressed() {
        Log.d(getTag(), "onBackPressed");
    }


    protected void showProgress() {
        if (view != null) view.showProgress();
    }

    protected void hideProgress() {
        if (view != null) view.hideProgress();
    }

    protected void showMessage(CharSequence message) {
        if (view != null) view.showMessage(message);
    }

    protected void showImportantMessage(CharSequence message) {
        if (view != null) view.showImportantMessage(message);
    }

    protected void showFinalMessage(CharSequence message){
        if (view != null) view.showFinalMessage(message);
    }

    protected void showFinalMessageAceptCancel(CharSequence message, CharSequence messageTitle){
        if (view != null) view.showFinalMessageAceptCancel(message, messageTitle);
    }

}
