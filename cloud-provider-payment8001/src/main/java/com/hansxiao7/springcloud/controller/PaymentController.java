package com.hansxiao7.springcloud.controller;

import com.hansxiao7.springcloud.entities.CommonResult;
import com.hansxiao7.springcloud.entities.Payment;
import com.hansxiao7.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return new CommonResult(200, "Insert Successfully, SERVER PORT: " + serverPort, result);
        } else {
            return new CommonResult(444, "Insert FAIL, SERVER PORT: " + serverPort);
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

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/discovery")
    public Object discovery(){
        // 返回所有eureka中service的名称
        List<String> services = discoveryClient.getServices();

        for (String element: services){
            log.info("*******Current service name is: " + element);
        }

        // 返回指定service下的实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance: instances){
            log.info(instance.getServiceId() + "\t" +
                    instance.getHost() + "\t" +
                    instance.getPort() + "\t" +
                    instance.getUri());
        }

        return discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

}
