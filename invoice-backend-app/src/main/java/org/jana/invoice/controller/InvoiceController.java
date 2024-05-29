package org.jana.invoice.controller;

import org.jana.invoice.request.AddInvoiceRequest;
import org.jana.invoice.request.UpdateInvoiceRequest;
import org.jana.invoice.response.InvoiceResponse;
import org.jana.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "http://localhost:3000")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceResponse> getInvoices(@RequestParam int page, @RequestParam int size) {
        return invoiceService.getInvoices(page, size);
    }

    @PostMapping
    public InvoiceResponse addInvoice(@RequestBody AddInvoiceRequest req) {
        return invoiceService.addInvoice(req);
    }

    @PutMapping
    public Optional<InvoiceResponse> updateInvoice(@RequestParam long invoiceId, @RequestBody UpdateInvoiceRequest req) {
        return invoiceService.updateInvoice(invoiceId, req);
    }

    @DeleteMapping
    public void deleteInvoice(@RequestParam List<Long> ids) {
        invoiceService.deleteInvoices(ids);
    }

    // TODO - create deleteInvoices

}
