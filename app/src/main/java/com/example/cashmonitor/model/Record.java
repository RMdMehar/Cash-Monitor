package com.example.cashmonitor.model;

public class Record {
    String label;
    int amount;

    public Record(String label, int amount) {
        this.label = label;
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public int getAmount() {
        return amount;
    }
}
