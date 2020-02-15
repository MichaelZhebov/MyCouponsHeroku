package com.main.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.beans.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findByEmailIgnoreCase(String email);
		
	List<Customer> findByCouponsId(Long couponID);
	
	long count();
}
