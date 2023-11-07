package com.productStore.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productStore.model.entities.Customer;
@Service
@Transactional
public class SpringUserDetailsService implements UserDetailsService {

	private CustomerService service;

	@Autowired
	public SpringUserDetailsService(CustomerService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		Customer user = service.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("the user with email " + email
					+ " is not there check");
		}
		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRole()));
		return springUser;
	}

}
