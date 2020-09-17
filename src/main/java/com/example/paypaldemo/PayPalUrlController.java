package com.example.paypaldemo;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("url")
public class PayPalUrlController {
    @Autowired
    AppService appService;

    @GetMapping("createPayment")
    public String getPaymenturl(HttpServletRequest req, HttpServletResponse resp) {
        return appService.getPaymentUrl(req,PaypaldemoApplication.apiContext);
    }

    @GetMapping("exchange")
    public Boolean exchange(@RequestParam String PayerID, @RequestParam String paymentId) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerID);
        try {
            Payment createdPayment = payment.execute(PaypaldemoApplication.apiContext, paymentExecution);
            System.out.println(createdPayment);
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return true;
    }

}
