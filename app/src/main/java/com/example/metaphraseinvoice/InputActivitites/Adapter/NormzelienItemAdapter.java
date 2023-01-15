package com.example.metaphraseinvoice.InputActivitites.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.metaphraseinvoice.InputActivitites.Model.DezimalModel;
import com.example.metaphraseinvoice.InputActivitites.Model.NormezelienModel;
import com.example.metaphraseinvoice.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class NormzelienItemAdapter extends RecyclerView.Adapter<NormzelienItemAdapter.Placeholder> {
    Context context;
    ArrayList<NormezelienModel> normezelienModelArrayList;

    public NormzelienItemAdapter(Context context, ArrayList<NormezelienModel> normezelienModelArrayList) {
        this.context = context;
        this.normezelienModelArrayList = normezelienModelArrayList;
    }



    @Override
    public Placeholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_normezelen_layout,parent,false);
        return new Placeholder(view);
    }

    @Override
    public void onBindViewHolder(final Placeholder holder, final int position) {
        final NormezelienModel item=normezelienModelArrayList.get(position);
        holder.tv_description_norm.setText(item.getDescription());
        holder.tv_word.setText(item.getWords());
        holder.tv_price_norm.setText(item.getPrice());

        holder.btn_edit_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View deleteDialogView = factory.inflate(R.layout.dialog_item_norm, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setView(deleteDialogView);
                final EditText et_desc_norm=deleteDialogView.findViewById(R.id.et_desc_norm);
                final EditText et_word_norm=deleteDialogView.findViewById(R.id.et_word_norm);
                final EditText et_price_norm=deleteDialogView.findViewById(R.id.et_price_norm);
                //final EditText et_advance_payments_norm=deleteDialogView.findViewById(R.id.et_advance_payments_norm);

                et_desc_norm.setText(item.getDescription());
                et_word_norm.setText(item.getWords());
                et_price_norm.setText(item.getPrice());
                //et_advance_payments_norm.setText(item.getAdvancePayment());

                Button btn_update_item_norm=deleteDialogView.findViewById(R.id.btn_update_item_norm);
                Button btn_cancel_norm=deleteDialogView.findViewById(R.id.btn_cancel_norm);

                btn_update_item_norm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(et_desc_norm.getText().toString().isEmpty()){
                            et_desc_norm.setText("\n" + "Beschreibung erforderlich!");
                        }
                        else if(et_word_norm.getText().toString().isEmpty()){
                            et_word_norm.setText("Worte erforderlich!");
                        }
                        else if(et_price_norm.getText().toString().isEmpty()){
                            et_price_norm.setText("\n" + "Preis erforderlich!");
                        }
                        /*else if(et_advance_payments_norm.getText().toString().isEmpty()){
                            et_advance_payments_norm.setText("advance payment required!");
                        }*/
                        else{
                            updateItem();
                        }
                    }

                    private void updateItem() {
                        normezelienModelArrayList.remove(position);
                        item.setDescription(et_desc_norm.getText().toString());
                        item.setWords(et_word_norm.getText().toString());
                        item.setPrice(et_price_norm.getText().toString());
                        normezelienModelArrayList.add(position,item);
                        notifyItemChanged(position);
                        deleteDialog.dismiss();
                    }
                });
                btn_cancel_norm.setOnClickListener(new View.OnClickListener() {
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
        return normezelienModelArrayList.size();
    }

    public class Placeholder extends RecyclerView.ViewHolder {
        TextView tv_description_norm,tv_price_norm,tv_advancePayment_norm,tv_word;
        Button btn_edit_norm;

        public Placeholder(View itemView) {
            super(itemView);
            tv_word=itemView.findViewById(R.id.tv_words);
            tv_description_norm=itemView.findViewById(R.id.tv_desc_norm);
            tv_price_norm=itemView.findViewById(R.id.tv_price_norm);
            tv_advancePayment_norm=itemView.findViewById(R.id.tv_advancePayment_norm);
            btn_edit_norm=itemView.findViewById(R.id.btn_edit_norm);

        }
    }

}

