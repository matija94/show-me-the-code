package com.matija.spendless.ui.adapters;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matija.spendless.R;
import com.matija.spendless.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * Created by matija on 23.3.18..
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    private List<Transaction> transactions;
    private Context context;

    public TransactionAdapter(Context context) {
        this.context = context;
    }

    public void setTransactionList(final List<Transaction> transactionList) {
        if (this.transactions == null) {
            this.transactions = transactionList;
            notifyItemRangeInserted(0, transactionList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return transactions.size();
                }

                @Override
                public int getNewListSize() {
                    return transactionList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return transactions.get(oldItemPosition).getId() ==
                            transactionList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Transaction newTransaction = transactionList.get(newItemPosition);
                    Transaction oldTransaction = transactions.get(oldItemPosition);
                    return newTransaction.getId() == oldTransaction.getId()
                            && Objects.equals(newTransaction.getValue(), oldTransaction.getValue())
                            && newTransaction.getDateTime() == oldTransaction.getDateTime();
                }
            });
            transactions= transactionList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.bindTransaction(this.transactions.get(position));
    }

    @Override
    public int getItemCount() {
        return this.transactions == null ? 0 : this.transactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView valueTextView;
        private TextView dateTextView;

        private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        public TransactionViewHolder(View itemView) {
            super(itemView);
            valueTextView = itemView.findViewById(R.id.transaction_item_transaction_value_text_view);
            dateTextView = itemView.findViewById(R.id.transaction_item_transaction_date_text_view);
        }

        public void bindTransaction(Transaction transaction) {
            valueTextView.setText(Float.toString(transaction.getValue()));
            dateTextView.setText(sdf.format(transaction.getDateTime()));
        }


    }
}
