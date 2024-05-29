package org.jana.invoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "invoice")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNo;

    private Date invoiceDate;
    private Date dueDate;

    private BigDecimal amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
