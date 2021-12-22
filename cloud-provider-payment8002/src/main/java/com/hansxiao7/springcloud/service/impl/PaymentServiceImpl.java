package com.hansxiao7.springcloud.service.impl;

import com.hansxiao7.springcloud.dao.PaymentDao;
import com.hansxiao7.springcloud.entities.Payment;
import com.hansxiao7.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
