package org.jana.invoice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {

    private Long invoiceId;
    private String invoiceNo;

    private String customerName;

    private String customerEmail;

    private Date invoiceDate;

    private Date dueDate;

    private String status;

    private BigDecimal amount;

}
