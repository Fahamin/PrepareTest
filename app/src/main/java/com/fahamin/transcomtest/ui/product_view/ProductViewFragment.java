package com.fahamin.transcomtest.ui.product_view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fahamin.transcomtest.R;
import com.fahamin.transcomtest.adapter.DataAdapter;
import com.fahamin.transcomtest.database.DatabaseHelper;
import com.fahamin.transcomtest.dataparser.ItemDecorate;
import com.fahamin.transcomtest.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ProductViewFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    TextView messageTV;
    List<DataModel> diaryModelList;
    DataModel diaryModel;
    DataAdapter adapter;
    DatabaseHelper diaryDatabase;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_productv_iew, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        diaryDatabase = new DatabaseHelper(getContext());

        diaryModelList = diaryDatabase.getAllitem();

        //   Toast.makeText(getContext(),  diaryModelList.get(0).getproduct_name(), Toast.LENGTH_SHORT).show();

        if (diaryModelList.size() > 0) {

            prepareForView();


        } else {

            recyclerView.setVisibility(View.GONE);
            messageTV.setVisibility(View.VISIBLE);

        }
    }

    private void init(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        messageTV = (TextView) view.findViewById(R.id.messageTV);

        diaryModelList = new ArrayList<>();
        diaryModel = new DataModel();
        diaryDatabase = new DatabaseHelper(getContext());

    }

    private void prepareForView() {

        messageTV.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        diaryModelList.clear();
        diaryModelList = diaryDatabase.getAllitem();

        adapter = new DataAdapter(getContext(), diaryModelList, recyclerView, adapter, "", messageTV, getActivity());

        RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManagerBeforeMeal);
        recyclerView.addItemDecoration(new ItemDecorate(1, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onStart() {
        super.onStart();
        diaryModelList.clear();
        diaryModelList = diaryDatabase.getAllitem();

        if (diaryModelList.size() > 0) {

            prepareForView();

        } else {

            recyclerView.setVisibility(View.GONE);
            messageTV.setVisibility(View.VISIBLE);

        }
    }


}