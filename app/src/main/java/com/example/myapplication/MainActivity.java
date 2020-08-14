package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1000;
    Button button;
    ImageView shortcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shortcut = findViewById(R.id.shortcut_image);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String packageName = preferences.getString("shortcut", null);
        try {
            shortcut.setImageDrawable(getPackageManager().getApplicationIcon(packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onImageClicked(View view) {
        ImageView imageView = (ImageView) view;
        Drawable icon = imageView.getDrawable();

        if(icon != null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String packageName = preferences.getString("shortcut", null);

            Intent intent = new Intent(getPackageManager().getLaunchIntentForPackage(packageName));
            startActivity(intent);
        }
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, AppListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            ApplicationInfo info = data.getParcelableExtra("info");
            Drawable icon = info.loadIcon(getPackageManager());

            shortcut.setImageDrawable(icon);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("shortcut", info.packageName);
            editor.apply();
        }
    }
}
