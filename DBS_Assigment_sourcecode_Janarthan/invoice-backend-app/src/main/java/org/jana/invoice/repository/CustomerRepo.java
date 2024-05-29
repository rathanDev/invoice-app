package org.jana.invoice.repository;

import org.jana.invoice.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {
}
