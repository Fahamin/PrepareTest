package com.fahamin.transcomtest.dialog;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fahamin.transcomtest.R;

import java.io.File;


public class SalesDialog {

    private static final String BOOK_STORE_PATH = "file";
    AppCompatActivity activity;

    public void showDialog(final AppCompatActivity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.sale_dialog);

        Button urlBtn;
        final EditText urlEdt;

        urlBtn = dialog.findViewById(R.id.downloadUrlBtn);
        urlEdt = dialog.findViewById(R.id.downloadEdt);


        urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
