package com.hansxiao7.springcloud.controller;

import com.hansxiao7.springcloud.entities.CommonResult;
import com.hansxiao7.springcloud.entities.Payment;
import com.hansxiao7.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****INSERT RESULT：" + result);

        if (result > 0) {
            return new CommonResult(200, "Insert Successfully , SERVER PORT: " + serverPort, result);
        } else {
            return new CommonResult(444, "Insert FAIL , SERVER PORT: " + serverPort);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****GET RESULT: " + payment);

        if (payment != null){
            return new CommonResult<>(200, "Get successfully, SERVER PORT: " + serverPort, payment);
        } else {
            return new CommonResult<>(444, "NO GET RECORD, SERVER PORT: " + serverPort);
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

}
