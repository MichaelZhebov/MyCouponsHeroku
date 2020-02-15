package com.main.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.main.repo.CompanyRepository;
import com.main.repo.CouponRepository;
import com.main.repo.CustomerRepository;

@Service
public class InfoService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	public ResponseEntity<?> getInfo() {
		Map<Object, Object> model = new HashMap<>();
		model.put("companiesCount", companyRepository.count() - 1);
		model.put("customersCount", customerRepository.count());
		model.put("couponsCount", couponRepository.count());
		return ok(model);
	}
}
