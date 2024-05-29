package org.jana.invoice.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateInvoiceRequest {

    private String invoiceNo;

    private String customerName;
    private String customerEmail;

    private Date invoiceDate;
    private Date dueDate;

    private String status;
    private BigDecimal amount;
}
