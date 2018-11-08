package com.rxhelper.rxhelper;

public class PrescriptionBuilder {
    private String name = "";
    private int dosage = 0;

    PrescriptionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    PrescriptionBuilder setDosage(int dosage) {
        this.dosage = dosage;
        return this;
    }

    Prescription build() {
        return new Prescription(name, dosage);
    }
}