package com.rxhelper.rxhelper;

import java.util.Vector;

public class Member {
    public String dbKey;
    public String name;
    public Vector<Prescription> prescriptions;

    public Member(String name, String dbKey, String pharmacyName, String pharmacyPhoneNumber) {
        this.name = name;
        this.dbKey = dbKey;
        this.prescriptions = new Vector<Prescription>();
    }

}
