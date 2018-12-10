package com.rxhelper.rxhelper;

public class PrescriptionBuilder {
    private String name = "";
    private String dosage = "";
    private int remainingDoses = 0;
    private String dbKey = "";

    PrescriptionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    PrescriptionBuilder setDosage(String dosage) {
        this.dosage = dosage;
        return this;
    }

    PrescriptionBuilder setRemainingDoses(int remainingDoses) {
        this.remainingDoses = remainingDoses;
        return this;
    }

    PrescriptionBuilder setDBKey(String dbKey) {
        this.dbKey = dbKey;
        return this;
    }

    Prescription build() {
        return new Prescription(name, dosage, remainingDoses, dbKey);
    }
}