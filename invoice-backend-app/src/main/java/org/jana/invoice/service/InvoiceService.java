package org.jana.invoice.service;

import org.jana.invoice.request.AddInvoiceRequest;
import org.jana.invoice.request.UpdateInvoiceRequest;
import org.jana.invoice.response.InvoiceResponse;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    List<InvoiceResponse> getInvoices(int page, int size);

    InvoiceResponse addInvoice(AddInvoiceRequest req);

    Optional<InvoiceResponse> updateInvoice(long id, UpdateInvoiceRequest req);

    void deleteInvoices(List<Long> ids);

}
