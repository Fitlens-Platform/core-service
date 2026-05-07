package com.fitlens.backend.services;

import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.customer_history.CustomerHistoryFilter;
import com.fitlens.backend.dto.customer_history.CustomerHistoryResponse;
import com.fitlens.backend.entities.User;
import com.fitlens.backend.mappers.CustomerHistoryMapper;
import com.fitlens.backend.repositories.CustomerHistoryRepository;
import com.fitlens.backend.repositories.UserRepository;
import com.fitlens.backend.specifications.CustomerHistorySpecification;
import com.fitlens.backend.utils.SortUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerHistoryService {

	private final CustomerHistoryRepository userHistoryRepository;

	private final CustomerHistoryMapper customerHistoryMapper;

	private final UserRepository userRepository;

	@Transactional
	public void saveCustomerHistorySnapshot(User user) {

		log.info("Creating history snapshot for user ID: {}", user.getId());

		var customerHistory = customerHistoryMapper.toHistory(user);

		userHistoryRepository.save(customerHistory);
	}

	@Transactional
	public PagedResponse<CustomerHistoryResponse> getAllHistory(Long customerId, CustomerHistoryFilter filter) {

		log.info("Getting history for customer: {}", customerId);

		userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));

		var specification = CustomerHistorySpecification.buildSpecification(customerId, filter);
		var sort = SortUtils.buildSort(filter.getSortBy(), filter.getSortDirection());
		var pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

		var historyPage = userHistoryRepository.findAll(specification, pageable);

		var history = historyPage.getContent().stream().map(customerHistoryMapper::toResponse).toList();

		return PagedResponse.<CustomerHistoryResponse>builder()
			.content(history)
			.totalElements(historyPage.getTotalElements())
			.totalPages(historyPage.getTotalPages())
			.currentPage(historyPage.getNumber())
			.pageSize(historyPage.getSize())
			.hasNext(historyPage.hasNext())
			.hasPrevious(historyPage.hasPrevious())
			.build();

	}

}
