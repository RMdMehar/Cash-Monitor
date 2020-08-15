package com.example.cashmonitor.utlilties;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cashmonitor.R;
import com.example.cashmonitor.model.Record;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecordAdapter extends FirestoreAdapter<RecordAdapter.ViewHolder> {

    public RecordAdapter(Query query) {
        super(query);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView label, amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.text_view_label);
            amount = itemView.findViewById(R.id.text_view_amount);
        }

        public void bind(final DocumentSnapshot snapshot) {
            Record record = snapshot.toObject(Record.class);
            label.setText(record.getLabel());
            amount.setText(String.valueOf(record.getAmount()));
        }
    }
}
