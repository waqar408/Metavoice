package com.example.metaphraseinvoice;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by hp on 3/24/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Placeholder> {
    Context context;
    ArrayList<ItemModel> itemModelArrayList;

    public ItemAdapter(Context context, ArrayList<ItemModel> itemModelArrayList) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
    }
    @Override
    public Placeholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_item,parent,false);
        return new Placeholder(view);
    }

    @Override
    public void onBindViewHolder(final Placeholder holder, final int position) {
        final ItemModel item=itemModelArrayList.get(position);
        holder.tv_description.setText(item.getDescription());
        holder.tv_quantity.setText(item.getQuantity());
        holder.tv_price.setText(item.getPrice());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View deleteDialogView = factory.inflate(R.layout.dialog_edit_item, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setView(deleteDialogView);
                final EditText et_desc=deleteDialogView.findViewById(R.id.et_desc);
                final EditText et_quantity=deleteDialogView.findViewById(R.id.et_quantity);
                final EditText et_price=deleteDialogView.findViewById(R.id.et_price);

                et_desc.setText(item.getDescription());
                et_quantity.setText(item.getQuantity());
                et_price.setText(item.getPrice());

                Button btn_update_item=deleteDialogView.findViewById(R.id.btn_update_item);
                Button btn_cancel=deleteDialogView.findViewById(R.id.btn_cancel);

                btn_update_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(et_desc.getText().toString().isEmpty()){
                            et_desc.setText("\n" + "Beschreibung erforderlich!");
                        }
                        else if(et_quantity.getText().toString().isEmpty()){
                            et_quantity.setText("\n" + "Benoetigte Menge!");
                        }
                        else if(et_price.getText().toString().isEmpty()){
                            et_price.setText("\n" + "Preis erforderlich!");
                        }
                       /* else if(et_advance_payments.getText().toString().isEmpty()){
                            et_advance_payments.setText("advance payment required!");
                        }*/
                        else{
                            updateItem();
                        }
                    }

                    private void updateItem() {
                        itemModelArrayList.remove(position);
                        item.setDescription(et_desc.getText().toString());
                        item.setQuantity(et_quantity.getText().toString());
                        item.setPrice(et_price.getText().toString());
                       // item.setAdvancePayment(et_advance_payments.getText().toString());
                        itemModelArrayList.add(position,item);
                        notifyItemChanged(position);
                        deleteDialog.dismiss();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
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
        return itemModelArrayList.size();
    }

    public static class Placeholder extends RecyclerView.ViewHolder {
        TextView tv_quantity,tv_description,tv_price,tv_advance_payment;
        Button btn_edit;

        public Placeholder(View itemView) {
            super(itemView);
            tv_quantity=itemView.findViewById(R.id.tv_quantity);
            tv_description=itemView.findViewById(R.id.tv_desc);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_advance_payment=itemView.findViewById(R.id.tv_advance_payment);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            btn_edit=itemView.findViewById(R.id.btn_edit);

        }
    }

}
