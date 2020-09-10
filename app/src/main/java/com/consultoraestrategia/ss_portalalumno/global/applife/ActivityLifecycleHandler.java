// https://gist.github.com/klaasnotfound/e14adefddaf72b941ef4e4245edca7e4
/*
 *    Copyright 2015 Klaas Klasing (klaas [at] klaasnotfound.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.consultoraestrategia.ss_portalalumno.global.applife;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * A convenience lifecycle handler that tracks whether the overall application is
 * started, in the foreground, in the background or stopped and ignores transitions
 * between individual activities.
 */
public class ActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    /**
     * Informs the listener about application lifecycle events.
     */
    public interface LifecycleListener {
        /**
         * Called right before the application is stopped.
         */
        void onApplicationStopped();

        /**
         * Called right after the application has been started.
         */
        void onApplicationStarted();

        /**
         * Called when the application is paused (but still awake).
         */
        void onApplicationPaused();

        /**
         * Called right after the application has been resumed (come to the foreground).
         */
        void onApplicationResumed();

        void onActivityCreated(Activity activity, Bundle bundle);
    }

    private LifecycleListener listener;
    private int started;
    private int resumed;
    private boolean transitionPossible;

    public ActivityLifecycleHandler(LifecycleListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        listener.onActivityCreated(activity, bundle);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (started == 0 && listener != null)
            listener.onApplicationStarted();
        started++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (resumed == 0 && !transitionPossible && listener != null)
            listener.onApplicationResumed();
        transitionPossible = false;
        resumed++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        transitionPossible = true;
        resumed--;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (started == 1 && listener != null) {
            // We only know the application was paused when it's stopped (because transitions always pause activities)
            // http://developer.android.com/guide/components/activities.html#CoordinatingActivities
            if (transitionPossible && resumed == 0)
                listener.onApplicationPaused();
            listener.onApplicationStopped();
        }
        transitionPossible = false;
        started--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

}
