package org.jana.invoice.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jana.invoice.entity.CustomerEntity;
import org.jana.invoice.entity.InvoiceEntity;
import org.jana.invoice.repository.CustomerRepo;
import org.jana.invoice.repository.InvoiceRepo;
import org.jana.invoice.request.AddInvoiceRequest;
import org.jana.invoice.request.UpdateInvoiceRequest;
import org.jana.invoice.response.InvoiceResponse;
import org.jana.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<InvoiceResponse> getInvoices(int page, int size) {
        log.info("GetInvoices page:{} size:{}", page, size);
        Page<InvoiceEntity> entities = invoiceRepo.findAll(PageRequest.of(page, size));
        List<InvoiceResponse> list = entities.stream().map(this::mapInvoiceEntityToInvoiceRes).toList();
        return list;
    }

    @Override
    @Transactional
    public InvoiceResponse addInvoice(AddInvoiceRequest req) {
        log.info("AddInvoice req:{}", req);
        InvoiceEntity invoiceEntity = mapAddInvoiceReqToInvoiceEntity(req);
        CustomerEntity customerEntity = invoiceEntity.getCustomer();
        CustomerEntity savedCustomer = customerRepo.save(customerEntity);
        InvoiceEntity savedInvoice = invoiceRepo.save(invoiceEntity);
        savedInvoice.setCustomer(savedCustomer);
        return mapInvoiceEntityToInvoiceRes(savedInvoice);
    }

    @Override
    @Transactional
    public Optional<InvoiceResponse> updateInvoice(long invoiceId, UpdateInvoiceRequest req) {
        log.info("UpdateInvoice invoiceId:{} req:{}", invoiceId, req);
        Optional<InvoiceEntity> entityOpt = invoiceRepo.findById(invoiceId);
        if (entityOpt.isEmpty()) {
            log.error("No invoice found for invoiceId:{}", invoiceId);
            return Optional.empty();
        }
        InvoiceEntity invoiceEntity = entityOpt.get();
        CustomerEntity customer = invoiceEntity.getCustomer();

        InvoiceEntity updatedInvoice = mapUpdateInvoiceReqToInvoiceEntity(req);
        updatedInvoice.setId(invoiceId);
        CustomerEntity updatedCustomer = updatedInvoice.getCustomer();
        updatedCustomer.setId(customer.getId());

        customerRepo.save(updatedCustomer);
        invoiceRepo.save(updatedInvoice);
        return Optional.of(mapInvoiceEntityToInvoiceRes(updatedInvoice));
    }

    @Override
    @Transactional
    public void deleteInvoices(List<Long> ids) {
        log.info("Delete invoices; ids:{}", ids);
        invoiceRepo.deleteByIdIn(ids);
    }

    public InvoiceResponse mapInvoiceEntityToInvoiceRes(InvoiceEntity e) {
        CustomerEntity customer = e.getCustomer();
        return InvoiceResponse.builder()
                .invoiceId(e.getId())
                .invoiceNo(e.getInvoiceNo())
                .invoiceDate(e.getInvoiceDate())
                .dueDate(e.getDueDate())
                .amount(e.getAmount())
                .status(e.getStatus())
                .customerName(customer.getCustomerName())
                .customerEmail(customer.getEmail())
                .build();
    }

    public InvoiceEntity mapAddInvoiceReqToInvoiceEntity(AddInvoiceRequest req) {
        CustomerEntity customer = CustomerEntity.builder()
                .customerName(req.getCustomerName())
                .email(req.getCustomerEmail())
                .build();

        return InvoiceEntity.builder()
                .invoiceNo(req.getInvoiceNo())
                .invoiceDate(req.getInvoiceDate())
                .dueDate(req.getDueDate())
                .amount(req.getAmount())
                .status("NEW")
                .customer(customer)
                .build();
    }

    public InvoiceEntity mapUpdateInvoiceReqToInvoiceEntity(UpdateInvoiceRequest req) {
        CustomerEntity customer = CustomerEntity.builder()
                .customerName(req.getCustomerName())
                .email(req.getCustomerEmail())
                .build();

        return InvoiceEntity.builder()
                .invoiceNo(req.getInvoiceNo())
                .invoiceDate(req.getInvoiceDate())
                .dueDate(req.getDueDate())
                .amount(req.getAmount())
                .status(req.getStatus())
                .customer(customer)
                .build();
    }

}
