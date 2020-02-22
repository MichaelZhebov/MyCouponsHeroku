package com.main.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	List<Coupon> findByCompanyId(long id);
	
	List<Coupon> findByEndDateLessThanEqual(Date endDate);
	
	Optional<Coupon> findByTitle(String title);
	
	long count();
}
