package com.example.saver.Others;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.saver.R;

public class Loader extends Dialog {
    ProgressBar progress;
    Context mContext;
    Loader loader;

    public Loader(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public Loader(Context context, int theme) {
        super(context, theme);
    }

    public Loader show(CharSequence message) {
        loader = new Loader (mContext, R.style.ProgressDialog);
        loader.setContentView(R.layout.progress_loader);
        progress = (ProgressBar) loader.findViewById (R.id.progress);
        progress.setColorSchemeResources(R.color.primarylight, R.color.primarydark, R.color.primary);
        loader.setCancelable(false);
        if(loader!= null) {
            loader.show ();
        }
        return loader;
    }
    public Loader dismiss(CharSequence message) {
        if(loader!=null) {
            loader.dismiss();
        }
        return loader;
    }
}