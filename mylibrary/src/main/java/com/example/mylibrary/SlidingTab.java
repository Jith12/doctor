package com.example.mylibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlidingTab extends RecyclerView{

    private int backgroundColor = Color.parseColor("#D6D7D7");
    public static int selected_text_color = Color.parseColor("#D6D7D7");
    public static int unselected_text_color = Color.parseColor("#D6D7D7");
    public static int selected_inidcator_color = Color.parseColor("#D6D7D7");
    public static int unselected_inidcator_color = Color.TRANSPARENT;
    public static TabClickListener tabClickListener;

    LinearLayoutManager layoutManager1;
    public static TabAdapter2 tabAdapter;
    private List<String> tabList;
    private List<Fragment> fragmentList;
    private Context ctx;
    private View view;
    private int itemClicked=0;

    public SlidingTab(@NonNull Context context) {
        super(context);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public SlidingTab(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        processAttributes(context,attrs);
    }

    public SlidingTab(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        processAttributes(context,attrs);
    }

    public static void loadTabTitles(List<String> tab_title,TabClickListener tabClickListener){
        tabAdapter.loadTabTitles(tab_title,tabClickListener);
    }

    private void processAttributes(final Context context, final AttributeSet attrs) {
        layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        this.setHasFixedSize(true);
        this.setNestedScrollingEnabled(true);
        this.setLayoutManager(layoutManager1);

        tabAdapter = new TabAdapter2(context, tabList);
        this.setAdapter(tabAdapter);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            initDefaultAttributes(context,attrs);
        else
            initDefaultAttributes17(context,attrs);

        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.Slidingtablayoute, 0, 0);
        initAttributes(attrsArray);
        attrsArray.recycle();
    }

    private void initDefaultAttributes(final Context context,AttributeSet attrs) {
        int[] defAttr = new int[]{
                android.R.attr.gravity,
                android.R.attr.padding,
                android.R.attr.paddingLeft,
                android.R.attr.paddingTop,
                android.R.attr.paddingRight,
                android.R.attr.paddingBottom
        };

        TypedArray defAttrsArray = context.obtainStyledAttributes(attrs, defAttr);
        defAttrsArray.recycle();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initDefaultAttributes17(final Context context,AttributeSet attrs) {
        int[] defAttr = new int[]{
                android.R.attr.gravity,
                android.R.attr.padding,
                android.R.attr.paddingLeft,
                android.R.attr.paddingTop,
                android.R.attr.paddingRight,
                android.R.attr.paddingBottom,
                android.R.attr.paddingStart,
                android.R.attr.paddingEnd
        };

        TypedArray defAttrsArray = context.obtainStyledAttributes(attrs, defAttr);
        defAttrsArray.recycle();

    }

    private void initAttributes(TypedArray attrs) {
        backgroundColor = attrs.getColor(R.styleable.Slidingtablayoute_backgroundColor,Color.WHITE);
        selected_text_color = attrs.getColor(R.styleable.Slidingtablayoute_tab_selected_text_color,selected_text_color);
        unselected_text_color = attrs.getColor(R.styleable.Slidingtablayoute_tab_unselected_text_color,unselected_text_color);
        selected_inidcator_color = attrs.getColor(R.styleable.Slidingtablayoute_tab_selected_inidcator_color,selected_inidcator_color);
        unselected_inidcator_color = attrs.getColor(R.styleable.Slidingtablayoute_tab_unselected_inidcator_color,unselected_inidcator_color);
        setupBackground();
    }

    private void setupBackground() {


        // Default Drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setColor(backgroundColor);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setBackground(getRippleDrawable(defaultDrawable));

        } else {

            StateListDrawable states = new StateListDrawable();

            states.addState(new int[]{}, defaultDrawable);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states);
            } else {
                this.setBackground(states);
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable disabledDrawable) {
        if (!isEnabled()) {
            return disabledDrawable;
        } else {
            return disabledDrawable;
        }

    }
}