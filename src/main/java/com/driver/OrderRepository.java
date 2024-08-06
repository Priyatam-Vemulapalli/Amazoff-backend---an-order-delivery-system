package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerMap.put(deliveryPartner.getId(), deliveryPartner);
        partnerToOrderMap.put(partnerId, new HashSet<>());

    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        // your code here
        //add order to given partner's order list
        //increase order count of partner
        //assign partner to this order
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            Order order = orderMap.get(orderId);
            DeliveryPartner deliveryPartner = partnerMap.get(partnerId); // This part of code might not be required.

            deliveryPartner.addOrderToDeliveryPartner(order);
            // Incrementing the number of order of the partner
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
            //Assigned partner to the order
            order.setDeliveryPartner(deliveryPartner);

            // Mapping the order and partner together.
            orderToPartnerMap.put(orderId, partnerId);
            partnerToOrderMap.put(partnerId, deliveryPartner.getOrderList());

        }
    }

    public Order findOrderById(String orderId){
        // your code here
        Order order = null;
        try{
            if(orderId != null){
                order = orderMap.get(orderId);
            }
        }
        catch(Exception e){
            e.getLocalizedMessage();
        }
        return order;
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        DeliveryPartner deliveryPartner = null;
        try{
            if(partnerId != null){
                deliveryPartner = partnerMap.get(partnerId);
            }
        }
        catch(Exception e){
            e.getLocalizedMessage();
        }
        return deliveryPartner;
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
        return deliveryPartner.getNumberOfOrders();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        return new ArrayList<>(partnerToOrderMap.get(partnerId));// Irregular code Replace this when there is issue.
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        DeliveryPartner partner = partnerMap.get(partnerId);
        if (partner != null) {
            HashSet<String> orders = partnerToOrderMap.get(partnerId);
            for (String orderId : orders) {
                orderToPartnerMap.remove(orderId);
                orderMap.get(orderId).setDeliveryPartner(null);
            }
            partnerToOrderMap.remove(partnerId);
            partnerMap.remove(partnerId);
        }
    }

    public void deleteOrder(String orderId){
        Order order = orderMap.get(orderId);
        if (order != null) {
            String partnerId = orderToPartnerMap.get(orderId);
            if (partnerId != null) {
                partnerToOrderMap.get(partnerId).remove(orderId);
                DeliveryPartner partner = partnerMap.get(partnerId);
                partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);
                orderToPartnerMap.remove(orderId);
            }
            orderMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int countUnassignedOrders = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderToPartnerMap.containsKey(orderId)) {
                countUnassignedOrders++;
            }
        }
        return countUnassignedOrders;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        int time = Integer.parseInt(timeString.split(":")[0]) * 60 + Integer.parseInt(timeString.split(":")[1]);
        int count = 0;
        for (String orderId : partnerToOrderMap.get(partnerId)) {
            Order order = orderMap.get(orderId);
            if (order.getDeliveryTime() > time) {
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        int lastDeliveryTime = 0;
        for (String orderId : partnerToOrderMap.get(partnerId)) {
            Order order = orderMap.get(orderId);
            if (order.getDeliveryTime() > lastDeliveryTime) {
                lastDeliveryTime = order.getDeliveryTime();
            }
        }
        int hours = lastDeliveryTime / 60;
        int minutes = lastDeliveryTime % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}