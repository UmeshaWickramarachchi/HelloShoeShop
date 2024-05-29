$(document).ready(function () {
  $("#addCustomerFormOpen").on("click", function handleNewCustomerForm() {
    $("#addNewCustomer").modal("show");
  });
  function fetchCustomers() {
    $.ajax({
      url: "http://localhost:8080/api/customer",
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (data) {
        let customerBody = $("#customerTable");
        customerBody.empty();
        const userRole = window.sessionStorage.getItem("loggedUserRole");
        data.forEach((customer, index) => {
          let row = `
                      <tr>
                        <th scope="row">${customer.customerCode}</th>
                        <td>${customer.customerName}</td>
                        <td>${customer.totalPoint}</td>
                        <td>${customer.email}</td>
                    `;
          if(userRole === "ADMIN" || userRole === "SUPER_ADMIN"){
            row += `<td><button class="btn btn-sm btn-primary customerEditBtn" data-id="${customer.customerCode}">Edit</button></td>
                   `;
          }
          row += `</tr>`;
          customerBody.append(row);
        });
        $(".customerEditBtn").on("click", function (e) {
          e.preventDefault();
          const code = $(this).data("id");
          fetchCustomerDetailsByEmail(code);
        });
      },
      error: function (error) {
        console.error("Error fetching data:", error);
      },
    });
  }

  function fetchCustomerDetailsByEmail(code) {
    $.ajax({
      url: `http://localhost:8080/api/customer/code/${code}`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (customer) {
        $("#existCustomerName").val(customer.customerName);
        $("#existGender option").each(function () {
          if ($(this).text() == customer.gender) {
            $(this).prop("selected", true);
          }
        });
        $("existingDOB").val(customer.dob);
        $("#existAddressLine1").val(customer.addressLine1);
        $("#existAddressLine2").val(customer.addressLine2);
        $("#existCity").val(customer.city);
        $("#existState").val(customer.state);
        $("#existPostalCode").val(customer.postalCode);
        $("#existMobileNo").val(customer.contactNo);
        $("#existEmail").val(customer.email);
        $("#updateExistCustomer").modal("show");
      },
      error: function (error) {
        console.error("Error fetching customer details:", error);
      },
    });
  }

  $("#saveChanges").on("click", function () {
    let email = $("#existEmail").val();
    $.ajax({
      url: `http://localhost:8080/api/customer/${email}`,
      type: "PATCH",
      data: JSON.stringify({
        customerName: $("#existCustomerName").val(),
        dob: $("#existingDOB").val(),
        addressLine1: $("#existAddressLine1").val(),
        addressLine2: $("#existAddressLine2").val(),
        city: $("#existCity").val(),
        state: $("#existState").val(),
        postalCode: $("#existPostalCode").val(),
        gender: $("#existGender").val(),
        contactNo: $("#existMobileNo").val(),
        email: email,
      }),
      contentType: "application/json",
      dataType: "json",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("Customer details updated successfully!");
        $("#editCustomerModal").modal("hide");
        fetchCustomers();
      },
      error: function (error) {
        console.error("Error updating supplier details:", error);
        alert("Failed to update supplier details. Please try again.");
      },
    });
  });

  $("#addNew").on("click", function () {
    $.ajax({
      url: `http://localhost:8080/api/customer`,
      type: "POST",
      data: JSON.stringify({
        customerCode: $("#CustomerCode").val(),
        customerName: $("#CustomerName").val(),
        gender: $("#Gender").val(),
        addressLine1: $("#addSupAddress1").val(),
        addressLine2: $("#addSupAddress2").val(),
        city: $("#addSupAddress3").val(),
        state: $("#addSupAddress4").val(),
        postalCode: $("#addSupAddress5").val(),
        dob: $("#dob").val(),
        contactNo: $("#addContact").val(),
        email: $("#email").val(),
      }),
      contentType: "application/json",
      dataType: "json",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("New Loyality Customer is added successfully!");
        $("#addNewCustomer").modal("hide");
        fetchCustomers();
      },
      error: function (error) {
        console.error("Error adding new supplier:", error);
        alert("Failed to add new supplier. Please try again.");
      },
    });
  });

  fetchCustomers();
});
