package com.fahamin.transcomtest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.fahamin.transcomtest.database.DatabaseHelper;
import com.fahamin.transcomtest.database.DatabaseSalesHelper;
import com.fahamin.transcomtest.model.DataModel;
import com.fahamin.transcomtest.ui.product_add.GalleryViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SalesActivity extends AppCompatActivity {

    EditText nameEdit;
    EditText priceEdit;
    EditText quantityEdit;
    EditText supplierNameEdit;
    EditText supplierPhoneEdit;
    ImageButton decreaseQuantity;
    ImageButton increaseQuantity;
    Button imageBtn, saveBtn;
    ImageView imageView;
    Uri actualUri;
    String times, date, productName, quantity, price, supplierName, supplierPhone;
    Calendar calendar;
    private static final int PICK_IMAGE_REQUEST = 0;
    private static final int MY_PERMISSIONS_REQUEST = 5;
    int id, flag;
    DatabaseHelper database;
    DataModel diaryModel;
    String ss  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        database = new DatabaseHelper(this);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        flag = intent.getIntExtra("flag", -1);

        diaryModel = new DataModel();

        diaryModel = database.selectWithId(String.valueOf(id));


        init();
        setData(diaryModel);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.sale_dialog);
        EditText et = dialog.findViewById(R.id.downloadEdt);
        Button bt = dialog.findViewById(R.id.downloadUrlBtn);
        et.setText("1");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ps = diaryModel.gettotal_quantity();

                 ss = et.getText().toString();

                int salesvalue = Integer.parseInt(ss);
                int pss = Integer.parseInt(ps);
                quantityEdit.setText(String.valueOf(pss - salesvalue));
                saveData();
                dialog.dismiss();

            }
        });
        dialog.show();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtractOneToQuantity();

            }
        });

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumOneToQuantity();

            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GalleryViewModel viewModel = new GalleryViewModel(SalesActivity.this);
                if (viewModel.haveStoragePermission()) {
                    tryToOpenImageSelector();

                }

            }
        });
    }

    private void init() {
        nameEdit = findViewById(R.id.product_name_edit);
        priceEdit = findViewById(R.id.price_edit);
        quantityEdit = findViewById(R.id.quantity_edit);
        supplierNameEdit = findViewById(R.id.supplier_name_edit);
        supplierPhoneEdit = findViewById(R.id.supplier_phone_edit);
        decreaseQuantity = findViewById(R.id.decrease_quantity);
        increaseQuantity = findViewById(R.id.increase_quantity);
        imageBtn = findViewById(R.id.select_image);
        imageView = findViewById(R.id.image_view);
        saveBtn = findViewById(R.id.saveBtnID);
    }

    private void setData(DataModel diaryModel) {

        nameEdit.setText(diaryModel.getproduct_name());
        priceEdit.setText(diaryModel.getPrice_rate());

        supplierNameEdit.setText(diaryModel.getSupplierName());
        supplierPhoneEdit.setText(diaryModel.getSupplierPhone());


    }

    private void saveData() {

        if (checkValidity()) {

            Calendar cal = Calendar.getInstance();
            Date chosenDate = cal.getTime();

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            date = dateFormat.format(chosenDate);

            calendar = Calendar.getInstance();

            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
            String strTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

            Date time = null;
            try {
                time = sdf24.parse(strTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            times = sdf12.format(time);

            productName = nameEdit.getText().toString();
            quantity = quantityEdit.getText().toString();
            price = priceEdit.getText().toString();
            supplierName = supplierNameEdit.getText().toString();
            supplierPhone = supplierPhoneEdit.getText().toString();


            DataModel diaryModel = new DataModel();
            diaryModel.setId(id);
            diaryModel.setproduct_name(productName);
            diaryModel.settotal_quantity(quantity);
            diaryModel.setPrice_rate(price);
            diaryModel.setDate(date);
            diaryModel.setTime(times);
            diaryModel.setSupplierName(supplierName);
            diaryModel.setSupplierPhone(supplierPhone);


            if (actualUri == null) {
                String images = "hello";
                diaryModel.setProduct_image(images);
            } else {
                diaryModel.setProduct_image(actualUri.toString());
            }

            //main data add
            database.editelement(diaryModel);

            //sales data add
            DatabaseSalesHelper salesHelper = new DatabaseSalesHelper(this);

            diaryModel.settotal_quantity(ss);
            salesHelper.insertelement(diaryModel);
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                            .setContentTitle(diaryModel.getproduct_name())
                            .setContentText("This Product sales now");

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

            finish();
        }

    }

    private void subtractOneToQuantity() {
        String previousValueString = quantityEdit.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            return;
        } else if (previousValueString.equals("0")) {
            return;
        } else {
            previousValue = Integer.parseInt(previousValueString);
            quantityEdit.setText(String.valueOf(previousValue - 1));
        }
    }

    public void tryToOpenImageSelector() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
            return;
        }
        openImageSelector();
    }

    private void sumOneToQuantity() {
        String previousValueString = quantityEdit.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            previousValue = 0;
        } else {
            previousValue = Integer.parseInt(previousValueString);
        }
        quantityEdit.setText(String.valueOf(previousValue + 1));
    }

    private void openImageSelector() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImageSelector();
                    // permission was granted
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.

        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"

            if (resultData != null) {
                actualUri = resultData.getData();
                imageView.setImageURI(actualUri);
                imageView.invalidate();
            }
        }


    }


    private boolean checkValidity() {

        if (nameEdit.getText().toString().equals("")) {

            nameEdit.setError("This field is required !!!");
            return false;

        } else if (priceEdit.getText().toString().equals("")) {

            priceEdit.setError("This field is required !!!");
            return false;

        } /*else if (actualUri == null) {
            imageBtn.setError("add image");
            imageView.setImageResource(R.drawable.pepsi);
            Toast.makeText(getContext(), "Please Add Image", Toast.LENGTH_SHORT).show();

            return false;
        } */ else {

            return true;
        }
    }
}