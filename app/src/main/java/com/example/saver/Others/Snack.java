package com.example.saver.Others;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnimIconBuilder;
import com.androidadvance.topsnackbar.TSnackbar;
import com.example.saver.R;

public class Snack {

    Context context;

    public Snack(Context context) {
        this.context = context;
    }

    public void host(String message){
        Typeface bold = Typeface.createFromAsset(
                context.getAssets(), "bold.ttf");
        Typeface regular = Typeface.createFromAsset(
                context.getAssets(), "regular.ttf");
        FlashAnimIconBuilder flashAnimIconBuilder = new FlashAnimIconBuilder(context);
        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .title("INFO")
                .message(message)
                .backgroundColorRes(R.color.info_background)
                .castShadow(true, 50)
                .icon(R.drawable.host)
                .iconColorFilter(R.color.primarylight)
                .duration(3000)
                .titleColor(R.color.primarylight)
                .messageColor(R.color.primarylight)
                .titleTypeface(bold)
                .messageTypeface(regular)
                .titleSizeInSp(14)
                .messageSizeInSp(12)
                .iconAnimation(flashAnimIconBuilder.pulse())
                .showIcon()
                .build()
                .show();
    }

    public void warning(String message){
        Typeface bold = Typeface.createFromAsset(
                context.getAssets(), "bold.ttf");
        Typeface regular = Typeface.createFromAsset(
                context.getAssets(), "regular.ttf");
        FlashAnimIconBuilder flashAnimIconBuilder = new FlashAnimIconBuilder(context);
        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .title("WARNING")
                .message(message)
                .backgroundColorRes(R.color.warning_background)
                .castShadow(true, 50)
                .icon(R.drawable.error)
                .iconColorFilter(R.color.primarylight)
                .duration(3000)
                .titleColor(R.color.primarylight)
                .messageColor(R.color.primarylight)
                .titleTypeface(bold)
                .messageTypeface(regular)
                .titleSizeInSp(14)
                .messageSizeInSp(12)
                .iconAnimation(flashAnimIconBuilder.pulse())
                .showIcon()
                .build()
                .show();
    }

    public void network(String message){
        Typeface bold = Typeface.createFromAsset(
                context.getAssets(), "bold.ttf");
        Typeface regular = Typeface.createFromAsset(
                context.getAssets(), "regular.ttf");
        FlashAnimIconBuilder flashAnimIconBuilder = new FlashAnimIconBuilder(context);
        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .title("NETWORK")
                .message(message)
                .backgroundColorRes(R.color.warning_background)
                .castShadow(true, 50)
                .icon(R.drawable.network)
                .iconColorFilter(R.color.primarylight)
                .duration(3000)
                .titleColor(R.color.primarylight)
                .messageColor(R.color.primarylight)
                .titleTypeface(bold)
                .messageTypeface(regular)
                .titleSizeInSp(14)
                .messageSizeInSp(12)
                .iconAnimation(flashAnimIconBuilder.pulse())
                .showIcon()
                .build()
                .show();
    }

    public void success(String message){
        Typeface bold = Typeface.createFromAsset(
                context.getAssets(), "bold.ttf");
        Typeface regular = Typeface.createFromAsset(
                context.getAssets(), "regular.ttf");
        FlashAnimIconBuilder flashAnimIconBuilder = new FlashAnimIconBuilder(context);
        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .title("SUCCESS")
                .message(message)
                .backgroundColorRes(R.color.success_background)
                .castShadow(true, 50)
                .icon(R.drawable.success)
                .iconColorFilter(R.color.primarylight)
                .duration(3000)
                .titleColor(R.color.primarylight)
                .messageColor(R.color.primarylight)
                .titleTypeface(bold)
                .messageTypeface(regular)
                .titleSizeInSp(14)
                .messageSizeInSp(12)
                .iconAnimation(flashAnimIconBuilder.pulse())
                .showIcon()
                .build()
                .show();
    }

    public void failure(String message){
        Typeface bold = Typeface.createFromAsset(
                context.getAssets(), "bold.ttf");
        Typeface regular = Typeface.createFromAsset(
                context.getAssets(), "regular.ttf");
        FlashAnimIconBuilder flashAnimIconBuilder = new FlashAnimIconBuilder(context);
        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .title("FAILED")
                .message(message)
                .backgroundColorRes(R.color.error_background)
                .castShadow(true, 50)
                .icon(R.drawable.error)
                .iconColorFilter(R.color.primarylight)
                .duration(3000)
                .titleColor(R.color.primarylight)
                .messageColor(R.color.primarylight)
                .titleTypeface(bold)
                .messageTypeface(regular)
                .titleSizeInSp(14)
                .messageSizeInSp(12)
                .iconAnimation(flashAnimIconBuilder.pulse())
                .showIcon()
                .build()
                .show();
    }

    public void timeout(String message){
        Typeface bold = Typeface.createFromAsset(
                context.getAssets(), "bold.ttf");
        Typeface regular = Typeface.createFromAsset(
                context.getAssets(), "regular.ttf");
        FlashAnimIconBuilder flashAnimIconBuilder = new FlashAnimIconBuilder(context);
        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .title("INFO")
                .message(message)
                .backgroundColorRes(R.color.info_background)
                .castShadow(true, 50)
                .icon(R.drawable.error)
                .iconColorFilter(R.color.primarylight)
                .duration(3000)
                .titleColor(R.color.primarylight)
                .messageColor(R.color.primarylight)
                .titleTypeface(bold)
                .messageTypeface(regular)
                .titleSizeInSp(14)
                .messageSizeInSp(12)
                .iconAnimation(flashAnimIconBuilder.pulse())
                .showIcon()
                .build()
                .show();
    }

}
