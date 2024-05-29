import React, { useEffect, useState } from "react";
import axios from "axios";
import InvoiceFormComponent from "./InvoiceFormComponent";

const InvoiceListComponent = () => {
  const baseUrl = "http://localhost:8080/invoices";
  const dataFormattingOptions = {
    year: "numeric",
    month: "long",
    day: "numeric",
  };
  const [allInvoices, setAllInvoices] = useState([]);
  const [invoices, setInvoices] = useState([]);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(3);
  const [showAddInvoiceForm, setShowAddInvoiceForm] = useState(false);
  const getCurrentDate = () => {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, "0");
    const month = String(today.getMonth() + 1).padStart(2, "0"); // Months are zero-indexed
    const year = today.getFullYear();
    return `${year}-${month}-${day}`;
  };
  const emptyInvoice = {
    invoiceNo: "",
    customerName: "",
    customerEmail: "",
    invoiceDate: getCurrentDate(),
    dueDate: getCurrentDate(),
    amount: "",
    status: "",
  };
  const [currentInvoice, setCurrentInvoice] = useState(emptyInvoice);
  const [userEvent, setUserEvent] = useState("");
  const [searchQuery, setSearchQuery] = useState("");
  const itemsPerPageOptions = [3, 5, 10];

  useEffect(() => {
    pullInvoices();
  }, [page, pageSize]);

  const pullInvoices = () => {
    axios
      .get(`${baseUrl}?page=${page}&size=${pageSize}`)
      .then((res) => {
        setAllInvoices(res?.data);
        setInvoices(res?.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const handlePageSize = (e) => {
    const chosen = parseInt(e.target.value, 10);
    setPageSize(chosen);
    setPage(0);
  };

  const deleteInvoice = (invoice) => {
    const invoiceId = invoice.invoiceId;
    axios
      .delete(`${baseUrl}?ids=${JSON.stringify(invoiceId)}`)
      .then((res) => {
        pullInvoices();
      })
      .catch((e) => console.error(e));
  };

  const formatDate = (date) => {
    return new Date(date).toLocaleDateString(undefined, dataFormattingOptions);
  };

  const toggleInvoiceForm = () => {
    setShowAddInvoiceForm(!showAddInvoiceForm);
  };

  const onAddInvoiceButtonClicked = () => {
    setUserEvent("Add");
    setCurrentInvoice(emptyInvoice);
    toggleInvoiceForm();
  };

  const addInvoice = (newInvoice) => {
    axios
      .post(`${baseUrl}`, newInvoice)
      .then((res) => {
        pullInvoices();
      })
      .catch((err) => console.error(err));
    setPage(0);
  };

  const onEditInvoiceButtonClicked = (invoice) => {
    setUserEvent("Edit");
    setCurrentInvoice(invoice);
    toggleInvoiceForm();
  };

  const editInvoice = (invoice) => {
    axios
      .put(`${baseUrl}?invoiceId=${invoice.invoiceId}`, invoice)
      .then((res) => {
        pullInvoices();
      })
      .catch((err) => console.error(err));
    setPage(0);
  };

  const handleSearch = (e) => {
    const newQuery = e.target.value;
    setSearchQuery(newQuery);
    if (!newQuery || newQuery == "") {
      setInvoices(allInvoices);
      return;
    }
    const filtered = allInvoices.filter((e) =>
      e.invoiceNo.toLowerCase().includes(newQuery)
    );
    setInvoices(filtered);
  };

  return (
    <div>
      <h3>Invoice List</h3>
      <div className="search-add-div">
        <input
          type="text"
          placeholder="Search invoices..."
          value={searchQuery}
          onChange={handleSearch}
        />
        <button onClick={onAddInvoiceButtonClicked}>Add Invoice</button>
        {showAddInvoiceForm && (
          <InvoiceFormComponent
            userEvent={userEvent}
            currentInvoice={currentInvoice}
            addInvoice={addInvoice}
            editInvoice={editInvoice}
            onClose={toggleInvoiceForm}
          />
        )}
      </div>
      {invoices.length == 0 ? (
        <p>No invoices found</p>
      ) : (
        <div>
          <table>
            <thead>
              <tr>
                <th>InvoiceNo</th>
                <th>Customer</th>
                <th>Email</th>
                <th>Invoice date</th>
                <th>Due date</th>
                <th>Status</th>
                <th>Amount</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {invoices.map((i) => (
                <tr key={i.invoiceId}>
                  <td>{i.invoiceNo}</td>
                  <td>{i.customerName}</td>
                  <td>{i.customerEmail}</td>
                  <td>{formatDate(i.invoiceDate)}</td>
                  <td>{formatDate(i.dueDate)}</td>
                  <td>{i.status}</td>
                  <td>{i.amount}</td>
                  <td>
                    <button onClick={() => onEditInvoiceButtonClicked(i)}>
                      Edit
                    </button>
                    <button onClick={() => deleteInvoice(i)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div>
            <button onClick={() => setPage(page - 1)}>Prev</button>
            <button onClick={() => setPage(page + 1)}>Next</button>
          </div>
          <div>
            <select
              value={pageSize}
              onChange={handlePageSize}
              className="items-per-page"
            >
              {itemsPerPageOptions.map((option) => (
                <option key={option} value={option}>
                  {option}
                </option>
              ))}
            </select>
          </div>
        </div>
      )}
    </div>
  );
};

export default InvoiceListComponent;
