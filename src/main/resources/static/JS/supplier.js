$(document).ready(function () {
  $("#addSupplierFormOpen").on("click", function handleNewSupplierForm() {
    $("#addNewSupplier").modal("show");
    const categorySelect = document.getElementById('Category');
    const countryContainer = document.getElementById("countryContainer");
    categorySelect.addEventListener('change', function() {
      const selectedCategory = this.value;

      if (selectedCategory === 'INTERNATIONAL') {
          const countryField = `
              <label for="Country" class="form-label fw-bold">Country</label>
              <input type="text" class="form-control" style="background-color: #e9c46a" id="Country">
          `;
          countryContainer.innerHTML = countryField;
      } else {
          countryContainer.innerHTML = '';
      }
  });
  });
  function fetchSuppliers() {
    $.ajax({
      url: "http://localhost:8080/api/supplier",
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (data) {
        let supplierBody = $("#supplierBody");
        supplierBody.empty(); // Clear the table body

        data.forEach((supplier, index) => {
          let row = `
                    <tr>
                      <th scope="row">${supplier.supplierCode}</th>
                      <td>${supplier.supplierName}</td>
                      <td>${supplier.mobileNo}</td>
                      <td>${supplier.category}</td>
                      <td><button class="btn btn-sm btn-primary supplierEditBtn" data-id="${supplier.email}">Edit</button></td>
                    </tr>
                  `;
          supplierBody.append(row);
        });
        $(".supplierEditBtn").on("click", function (e) {
          e.preventDefault();
          const email = $(this).data("id");
          fetchSupplierDetailsByEmail(email);
        });
      },
      error: function (error) {
        console.error("Error fetching data:", error);
      },
    });
  }

  function fetchSupplierDetailsByEmail(email) {
    $.ajax({
      url: `http://localhost:8080/api/supplier/${email}`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (supplier) {
        $("#existSupplierName").val(supplier.supplierName);
        $("#existCategory option").each(function () {
          if ($(this).text() == supplier.category) {
            $(this).prop("selected", true);
          }
        });
        $("#existAddressLine1").val(supplier.addressLine1);
        $("#existAddressLine2").val(supplier.addressLine2);
        $("#existCity").val(supplier.city);
        $("#existState").val(supplier.state);
        $("#existPostalCode").val(supplier.postalCode);
        $("#existCountry").val(supplier.country);
        $("#existMobileNo").val(supplier.mobileNo);
        $("#existLandLineNo").val(supplier.landLineNo);
        $("#existEmail").val(supplier.email);
        $("#editSupplierModal").modal("show");
      },
      error: function (error) {
        console.error("Error fetching supplier details:", error);
      },
    });
  }

  $("#saveChanges").on("click", function () {
    let country;
    if ($("#existCategory").val() === "INTERNATIONAL") {
      country = $("#existCountry").val();
    } else {
      country = "Sri Lanka";
    }
    const email = $("#existEmail").val();
    $.ajax({
      url: `http://localhost:8080/api/supplier/${email}`,
      type: "PATCH",
      data: JSON.stringify({
        supplierName: $("#existSupplierName").val(),
        category: $("#existCategory").val(),
        addressLine1: $("#existAddressLine1").val(),
        addressLine2: $("#existAddressLine2").val(),
        city: $("#existCity").val(),
        state: $("#existState").val(),
        postalCode: $("#existPostalCode").val(),
        country: country,
        mobileNo: $("#existMobileNo").val(),
        landLineNo: $("#existLandLineNo").val(),
        email: email,
      }),
      contentType: "application/json",
      dataType: "json",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("Supplier details updated successfully!");
        $("#editSupplierModal").modal("hide");
        fetchSuppliers();
      },
      error: function (error) {
        console.error("Error updating supplier details:", error);
        alert("Failed to update supplier details. Please try again.");
      },
    });
  });

  $("#addNew").on("click", function () {
    let country;
    if ($("#existCategory").val() === "INTERNATIONAL") {
      country = $("#Country").val();
    } else {
      country = "Sri Lanka";
    }
    $.ajax({
      url: `http://localhost:8080/api/supplier`,
      type: "POST",
      data: JSON.stringify({
        supplierName: $("#SupplierName").val(),
        category: $("#Category").val(),
        addressLine1: $("#addSupAddress1").val(),
        addressLine2: $("#addSupAddress2").val(),
        city: $("#addSupAddress3").val(),
        state: $("#addSupAddress4").val(),
        postalCode: $("#addSupAddress5").val(),
        country: country,
        mobileNo: $("#addContact1").val(),
        landLineNo: $("#addContact2").val(),
        email: $("#email").val(),
      }),
      contentType: "application/json",
      dataType: "json",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("New Supplier is added successfully!");
        $("#addNewSupplier").modal("hide");
        fetchSuppliers();
      },
      error: function (error) {
        console.error("Error adding new supplier:", error);
        alert("Failed to add new supplier. Please try again.");
      },
    });
  });

  fetchSuppliers();
});
