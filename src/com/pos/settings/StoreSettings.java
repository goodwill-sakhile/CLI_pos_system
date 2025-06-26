package com.pos.settings;

public class StoreSettings {
    private String storeName = "Default Store";
    private String storeLocation = "Unknown";
    private String contactInfo = "N/A";

    public String getStoreName() {
        return storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void updateSettings(String name, String location, String contact) {
        this.storeName = name;
        this.storeLocation = location;
        this.contactInfo = contact;
    }

    public void display() {
        System.out.println("📍 Store Settings:");
        System.out.println("Store Name    : " + storeName);
        System.out.println("Location      : " + storeLocation);
        System.out.println("Contact Info  : " + contactInfo);
    }
}
