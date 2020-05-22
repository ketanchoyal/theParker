package com.service.parking.theparker.View;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.service.parking.theparker.R;

public class SnackbarWrapper {

    private final CharSequence text;
    private final int duration;
    private final WindowManager windowManager;
    private final Context appplicationContext;
    @Nullable
    private Snackbar.Callback externalCallback;
    @Nullable
    private Action action;

    @NonNull
    public static SnackbarWrapper make(@NonNull Context applicationContext, @NonNull CharSequence text, @Snackbar.Duration int duration) {
        return new SnackbarWrapper(applicationContext, text, duration);
    }

    @NonNull
    public static SnackbarWrapper make(@NonNull Context applicationContext, @NonNull CharSequence text) {
        return new SnackbarWrapper(applicationContext, text);
    }

    private SnackbarWrapper(@NonNull final Context appplicationContext, @NonNull CharSequence text, @Snackbar.Duration int duration) {
        this.appplicationContext = appplicationContext;
        this.windowManager = (WindowManager) appplicationContext.getSystemService(Context.WINDOW_SERVICE);
        this.text = text;
        this.duration = duration;
    }

    private SnackbarWrapper(@NonNull final Context appplicationContext, @NonNull CharSequence text) {
        this.appplicationContext = appplicationContext;
        this.windowManager = (WindowManager) appplicationContext.getSystemService(Context.WINDOW_SERVICE);
        this.text = text;
        this.duration = Snackbar.LENGTH_LONG;
    }

    public void show() {
        WindowManager.LayoutParams layoutParams = createDefaultLayoutParams(WindowManager.LayoutParams.TYPE_TOAST, null);
        windowManager.addView(new FrameLayout(appplicationContext) {
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                onRootViewAvailable(this);
            }

        }, layoutParams);
    }

    private void onRootViewAvailable(final FrameLayout rootView) {
        final CoordinatorLayout snackbarContainer = new CoordinatorLayout(new ContextThemeWrapper(appplicationContext, R.style.FOL_Theme_SnackbarWrapper)) {
            @Override
            public void onAttachedToWindow() {
                super.onAttachedToWindow();
                onSnackbarContainerAttached(rootView, this);
            }
        };
        windowManager.addView(snackbarContainer, createDefaultLayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, rootView.getWindowToken()));
    }

    private void onSnackbarContainerAttached(final View rootView, final CoordinatorLayout snackbarContainer) {
        Snackbar snackbar = Snackbar.make(snackbarContainer, text, duration);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                // Clean up (NOTE! This callback can be called multiple times)
                if (snackbarContainer.getParent() != null && rootView.getParent() != null) {
                    windowManager.removeView(snackbarContainer);
                    windowManager.removeView(rootView);
                }
                if (externalCallback != null) {
                    externalCallback.onDismissed(snackbar, event);
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                if (externalCallback != null) {
                    externalCallback.onShown(snackbar);
                }
            }
        });
        if (action != null) {
            snackbar.setAction(action.text, action.listener);
        }
        snackbar.show();
    }

    private WindowManager.LayoutParams createDefaultLayoutParams(int type, @Nullable IBinder windowToken) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.type = type;
        layoutParams.token = windowToken;
        return layoutParams;
    }

    @NonNull
    public SnackbarWrapper setCallback(@Nullable Snackbar.Callback callback) {
        this.externalCallback = callback;
        return this;
    }

    @NonNull
    public SnackbarWrapper setAction(CharSequence text, final View.OnClickListener listener) {
        action = new Action(text, listener);
        return this;
    }

    private static class Action {
        private final CharSequence text;
        private final View.OnClickListener listener;

        public Action(CharSequence text, View.OnClickListener listener) {
            this.text = text;
            this.listener = listener;
        }
    }
}
