import React, { useState } from "react";

const InvoiceFormComponent = ({
  userEvent,
  currentInvoice,
  addInvoice,
  editInvoice,
  onClose,
}) => {
  const [formData, setFormData] = useState(currentInvoice);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (userEvent === "Add") {
      addInvoice(formData);
    } else {
      editInvoice(formData);
    }
    setFormData(currentInvoice);
    onClose();
  };

  return (
    <div className="invoice-form-container">
      <form onSubmit={handleSubmit}>
        <div>
          <label>Invoice No:</label>
          <input
            type="text"
            name="invoiceNo"
            value={formData.invoiceNo}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Customer:</label>
          <input
            type="text"
            name="customerName"
            value={formData.customerName}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Email:</label>
          <input
            type="text"
            name="customerEmail"
            value={formData.customerEmail}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Invoice Date:</label>
          <input
            type="date"
            name="invoiceDate"
            value={formData.invoiceDate}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Due Date:</label>
          <input
            type="date"
            name="dueDate"
            value={formData.dueDate}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Amount:</label>
          <input
            type="number"
            name="amount"
            value={formData.amount}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Status:</label>
          <input
            type="text"
            name="status"
            value={formData.status}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Save</button>
      </form>
    </div>
  );
};

export default InvoiceFormComponent;
