package com.example.paypaldemo;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AppController {

    @Autowired
    AppService appService;
    @PostMapping("/api/create-payment/")
    public String getClientToken(){
        BraintreeGateway gateway = new BraintreeGateway(PaypaldemoApplication.accessToken);
        return gateway.clientToken().generate();
    }

    @PostMapping("/api/execute-payment/")
    public String executePayment(@RequestParam String paymentID,@RequestParam String payerID){
        System.out.println(paymentID);
        System.out.println(payerID);
        return null;
    }

    @PostMapping("/api/make")
    public String makePayment(@RequestParam Double amount,@RequestParam String paymentMethodNonce){
        appService.MakeTransaction(amount,paymentMethodNonce);
        return "done";
    }
}
