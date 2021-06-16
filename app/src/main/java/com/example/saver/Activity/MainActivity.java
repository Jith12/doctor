package com.example.saver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mylibrary.Slidingtablayoute;
import com.example.saver.Fragment.AppointmentFragment;
import com.example.saver.Fragment.BookingFragment;
import com.example.saver.Fragment.CalendarFragment;
import com.example.saver.Fragment.DashboardFragment;
import com.example.saver.Fragment.ScheduleFragment;
import com.example.saver.Fragment.MessagesFragment;
import com.example.saver.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pixplicity.easyprefs.library.Prefs;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Slidingtablayoute rvTab;
    private List<String> tabList;
    private List<Fragment> tabFragment;
    private ImageView logout, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = Prefs.getString("e_name", null);

        rvTab = findViewById(R.id.rv_tab);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);

        tabList = new ArrayList<>();
        tabFragment = new ArrayList<>();
        tabList.add(getString(R.string.dash_board).toUpperCase());
        tabList.add(getString(R.string.schedule).toUpperCase());
        tabList.add(getString(R.string.booking).toUpperCase());
        tabList.add(getString(R.string.calendar).toUpperCase());
        tabList.add(getString(R.string.messages).toUpperCase());

        tabFragment.add(new DashboardFragment());
        tabFragment.add(new ScheduleFragment());
        tabFragment.add(new BookingFragment());
        tabFragment.add(new CalendarFragment());
        tabFragment.add(new MessagesFragment());

        rvTab.loadTabTitles(tabList, MainActivity.this);
        rvTab.loadFragment(tabFragment, MainActivity.this);

        logout.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .setTitle("LOGOUT")
                        .setMessage("Are you Sure ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i1) -> {
                            dialogInterface.dismiss();
                            Prefs.clear();
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Animatoo.animateFade(this);
                            finish();
                        })
                        .setNegativeButton("No", (dialogInterface, which) -> {
                            dialogInterface.dismiss();
                        })
                        .build();
                materialDialog.show();
            } else {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this)
                        .setTitle("LOGOUT")
                        .setMessage("Are you Sure ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i1) -> {
                            dialogInterface.dismiss();
                            Prefs.clear();
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Animatoo.animateFade(this);
                            finish();
                        })
                        .setNegativeButton("No", (dialogInterface, i1) -> {
                            dialogInterface.dismiss();
                        });
                dialogBuilder.show();
            }
        });

        profile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            Animatoo.animateFade(this);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}