package com.javatechie.os.api.common;

import com.javatechie.os.api.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data

@NoArgsConstructor
public class TransactionResponse {

    private Order order;
    private double amount;
    private String transactionId;
    private String message;

    public TransactionResponse(Order order, double amount, String transactionId, String response) {
        this.order=order;
        this.amount=amount;
        this.transactionId=transactionId;
        this.message=response;

    }


}
