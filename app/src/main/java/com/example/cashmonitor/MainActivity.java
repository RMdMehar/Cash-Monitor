package com.example.cashmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cashmonitor.utlilties.RecordAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMainRecyclerView;
    private RecordAdapter mAdapter;

    private Query mQuery;

    TextView mEmptyView;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainRecyclerView = findViewById(R.id.main_recycler_view);
        mEmptyView = findViewById(R.id.empty_view);

        initFirestore();
        initRecyclerView();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecordActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    private void initFirestore() {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getUid();
        String path = "collections/" + userId + "/records";
        mQuery = mFirestore.collection(path);
    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.v(LOG_TAG, "Custom Log: No query, not initializing RecyclerView");
        } else {
            Log.v(LOG_TAG, "Custom Log: Fetching");
            mAdapter = new RecordAdapter(mQuery) {
                @Override
                protected void onDataChanged() {
                    if (getItemCount() == 0) {
                        mMainRecyclerView.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        mMainRecyclerView.setVisibility(View.VISIBLE);
                        mEmptyView.setVisibility(View.GONE);
                    }
                }

                @Override
                protected void onError(FirebaseFirestoreException e) {
                    Snackbar.make(findViewById(android.R.id.content), "Error: check logs for info", Snackbar.LENGTH_LONG)
                            .show();
                }
            };
        }

        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainRecyclerView.setAdapter(mAdapter);
    }
}