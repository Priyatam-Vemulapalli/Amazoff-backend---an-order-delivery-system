package com.driver;

import java.util.List;

import com.driver.Exceptions.DeliveryPartnerNotFound;
import com.driver.Exceptions.OrderNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        // Add order feature done.
        // tested
        orderService.addOrder(order);
        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        // Add delivery partner feature done.
        // tested.
        orderService.addPartner(partnerId);
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){

        // Assigning the partner to order and vice versa is done
        // tested.
        orderService.createOrderPartnerPair(orderId, partnerId);
        //This is basically assigning that order to that partnerId
        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){
        // get order by ID feature is done
        // tested
        Order order= null;
        //order should be returned with an orderId.
        try{
            order = orderService.getOrderById(orderId);
        }
        catch(OrderNotFound orderNotFound){
            orderNotFound.getLocalizedMessage();
        }
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){
        // Get partner by ID is done.
        // tested.
        DeliveryPartner deliveryPartner = null;
        try{
            deliveryPartner = orderService.getPartnerById(partnerId);
        }
        catch(DeliveryPartnerNotFound deliveryPartnerNotFound){
            deliveryPartnerNotFound.getLocalizedMessage();
        }
        //deliveryPartner should contain the value given by partnerId

        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){
        // Code feature done
        // tested.
        Integer orderCount = 0;

        //orderCount should denote the orders given by a partner-id
        try{
            if(partnerId != null){
                orderCount = orderService.getOrderCountByPartnerId(partnerId);
            }
        }
        catch(Exception e){
            e.getLocalizedMessage();
        }

        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        // Code is done. Typecasting is needed at DB level
        // tested.
        List<String> orders = null;

        //orders should contain a list of orders by PartnerId
        if(partnerId != null){
            orders = orderService.getOrdersByPartnerId(partnerId);
        }
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        // Code is done
        // tested
        List<String> orders = null;
        orders = orderService.getAllOrders();
        //Get all orders
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        // Code is done
        // tested
        Integer countOfOrders = 0;
        countOfOrders = orderService.getCountOfUnassignedOrders();
        //Count of orders that have not been assigned to any DeliveryPartner

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){
        //test failed {
        //    "timestamp": "2024-08-06T14:04:36.051+00:00",
        //    "status": 500,
        //    "error": "Internal Server Error",
        //    "path": "/orders/get-count-of-orders-left-after-given-time/12943783"
        //}
        Integer countOfOrders = 0;

        //countOfOrders that are left after a particular time of a DeliveryPartner

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        //returns a null object

        String time = null;

        //Return the time when that partnerId will deliver his last delivery order.

        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

        // Delete the partnerId
        // And push all his assigned orders to unassigned orders.
        // Tested
        orderService.deletePartner(partnerId);
        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){

        // Delete an order and also
        // remove it from the assigned order of that partnerId
        // Tested

        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
