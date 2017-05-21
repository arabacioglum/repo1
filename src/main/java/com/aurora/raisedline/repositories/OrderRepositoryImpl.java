package com.aurora.raisedline.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class OrderRepositoryImpl implements OrderRepositoryCustom {
	@PersistenceContext
    EntityManager entityManager;

	@Override
	public int findMaxOrderNumber() {
		int max = 0;
		Query query = entityManager.createQuery("SELECT MAX(o.orderNumber) FROM Order o ");
		List<Integer> results = (List<Integer>)query.getResultList();
		if (results.isEmpty() || results.get(0) == null){
			max = 0;
		} else {
			max = results.get(0);
		}
		return max;
	}
	
}
