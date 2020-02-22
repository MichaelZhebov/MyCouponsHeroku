package com.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.beans.Company;
import com.main.beans.Coupon;
import com.main.beans.Customer;
import com.main.repo.CompanyRepository;
import com.main.repo.CouponRepository;
import com.main.repo.CustomerRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public ResponseEntity<?> addCoupon(Coupon coupon, long companyID) {
		if (coupon.getAmount() == null || coupon.getAmount() <= 0) {
			return new ResponseEntity<String>("The coupon Amount is not valid!", HttpStatus.BAD_REQUEST);
		}
		if (coupon.getPrice() == null || coupon.getPrice() <= 0) {
			return new ResponseEntity<String>("The coupon Price is not valid!", HttpStatus.BAD_REQUEST);
		}
		Optional<Company> company = companyRepository.findById(companyID);
		List<Coupon> coupons = couponRepository.findByCompanyId(companyID);
		for (Coupon coupon2 : coupons) {
			if (coupon2.getTitle().equalsIgnoreCase(coupon.getTitle())) {
				return new ResponseEntity<String>("The coupon with same Title is already exist!",
						HttpStatus.BAD_REQUEST);
			}
		}
		coupon.setCompanyId(companyID);
		coupon.setCompanyName(company.get().getFullName());
		if (coupon.getImage() == null) {
			coupon.setImage(
					"https://i.ibb.co/mSnVgRM/coupon-Default.png");
		}
		company.get().addCoupon(coupon);
		companyRepository.save(company.get());
		couponRepository.save(coupon);
		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}

	public ResponseEntity<?> updateCompany(Company newCompany, long companyId) {
		if (!newCompany.getEmail().matches(
				"^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")) {
			return new ResponseEntity<String>("Email is not valid!", HttpStatus.BAD_REQUEST);
		}
		if (newCompany.getPassword().length() < 4) {
			return new ResponseEntity<String>("Password must be at least 4 characters", HttpStatus.BAD_REQUEST);
		}
		Company company = companyRepository.findById(companyId).get();
		if (!newCompany.getEmail().equalsIgnoreCase(company.getEmail())) {
			Optional<Company> existByEmail = companyRepository.findByEmailIgnoreCase(newCompany.getEmail());
			Optional<Customer> existCustomer = customerRepository.findByEmailIgnoreCase(newCompany.getEmail());
			if (!existByEmail.isEmpty() || !existCustomer.isEmpty()) {
				return new ResponseEntity<String>("This email is already used!", HttpStatus.BAD_REQUEST);
			}
		}
		if (!newCompany.getFullName().equalsIgnoreCase(company.getFullName())) {
			Optional<Company> existByName = companyRepository.findByFullNameIgnoreCase(newCompany.getFullName());
			if (!existByName.isEmpty()) {
				return new ResponseEntity<String>("This name is already used!", HttpStatus.BAD_REQUEST);
			}
		}

		if (newCompany.getFullName() != null) {
			company.setFullName(newCompany.getFullName());
			List<Coupon> coupons = couponRepository.findByCompanyId(companyId);
			coupons.forEach(c -> c.setCompanyName(newCompany.getFullName()));
		}
		if (newCompany.getEmail() != null) {
			company.setEmail(newCompany.getEmail());
		}
		if (newCompany.getPassword() != null && !newCompany.getPassword().equals(company.getPassword())) {
			company.setPassword(encoder.encode(newCompany.getPassword()));
		}
		if (newCompany.isActive() != company.isActive()) {
			company.setActive(newCompany.isActive());
		}
		final Company updatedCompany = companyRepository.save(company);
		return ResponseEntity.ok(updatedCompany);
	}

	public Coupon getCoupon(long id) {
		return couponRepository.findById(id).get();
	}

	public ResponseEntity<?> updateCoupon(Coupon newCoupon, long couponID, long companyId) {
		if (newCoupon.getEndDate().before(newCoupon.getStartDate())) {
			return new ResponseEntity<String>("The coupon End Date is not valid!", HttpStatus.BAD_REQUEST);
		}
		if (newCoupon.getAmount() == null || newCoupon.getAmount() <= 0) {
			return new ResponseEntity<String>("The coupon Amount is not valid!", HttpStatus.BAD_REQUEST);
		}
		if (newCoupon.getPrice() == null || newCoupon.getPrice() <= 0) {
			return new ResponseEntity<String>("The coupon Price is not valid!", HttpStatus.BAD_REQUEST);
		}
		Coupon coupon = couponRepository.findById(couponID)
				.orElseThrow(() -> new ResourceNotFoundException("Coupon not found for this id : " + couponID));
		if (newCoupon.getTitle() != null) {
			Optional<Company> company = companyRepository.findById(companyId);
			for (Coupon c : company.get().getCoupons()) {
				if (c.getTitle() == newCoupon.getTitle()) {
					return new ResponseEntity<String>("The coupon with this title is already exist!",
							HttpStatus.BAD_REQUEST);
				}
			}
			coupon.setTitle(newCoupon.getTitle());
		}

		if (newCoupon.getAmount() != null) {
			coupon.setAmount(newCoupon.getAmount());
		}
		if (newCoupon.getCategory() != null) {
			coupon.setCategory(newCoupon.getCategory());
		}
		if (newCoupon.getDescription() != null) {
			coupon.setDescription(newCoupon.getDescription());
		}
		if (newCoupon.getStartDate() != null) {
			coupon.setStartDate(newCoupon.getStartDate());
		}
		if (newCoupon.getEndDate() != null) {
			coupon.setEndDate(newCoupon.getEndDate());
		}
		if (newCoupon.getImage() != null) {
			coupon.setImage(newCoupon.getImage());
		}
		if (newCoupon.getPrice() != null) {
			coupon.setPrice(newCoupon.getPrice());
		}

		final Coupon updatedCoupon = couponRepository.save(coupon);
		return ResponseEntity.ok(updatedCoupon);
	}

	public Map<String, Boolean> deleteCoupon(long couponID) {
		Coupon coupon = couponRepository.findById(couponID)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found for this id : " + couponID));
		List<Customer> customers = customerRepository.findByCouponsId(coupon.getId());
		if (customers != null) {
			customers.forEach(c -> {
				c.getCoupons().remove(coupon);
				customerRepository.save(c);
			});
		}
		couponRepository.delete(coupon);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	public List<Coupon> getCompanyCoupons(long id) {
		return couponRepository.findByCompanyId(id);
	}

	public ResponseEntity<Company> getOneCompany(long companyID) {
		Company company = companyRepository.findById(companyID)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found for this id : " + companyID));
		return ResponseEntity.ok(company);
	}

}
