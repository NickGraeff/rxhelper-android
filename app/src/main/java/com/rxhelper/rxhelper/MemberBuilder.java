package com.rxhelper.rxhelper;

public class MemberBuilder {
    private String name = "";
    private String dbKey = "";
    private String pharmacyName = "";
    private String pharmacyPhoneNumber = "";

    public MemberBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MemberBuilder setKey(String dbKey) {
        this.dbKey = dbKey;
        return this;
    }


    public MemberBuilder setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
        return this;
    }


    public MemberBuilder setPharmacyPhoneNumber(String pharmacyPhoneNumber) {
        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
        return this;
    }

    public Member build() {
        return new Member(name, dbKey, pharmacyName, pharmacyPhoneNumber);
    }
}