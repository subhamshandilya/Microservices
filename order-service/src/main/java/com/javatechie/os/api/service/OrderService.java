package com.javatechie.os.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.os.api.common.Payment;
import com.javatechie.os.api.common.TransactionRequest;
import com.javatechie.os.api.common.TransactionResponse;
import com.javatechie.os.api.entity.Order;
import com.javatechie.os.api.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository repository;


    @Autowired

    private RestTemplate template;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;
    private Logger log=LoggerFactory.getLogger(OrderService.class);



    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String response="";
        Order order=request.getOrder();
        Payment payment=request.getPayment();
        payment.setAmount(order.getPrice());

        log.info("Order Service request :{}",new ObjectMapper().writeValueAsString(request));
       Payment paymentResponse= template.postForObject(ENDPOINT_URL,payment,Payment.class);
       response=paymentResponse.getPaymentStatus().equals("success")?"payment done and order placed":"payment failed";
       log.info(" PaymentService Response from Order Service ");
        repository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);


    }
}
