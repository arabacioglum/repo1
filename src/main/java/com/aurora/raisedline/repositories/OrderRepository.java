package com.aurora.raisedline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurora.raisedline.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
