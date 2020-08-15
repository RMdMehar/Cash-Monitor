package com.example.cashmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cashmonitor.model.Record;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecordActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final String LOG_TAG = RecordActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        final EditText txnLabel = findViewById(R.id.edit_text_txn_label);

        final EditText txnAmt = findViewById(R.id.edit_text_txn_amt);

        Button recordButton = findViewById(R.id.button_record);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String label = txnLabel.getText().toString();
                int amount = Integer.parseInt(txnAmt.getText().toString());
                String userId = FirebaseAuth.getInstance().getUid();
                String path = "users/" + userId + "/records";
                CollectionReference records = firebaseFirestore.collection(path);
                Log.v(LOG_TAG, "Referencing subcollection: " + path);
                records.add(new Record(label, amount))
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RecordActivity.this, "Success", Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }
                        });
            }
        });
    }
}