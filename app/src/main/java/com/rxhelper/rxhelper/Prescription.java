package com.rxhelper.rxhelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Prescription implements Parcelable {

    public String name; // Name of the prescription i.e. "Ibuprofen"
    public String dosage; // Dosage of the prescription i.e. "10mg"
    public int remainingDoses; // Remaining doses of the prescription i.e. 10
    public String dbKey;

    public Prescription(String name, String dosage, int remainingDoses, String dbKey) {
        this.name = name;
        this.dosage = dosage;
        this.remainingDoses = remainingDoses;
        this.dbKey = dbKey;
    }

    // Used to identify keys when stored in FireBase
    public static class PropertyKey {
        public final static String name = "name";
        public final static String dosage = "dosage";
        public final static String remainingDoses = "remainingDoses";
    }

    // Parcelable methods
    public Prescription(Parcel parcel) {
        this.name = parcel.readString();
        this.dosage = parcel.readString();
        this.remainingDoses = parcel.readInt();
        this.dbKey = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.dosage);
        dest.writeInt(this.remainingDoses);
        dest.writeString(this.dbKey);
    }

    public static final Parcelable.Creator<Prescription> CREATOR = new Parcelable.Creator<Prescription>(){

        @Override
        public Prescription createFromParcel(Parcel parcel) {
            return new Prescription(parcel);
        }

        @Override
        public Prescription[] newArray(int size) {
            return new Prescription[0];
        }
    };

}
