package com.driver.Exceptions;

public class DeliveryPartnerNotFound extends RuntimeException{
    public DeliveryPartnerNotFound(String message){
        super(message);
    }
}
