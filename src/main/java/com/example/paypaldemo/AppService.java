package com.example.paypaldemo;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AppService {

    public void MakeTransaction(Double amount,String paymentMethodNonce){
        BraintreeGateway gateway = new BraintreeGateway(PaypaldemoApplication.accessToken);
        TransactionRequest request = new TransactionRequest()
                .amount(BigDecimal.valueOf(amount))
                .merchantAccountId("USD")
                .paymentMethodNonce(paymentMethodNonce)
                .orderId("Mapped to PayPal Invoice Number")
                .descriptor()
                .name("Descriptor displayed in customer CC statements. 22 char max")
                .done()
                .shippingAddress()
                .firstName("Jen")
                .lastName("Smith")
                .company("Braintree")
                .streetAddress("1 E 1st St")
                .extendedAddress("Suite 403")
                .locality("Bartlett")
                .region("IL")
                .postalCode("60103")
                .countryCodeAlpha2("US")
                .done()
                .options()
                .paypal()
                .customField("PayPal custom field")
                .description("Description for PayPal email receipt")
                .done()
                .storeInVaultOnSuccess(true)
                .done();

        Result<Transaction> saleResult = gateway.transaction().sale(request);

        if (saleResult.isSuccess()) {
            Transaction transaction = saleResult.getTarget();
            System.out.println("Success ID: " + transaction.getId());
        } else {
            System.out.println("Message: " + saleResult.getMessage());
        }
    }
}
