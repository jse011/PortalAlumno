package com.consultoraestrategia.ss_portalalumno.base;

/**
 * Created by @stevecampos on 15/09/2017.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onBackPressed();
}
