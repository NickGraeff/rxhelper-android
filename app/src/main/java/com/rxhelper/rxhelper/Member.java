package com.rxhelper.rxhelper;

import java.util.Vector;

public class Member {
    public String dbKey;
    public String name;
    public String pharmacyName;
    public String pharmacyPhoneNumber;
    public Vector<Prescription> prescriptions;

    public Member(String name, String dbKey, String pharmacyName, String pharmacyPhoneNumber) {
        this.name = name;
        this.dbKey = dbKey;
        this.pharmacyName = pharmacyName;
        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
        this.prescriptions = new Vector<Prescription>();
    }

}
