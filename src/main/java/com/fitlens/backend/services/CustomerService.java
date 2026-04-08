package com.fitlens.backend.services;

import com.fitlens.backend.dto.customer.CustomerResponse;
import com.fitlens.backend.dto.customer.CustomerUpdateRequest;
import com.fitlens.backend.mappers.CustomerMapper;
import com.fitlens.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {

	private final UserRepository userRepository;

	private final CustomerMapper customerMapper;

	public CustomerResponse getCustomerProfile(Long customerId) {

		var customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));

//		return CustomerResponse.builder()
//			.id(customer.getId())
//			.firstName(customer.getFirstName())
//			.lastName(customer.getLastName())
//			.email(customer.getEmail())
//			.gender(customer.getGender())
//			.birthDate(customer.getBirthDate())
//			.heightCm(customer.getHeightCm())
//			.weightKg(customer.getWeightKg())
//			.fitnessLevel(customer.getFitnessLevel())
//			.goal(customer.getGoal())
//			.trainingDays(customer.getTrainingDays())
//			.build();
		return customerMapper.toResponse(customer);
	}

	public CustomerResponse updateProfile(Long customerId, CustomerUpdateRequest request){

		if (request == null){

		}
		var customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));
		customerMapper.updateCustomer(customer,request);
		var savedCustomer = userRepository.save(customer);
		return customerMapper.toResponse(savedCustomer);
	}

}
