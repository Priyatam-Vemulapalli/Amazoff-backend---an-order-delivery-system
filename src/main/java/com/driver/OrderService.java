package com.driver;

import java.util.*;

import com.driver.Exceptions.DeliveryPartnerNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order){
        orderRepository.saveOrder(order);
    }

    public void addPartner(String partnerId){
        orderRepository.savePartner(partnerId);
    }

    public void createOrderPartnerPair(String orderId, String partnerId){
        orderRepository.saveOrderPartnerMap(orderId, partnerId);
    }

    public Order getOrderById(String orderId){

        return orderRepository.findOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.findPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId){
        // Checking if the delivery partner with such ID exist in the DB.
        try{
            DeliveryPartner deliveryPartner = getPartnerById(partnerId);
            if(deliveryPartner == null){
                throw new DeliveryPartnerNotFound(String.format("Delivery partner with ID: %s is not found",partnerId));
            }

        }
        catch (DeliveryPartnerNotFound deliveryPartnerNotFound){
            deliveryPartnerNotFound.getLocalizedMessage();
        }
        return orderRepository.findOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        try{
            // Checking if the delivery partner with such ID exist in the DB.
            DeliveryPartner deliveryPartner = getPartnerById(partnerId);
            if(deliveryPartner == null){
                throw new DeliveryPartnerNotFound(String.format("Delivery partner with ID: %s is not found",partnerId));
            }
        }
        catch(DeliveryPartnerNotFound deliveryPartnerNotFound){
            deliveryPartnerNotFound.getLocalizedMessage();
        }
        return orderRepository.findOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders(){
        return orderRepository.findAllOrders();
    }

    public void deletePartner(String partnerId){
        DeliveryPartner partner = getPartnerById(partnerId);
        if (partner != null) {
            // Unassign all orders associated with this partner
            List<String> orders = partner.getOrderList().stream().toList();
            for (String orderId : orders) {
                Order order = getOrderById(orderId);
                if (order != null) {
                    order.setDeliveryPartner(null);
                }
            }
            // Remove the partner from repository
            orderRepository.deletePartner(partnerId);
        }
    }

    public void deleteOrder(String orderId){
        Order order = getOrderById(orderId);
        if (order != null) {
            // Unassign the partner associated with this order
            DeliveryPartner partner = order.getDeliveryPartner();
            if (partner != null) {
                partner.getOrderList().remove(orderId);
                partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);
            }
            // Remove the order from repository
            orderRepository.deleteOrder(orderId);
        }
//        orderRepository.deleteOrder(orderId);
    }

    public Integer getCountOfUnassignedOrders(){
        return orderRepository.findCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return orderRepository.findOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.findLastDeliveryTimeByPartnerId(partnerId);
    }
}