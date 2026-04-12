package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.CustomerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerHistoryRepository
		extends JpaRepository<CustomerHistory, Long>, JpaSpecificationExecutor<CustomerHistory> {

}
