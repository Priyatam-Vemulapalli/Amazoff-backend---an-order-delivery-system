package com.driver;

import java.util.HashSet;
import java.util.List;

public class DeliveryPartner {

    private final String id;
    private int numberOfOrders;
    private HashSet<String> orderList;

    public DeliveryPartner(String id) {
        this.id = id;
        this.numberOfOrders = 0;
        this.orderList = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public void addOrderToDeliveryPartner(Order order){
        orderList.add(order.getId());
    }

    public HashSet<String> getOrderList(){
        return this.orderList;
    }
}