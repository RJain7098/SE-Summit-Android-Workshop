package com.summit.summitproject.prebuilt.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.summit.summitproject.R;

import java.util.List;

/**
 * A {@link RecyclerView.Adapter} is used with a {@link RecyclerView}. It takes in the data which
 * should be displayed in the list and tells the UI how each individual piece of data should be
 * rendered.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Called when the UI needs the a new row (at {position}) to be <b>created</b>.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_transaction, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called when the UI needs the next row (at {position}) to be <b>filled with data</b> rendered
     * and passes the {@link ViewHolder} which should be filled with data.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Transaction current = transactions.get(position);
        holder.merchant.setText(current.getMerchant());
        holder.amount.setText(current.getAmount());
    }

    /**
     * Used to determine how many rows the list should be in total.
     */
    @Override
    public int getItemCount() {
        return transactions.size();
    }

    /**
     * Holds the UI widgets which will comprise a single row in the list (to render
     * a {@link Transaction}).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView merchant;

        TextView amount;

        ViewHolder(View rootView) {
            super(rootView);
            cardView = rootView.findViewById(R.id.card_container);
            merchant = rootView.findViewById(R.id.merchant);
            amount = rootView.findViewById(R.id.amount);
        }
    }
}
