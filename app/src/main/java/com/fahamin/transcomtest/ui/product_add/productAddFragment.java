package com.fahamin.transcomtest.ui.product_add;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fahamin.transcomtest.MainActivity;
import com.fahamin.transcomtest.R;
import com.fahamin.transcomtest.database.DatabaseHelper;
import com.fahamin.transcomtest.model.DataModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class productAddFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    EditText nameEdit;
    EditText priceEdit;
    EditText quantityEdit;
    EditText supplierNameEdit;
    EditText supplierPhoneEdit;
    ImageButton decreaseQuantity;
    ImageButton increaseQuantity;
    Button imageBtn, saveBtn;
    ImageView imageView;
    Uri actualUri ;
    String times, date, productName, quantity, price, supplierName, supplierPhone;
    Calendar calendar;
    private static final int PICK_IMAGE_REQUEST = 0;
    private static final int MY_PERMISSIONS_REQUEST = 5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new GalleryViewModel(getContext());
        View root = inflater.inflate(R.layout.fragment_product_add, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEdit = view.findViewById(R.id.product_name_edit);
        priceEdit = view.findViewById(R.id.price_edit);
        quantityEdit = view.findViewById(R.id.quantity_edit);
        supplierNameEdit = view.findViewById(R.id.supplier_name_edit);
        supplierPhoneEdit = view.findViewById(R.id.supplier_phone_edit);
        decreaseQuantity = view.findViewById(R.id.decrease_quantity);
        increaseQuantity = view.findViewById(R.id.increase_quantity);
        imageBtn = view.findViewById(R.id.select_image);
        imageView = view.findViewById(R.id.image_view);
        saveBtn = view.findViewById(R.id.saveBtnID);

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

                if (galleryViewModel.haveStoragePermission()) {
                    tryToOpenImageSelector();

                }

            }
        });
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
            diaryModel.setId(0);
            diaryModel.setproduct_name(productName);
            diaryModel.settotal_quantity(quantity);
            diaryModel.setPrice_rate(price);
            diaryModel.setDate(date);
            diaryModel.setTime(times);
            diaryModel.setSupplierName(supplierName);
            diaryModel.setSupplierPhone(supplierPhone);

            if(actualUri == null)
            {
                String images = "hello";
                diaryModel.setProduct_image(images);
            }
            else  {
                diaryModel.setProduct_image(actualUri.toString());
            }

            DatabaseHelper database = new DatabaseHelper(getContext());
            database.insertelement(diaryModel);

            Toast.makeText(getContext(), "DataSave", Toast.LENGTH_SHORT).show();
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
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
        } */else {

            return true;
        }
    }
}