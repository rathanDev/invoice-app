package org.jana.invoice.request;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class AddInvoiceRequest {

    private String invoiceNo;
    private BigDecimal amount; // TODO - change to String

    private String customerName;
    private String customerEmail;

    private Date invoiceDate;
    private Date dueDate;

    private String status;

}
