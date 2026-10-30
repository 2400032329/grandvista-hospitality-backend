package com.grandvista.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grandvista.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}