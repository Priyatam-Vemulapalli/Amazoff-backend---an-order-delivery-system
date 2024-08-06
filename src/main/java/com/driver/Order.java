package com.driver;

public class Order {

    private final String id;
    private final int deliveryTime;
    DeliveryPartner deliveryPartner;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM

        String[] sidesplittingTime = deliveryTime.split(":");
        int deliveryTimeInt = Integer.parseInt(sidesplittingTime[0]) * 60 + Integer.parseInt(sidesplittingTime[1]);

        this.id = id;
        this.deliveryTime=deliveryTimeInt;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public DeliveryPartner getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }
}
