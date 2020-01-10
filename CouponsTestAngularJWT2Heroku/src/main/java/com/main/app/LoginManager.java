package com.main.app;

import org.springframework.stereotype.Component;

@Component
public class LoginManager {
	
	
//	public ClientController login(String email, String password) {
//		List<User> users = userService.getAllUsers();
//		for (User user : users) {
//			if (user.getEmail().equalsIgnoreCase(email) && bCryptPasswordEncoder.matches(password, user.getPassword())) {
//				if (user.getRole().equals(Role.Admin)) {
//					return new AdminService();
//				} else if (user.getRole().equals(Role.Company)) {
//					CompanyService companyService = new CompanyService();
//					companyService.setId(user.getId());
//				} else if (user.getRole().equals(Role.Customer)) {
//					CustomerService customerSrvice = new CustomerService();
//					customerSrvice.setId(user.getId());
//				}
//			}
//		}
//		return null;
//	}

}
