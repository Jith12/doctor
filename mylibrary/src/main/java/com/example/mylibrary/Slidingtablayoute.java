package com.example.mylibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Slidingtablayoute extends LinearLayout implements TabClickListener{

    private SlidingTab rv_tab;
    private List<String> tabList;
    private List<Fragment> fragmentList;
    private Context ctx;
    public Slidingtablayoute(Context context) {
        super(context);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public Slidingtablayoute(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_lay, this, true);
    }

    public Slidingtablayoute(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Slidingtablayoute(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public void loadTabTitles(List<String> tab_title,Context ctx){
        this.tabList=tab_title;
        this.ctx=ctx;
        SlidingTab.loadTabTitles(tabList,this::onClick);
    }

    public void loadFragment(List<Fragment> tab_fragment,Context ctx){
        this.fragmentList=tab_fragment;
        Fragment fragment = fragmentList.get(0);

        ((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onClick(int id) {
        Fragment fragment = fragmentList.get(id);

        ((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}