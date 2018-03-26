package com.matija.spendless.ui.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matija.spendless.Application;
import com.matija.spendless.R;
import com.matija.spendless.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * Created by matija on 23.3.18..
 */

public class TransactionAdapter extends PagedListAdapter<Transaction, TransactionViewHolder> {

    public TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Application.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Transaction transaction = getItem(position);
        if (transaction != null) {
            holder.bindTransaction(transaction);
        }
    }

    public static final DiffCallback<Transaction> DIFF_CALLBACK = new DiffCallback<Transaction>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldTransaction, @NonNull Transaction newTransaction) {
            return oldTransaction.getId() == newTransaction.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldTransaction, @NonNull Transaction newTransaction) {
            return Objects.equals(oldTransaction.getDateTime(), newTransaction.getDateTime())
                    && Objects.equals(oldTransaction.getValue(), newTransaction.getValue())
                    && Objects.equals(oldTransaction.getCategoryId(), newTransaction.getCategoryId());
        }
    };

}
    class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView valueTextView;
        private TextView dateTextView;
        private TextView descriptionTextView;

        private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        public TransactionViewHolder(View itemView) {
            super(itemView);
            valueTextView = itemView.findViewById(R.id.transaction_item_transaction_value_text_view);
            dateTextView = itemView.findViewById(R.id.transaction_item_transaction_date_text_view);
            descriptionTextView = itemView.findViewById(R.id.transaction_item_transaction_description_text_view);
        }

        public void bindTransaction(Transaction transaction) {
            valueTextView.setText(Float.toString(transaction.getValue()));
            dateTextView.setText(sdf.format(transaction.getDateTime()));
        }


    }
