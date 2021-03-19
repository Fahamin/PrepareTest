package com.fahamin.transcomtest.ui.product_Table;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fahamin.transcomtest.R;
import com.fahamin.transcomtest.database.DatabaseHelper;
import com.fahamin.transcomtest.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ProductTableFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    DatabaseHelper diaryDatabase;
    TableLayout tableLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product_table, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        diaryDatabase = new DatabaseHelper(getContext());
        tableLayout = view.findViewById(R.id.tableLayoutProduct);
        List<DataModel> prouctList = new ArrayList<>();
        prouctList = diaryDatabase.getAllitem();
        createColumns();
        datasetTable(prouctList);
    }

    private void datasetTable(List<DataModel> prouctList) {
        for(DataModel product : prouctList)
        {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow currentRow = (TableRow) view;
                    TextView textViewId = (TextView) currentRow.getChildAt(0);
                    String id = textViewId.getText().toString();
                    Toast.makeText(getContext(), id, Toast.LENGTH_LONG).show();
                }
            });

            // date Column
            TextView textViewId = new TextView(getContext());
            textViewId.setText(product.getDate());
            textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewId.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewId);

            // Name Column
            TextView textViewName = new TextView(getContext());
            textViewName.setText(product.getproduct_name());
            textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewName.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewName);

            // Price Column
            TextView textViewPrice = new TextView(getContext());
            textViewPrice.setText(product.getPrice_rate());
            textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewPrice.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewPrice);

            // quantity Column
            TextView imageViewPhoto = new TextView(getContext());
            imageViewPhoto.setText(product.gettotal_quantity());
            tableRow.addView(imageViewPhoto);

            // suppilr Column
            TextView textsupplier = new TextView(getContext());
            textsupplier.setText(product.getSupplierName());
            tableRow.addView(textsupplier);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
        
    }

    private void createColumns() {
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

       //date
        TextView textViewId = new TextView(getContext());
        textViewId.setText("Date");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewId);

        // Name Column
        TextView textViewName = new TextView(getContext());
        textViewName.setText("Name");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        // Price Column
        TextView textViewPrice = new TextView(getContext());
        textViewPrice.setText("Price");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPrice);

        // quantity Column
        TextView textViewPhoto = new TextView(getContext());
        textViewPhoto.setText("Quantity");
        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPhoto.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPhoto);

        // supplier
        TextView textsupplier = new TextView(getContext());
        textsupplier.setText("Supplier Name");
        textsupplier.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textsupplier.setPadding(5, 5, 5, 0);
        tableRow.addView(textsupplier);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Add Divider
        tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // date Column
        textViewId = new TextView(getContext());
        textViewId.setText("------------");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewId);

        // Name Column
        textViewName = new TextView(getContext());
        textViewName.setText("------------");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        // Price Column
        textViewPrice = new TextView(getContext());
        textViewPrice.setText("---------");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPrice);

        // Photo Column
        textViewPhoto = new TextView(getContext());
        textViewPhoto.setText("----------");
        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPhoto.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewPhoto);

        // quantity Column
        textsupplier = new TextView(getContext());
        textsupplier.setText("-----------------");
        textsupplier.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textsupplier.setPadding(5, 5, 5, 0);
        tableRow.addView(textsupplier);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

    }


}