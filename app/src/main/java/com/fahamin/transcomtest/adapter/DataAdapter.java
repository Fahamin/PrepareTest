package com.fahamin.transcomtest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fahamin.transcomtest.R;
import com.fahamin.transcomtest.database.DatabaseHelper;
import com.fahamin.transcomtest.model.DataModel;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context context;
    private List<DataModel> dataModelList;
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    String serchKey;
    private TextView messageTV;
    private Activity activity;

    public static final int REQUEST_CODE = 55;

    int counter = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTv, descriptionTV, datetv, timeTV, rateTV;
        public CardView fullchildCV;
        public LinearLayout addtioonalLayout, viewDetailLayout, editLayout, deletLayout;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rateTV = itemView.findViewById(R.id.rate_TV);
            titleTv = itemView.findViewById(R.id.title_TV);
            descriptionTV = itemView.findViewById(R.id.description_TV);
            datetv = (TextView) itemView.findViewById(R.id.date_TV);
            timeTV = (TextView) itemView.findViewById(R.id.time_TV);
            imageView = itemView.findViewById(R.id.product_imageID);
            fullchildCV = (CardView) itemView.findViewById(R.id.fullChildCV);

            addtioonalLayout = (LinearLayout) itemView.findViewById(R.id.additional_LAYOUT);
            viewDetailLayout = (LinearLayout) itemView.findViewById(R.id.view_details_LAYOUT);
            editLayout = (LinearLayout) itemView.findViewById(R.id.edit_LAYOUT);
            deletLayout = (LinearLayout) itemView.findViewById(R.id.delete_LAYOUT);

        }
    }

    public DataAdapter(Context context, List<DataModel> dataModelList, RecyclerView recyclerView, DataAdapter adapter, String serchKey, TextView messageTV, Activity activity) {
        this.context = context;
        this.dataModelList = dataModelList;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.serchKey = serchKey;
        this.messageTV = messageTV;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate
                (R.layout.view_item_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final DataModel dataModel = dataModelList.get(i);
        myViewHolder.titleTv.setText(dataModel.getproduct_name());
        myViewHolder.descriptionTV.setText(dataModel.gettotal_quantity());
        myViewHolder.datetv.setText(dataModel.getDate());
        myViewHolder.rateTV.setText(dataModel.getPrice_rate());
        myViewHolder.timeTV.setText(dataModel.getTime());
        myViewHolder.imageView.setImageURI(Uri.parse(dataModel.getProduct_image()));

        myViewHolder.fullchildCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if (counter % 2 == 1) {
                    myViewHolder.addtioonalLayout.setVisibility(View.VISIBLE);
                } else
                    myViewHolder.addtioonalLayout.setVisibility(View.GONE);
            }
        });
        myViewHolder.fullchildCV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                counter++;

                if (counter % 2 == 1) {

                    myViewHolder.addtioonalLayout.setVisibility(View.VISIBLE);

                } else if (counter % 2 == 0) {

                    myViewHolder.addtioonalLayout.setVisibility(View.GONE);
                }


                return false;
            }
        });

/*
        myViewHolder.viewDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Details.class);
                intent.putExtra("id", dataModel.getId());
                context.startActivity(intent);

            }
        });


        myViewHolder.editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Edit.class);
                intent.putExtra("id", dataModel.getId());
                context.startActivity(intent);

            }
        });*/


        myViewHolder.deletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Continue with deletion")
                        .setMessage("Are you sure you want to really delete this data?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                deleteFile(dataModel, recyclerView, adapter, context);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
    }

    private void deleteFile(DataModel dataModel, RecyclerView recyclerView, DataAdapter adapter, Context context) {

        DatabaseHelper helper = new DatabaseHelper(context);
        helper.deletelement(dataModel);

        List<DataModel> dataModelList = new ArrayList<>();
        dataModelList = helper.getAllitem();

        DataAdapter adapters = new DataAdapter(context, dataModelList, recyclerView, adapter, "", messageTV, activity);

        adapters.notifyDataSetChanged();
        recyclerView.setAdapter(adapters);
        adapters.notifyDataSetChanged();

        if (dataModelList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            messageTV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }


}
