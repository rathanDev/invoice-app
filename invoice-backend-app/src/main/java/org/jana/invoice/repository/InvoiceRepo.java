package org.jana.invoice.repository;

import org.jana.invoice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepo extends JpaRepository<InvoiceEntity, Long> {

    void deleteByIdIn(List<Long> ids);

}
