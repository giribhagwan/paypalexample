package com.example.paypaldemo;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Service
public class AppService {

    public void MakeTransaction(Double amount,String paymentMethodNonce){
        BraintreeGateway gateway = new BraintreeGateway(PaypaldemoApplication.accessToken);

       TransactionRequest request = new TransactionRequest()
                .amount(BigDecimal.valueOf(amount))
                .merchantAccountId("USD")
                .paymentMethodNonce(paymentMethodNonce);


        Result<Transaction> saleResult = gateway.transaction().sale(request);

        if (saleResult.isSuccess()) {
            Transaction transaction = saleResult.getTarget();
            System.out.println("Success ID: " + transaction.getId());
        } else {
            System.out.println("Message: " + saleResult.getMessage());
        }
    }
    Map<String, String> map = new HashMap<String, String>();
    public String getPaymentUrl(HttpServletRequest req,APIContext apiContext) {
        Payment createdPayment = null;
        String url=null;
            // ###Details
            // Let's you specify details of a payment amount.
            Details details = new Details();
            details.setShipping("1");
            details.setSubtotal("5");
            details.setTax("1");

            // ###Amount
            // Let's you specify a payment amount.
            Amount amount = new Amount();
            amount.setCurrency("USD");
            // Total must be equal to sum of shipping, tax and subtotal.
            amount.setTotal("7");
            amount.setDetails(details);

            // ###Transaction
            // A transaction defines the contract of a
            // payment - what is the payment for and who
            // is fulfilling it. Transaction is created with
            // a `Payee` and `Amount` types
            com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
            transaction.setAmount(amount);
            transaction
                    .setDescription("This is the payment transaction description.");

            // ### Items
            Item item = new Item();
            item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
            ItemList itemList = new ItemList();
            List<Item> items = new ArrayList<Item>();
            items.add(item);
            itemList.setItems(items);

            transaction.setItemList(itemList);


            // The Payment creation API requires a list of
            // Transaction; add the created `Transaction`
            // to a List
            List<com.paypal.api.payments.Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            // ###Payer
            // A resource representing a Payer that funds a payment
            // Payment Method
            // as 'paypal'
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            // ###Payment
            // A Payment Resource; create one using
            // the above types and intent as 'sale'
            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);

            // ###Redirect URLs
            RedirectUrls redirectUrls = new RedirectUrls();
            String guid = UUID.randomUUID().toString().replaceAll("-", "");
            redirectUrls.setCancelUrl(req.getScheme() + "://"
                    + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
            redirectUrls.setReturnUrl(req.getScheme() + "://"
                    + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath() + "/url/exchange/");
            payment.setRedirectUrls(redirectUrls);

            // Create a payment by posting to the APIService
            // using a valid AccessToken
            // The return object contains the status;
            try {
                createdPayment = payment.create(apiContext);
//                LOGGER.info("Created payment with id = "
//                        + createdPayment.getId() + " and status = "
//                        + createdPayment.getState());
                // ###Payment Approval Url
                Iterator<Links> links = createdPayment.getLinks().iterator();
                while (links.hasNext()) {
                    Links link = links.next();
                    if (link.getRel().equalsIgnoreCase("approval_url")) {
                        req.setAttribute("redirectURL", link.getHref());
                        url= link.getHref();
                    }
                }
//                ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), Payment.getLastResponse(), null);
                map.put(guid, createdPayment.getId());
            } catch (PayPalRESTException e) {
//                ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), null, e.getMessage());
            }
        return url;
    }


}
