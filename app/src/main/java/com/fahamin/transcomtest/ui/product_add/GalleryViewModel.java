package com.fahamin.transcomtest.ui.product_add;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    Context context;

    public GalleryViewModel(Context context) {
        this.context = context;
    }

    public LiveData<String> getText() {
        return mText;
    }


    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //  Log.e("Permission error", "You have permission");
                return true;
            } else {

                //  Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
                Toast.makeText(context, "Need to Permission for add file", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            //  Log.e("Permission error", "You already have the permission");
            return true;
        }
    }

}