package com.example.metaphraseinvoice.InputActivitites.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.metaphraseinvoice.InputActivitites.Model.DezimalModel;
import com.example.metaphraseinvoice.ItemAdapter;
import com.example.metaphraseinvoice.ItemModel;
import com.example.metaphraseinvoice.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class DezimalItemAdapter extends RecyclerView.Adapter<DezimalItemAdapter.Placeholder> {
    Context context;
    ArrayList<DezimalModel> dezimalModelArrayList;

    public DezimalItemAdapter(Context context, ArrayList<DezimalModel> dezimalModelArrayList) {
        this.context = context;
        this.dezimalModelArrayList = dezimalModelArrayList;
    }



    @Override
    public Placeholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_dezimal_layout,parent,false);
        return new Placeholder(view);
    }

    @Override
    public void onBindViewHolder(final Placeholder holder, final int position) {
        final DezimalModel item=dezimalModelArrayList.get(position);
        holder.tv_description_dezimal.setText(item.getDescription());
        holder.tv_hour.setText(item.getHours());
        holder.tv_minute.setText(item.getMinutes());
        holder.tv_price_dezimal.setText(item.getPrice());

        holder.btn_edit_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View deleteDialogView = factory.inflate(R.layout.dialog_item_dezimal, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setView(deleteDialogView);
                final EditText et_desc_dezimal=deleteDialogView.findViewById(R.id.et_desc_dezimal);
                final EditText et_hour_dezimal=deleteDialogView.findViewById(R.id.et_hour_dezimal);
                final EditText et_minute_dezimal=deleteDialogView.findViewById(R.id.et_minute_dezimal);
                final EditText et_price_dezimal=deleteDialogView.findViewById(R.id.et_price_dezimal);
                final EditText et_advance_payments_dezimal=deleteDialogView.findViewById(R.id.et_advance_payments_dezimal);

                et_desc_dezimal.setText(item.getDescription());
                et_hour_dezimal.setText(item.getHours());
                et_minute_dezimal.setText(item.getMinutes());
                et_price_dezimal.setText(item.getPrice());

                Button btn_update_item_dezimal=deleteDialogView.findViewById(R.id.btn_update_item_dezimal);
                Button btn_cancel_dezimal=deleteDialogView.findViewById(R.id.btn_cancel_dezimal);

                btn_update_item_dezimal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(et_desc_dezimal.getText().toString().isEmpty()){
                            et_desc_dezimal.setText("\n" + "Beschreibung erforderlich!");
                        }
                        else if(et_hour_dezimal.getText().toString().isEmpty()){
                            et_hour_dezimal.setText("hour required!");
                        }
                        else if(et_minute_dezimal.getText().toString().isEmpty()){
                            et_minute_dezimal.setText("minutes required!");
                        }
                        else if(et_price_dezimal.getText().toString().isEmpty()){
                            et_price_dezimal.setText("\n" + "Preis erforderlich!");
                        }
                        else{
                            updateItem();
                        }
                    }

                    private void updateItem() {
                        dezimalModelArrayList.remove(position);
                        item.setDescription(et_desc_dezimal.getText().toString());
                        item.setHours(et_hour_dezimal.getText().toString());
                        item.setMinutes(et_minute_dezimal.getText().toString());
                        item.setPrice(et_price_dezimal.getText().toString());
                        dezimalModelArrayList.add(position,item);
                        notifyItemChanged(position);
                        deleteDialog.dismiss();
                    }
                });
                btn_cancel_dezimal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dezimalModelArrayList.size();
    }

    public class Placeholder extends RecyclerView.ViewHolder {
        TextView tv_description_dezimal,tv_price_dezimal,tv_advancePayment_dezimal,tv_minute,tv_hour;
        Button btn_edit_dezimal;

        public Placeholder(View itemView) {
            super(itemView);
            tv_hour=itemView.findViewById(R.id.tv_hours);
            tv_minute=itemView.findViewById(R.id.tv_minute);
            tv_description_dezimal=itemView.findViewById(R.id.tv_desc_dezimal);
            tv_price_dezimal=itemView.findViewById(R.id.tv_price_dezimal);
            tv_advancePayment_dezimal=itemView.findViewById(R.id.tv_advancePayment_dezimal);
            btn_edit_dezimal=itemView.findViewById(R.id.btn_edit_dezimal);

        }
    }

}
