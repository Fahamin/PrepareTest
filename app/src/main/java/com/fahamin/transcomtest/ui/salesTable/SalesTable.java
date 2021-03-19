package com.fahamin.transcomtest.ui.salesTable;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fahamin.transcomtest.R;
import com.fahamin.transcomtest.database.DatabaseHelper;
import com.fahamin.transcomtest.database.DatabaseSalesHelper;
import com.fahamin.transcomtest.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class SalesTable extends Fragment {

    private SalesTableViewModel mViewModel;
    DatabaseSalesHelper salesHelper ;
    TableLayout tableLayout;

    public static SalesTable newInstance() {
        return new SalesTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sales_table_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SalesTableViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        salesHelper = new DatabaseSalesHelper(getContext());
        tableLayout = view.findViewById(R.id.tableLayoutProduct);
        List<DataModel> prouctList = new ArrayList<>();
        prouctList = salesHelper.getAllitem();
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