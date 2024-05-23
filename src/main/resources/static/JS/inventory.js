function handleImagePreview(id, previewId) {
  document.getElementById(id).addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (event) {
        document
          .getElementById(previewId)
          .setAttribute("src", event.target.result);
        document.getElementById(previewId).style.display = "block";
      };
      reader.readAsDataURL(file);
    } else {
      document.getElementById(previewId).setAttribute("src", "");
      document.getElementById(previewId).style.display = "none";
    }
  });
}

$(document).ready(function () {
  let image;

  $("#addInventoryFormOpen").on("click",function handleNewInventoryForm() {
    handleImagePreview("addInventoryImage", "addpreview");
    fetchSuppliers();
    $("#addNewInventory").modal("show");
  })
  function fetchInventories() {
    $.ajax({
      url: "http://localhost:8080/api/inventory",
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (data) {
        let inventoryBody = $("#inventoryBody");
        inventoryBody.empty(); // Clear the table body

        data.forEach((inventory, index) => {
          let row = `
                  <tr>
                    <th scope="row"><img src="data:image/jpeg;base64,${inventory.itemPic}" style="width: 50px; height: 50px;"/></th>
                    <th scope="row">${inventory.itemCode}</th>
                    <td>${inventory.category}</td>
                    <td>${inventory.size}</td>
                    <td>${inventory.qty}</td>
                    <td>${inventory.unitPriceSale}</td>
                    <td>${inventory.unitPriceBuy}</td>
                    <td>${inventory.expectedProfit}</td>
                    <td>${inventory.status}</td>
                    <td><button class="btn btn-sm btn-primary inventoryEditBtn" data-id="${inventory.itemCode}">Edit</button></td>
                  </tr>
                `;
          inventoryBody.append(row);
        });
        $(".inventoryEditBtn").on("click", function (e) {
          e.preventDefault();
          const inventoryCode = $(this).data("id");
          fetchInventoryDetailsByCode(inventoryCode);
        });
      },
      error: function (error) {
        console.error("Error fetching data:", error);
      },
    });
  }

  function fetchSuppliers() {
    $.ajax({
      url: `http://localhost:8080/api/supplier`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (suppliers) {
        const supplierSelect = document.getElementById("addSupplierCode");
        supplierSelect.innerHTML = ""; // Clear existing options

        suppliers.forEach((supplier) => {
          const option = document.createElement("option");
          option.value = supplier.supplierCode;
          option.textContent = supplier.supplierCode;
          supplierSelect.appendChild(option);
        });
      },
      error: function (error) {
        console.error("Error fetching employee details:", error);
      },
    });
  }

  function fetchInventoryDetailsByCode(inventoryCode) {
    $.ajax({
      url: `http://localhost:8080/api/inventory/${inventoryCode}`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (inventory) {
        $("#existItemCode").val(inventory.itemCode);
        $("#existItemDescription").val(inventory.itemDesc);
        $("#existNewItemCategory").val(inventory.category);
        $("#existNewItemShoeSize").val(inventory.size);
        $("#existSupplierCode").val(inventory.supplierCode);
        $("#existSaleUnitPrice").val(inventory.unitPriceSale);
        $("#existBuyUnitPrice").val(inventory.unitPriceBuy);
        $("#existNewItemQty").val(inventory.qty);
        $("#existSupplierName").val(inventory.supplierName);
        $("#existProfite").val(inventory.expectedProfit);
        $("#existProfitMargin").val(inventory.profitMargin);
        $("#existStatus").val(inventory.status);

        const byteCharacters = atob(inventory.itemPic);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);

        // Step 2: Create Blob object from binary data
        const blob = new Blob([byteArray], { type: "image/jpeg" });

        // Step 3: Create File object with desired filename and MIME type
        const file = new File([blob], "inventoryPic.jpg", {
          type: "image/jpeg",
        });
        image = file;
        $("#editpreview").attr(
          "src",
          "data:image/jpeg;base64," + inventory.itemPic
        );

        handleImagePreview("inputItemImage", "editpreview");
        $("#editInventoryModal").modal("show");
      },
      error: function (error) {
        console.error("Error fetching employee details:", error);
      },
    });
  }

  $("#saveChanges").on("click", function () {
    const itemimage =
      document.getElementById("inputItemImage").files[0] === undefined
        ? image
        : document.getElementById("inputItemImage").files[0];
    const formData = new FormData();
    formData.append("itemPic", itemimage);
    formData.append("itemDesc", $("#existItemDescription").val());
    formData.append("category", $("#existNewItemCategory").val());
    formData.append("size", $("#existNewItemShoeSize").val());
    formData.append("supplierCode", $("#existSupplierCode").val());
    formData.append("unitPriceSale", $("#existSaleUnitPrice").val());
    formData.append("unitPriceBuy", $("#existBuyUnitPrice").val());
    formData.append("qty", $("#existNewItemQty").val());
    const itemCode = $("#existItemCode").val();

    $.ajax({
      url: `http://localhost:8080/api/inventory/${itemCode}`,
      type: "PATCH",
      data: formData,
      processData: false,
      contentType: false,
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("Inventory details updated successfully!");
        $("#editInventoryModal").modal("hide");
        fetchInventories();
        fetchInventoryDetailsByCode();
      },
      error: function (error) {
        console.error("Error updating inventory details:", error);
        alert("Failed to update inventory details. Please try again.");
      },
    });
  });

  $("#addNew").on("click", function () {
    const imageFile = document.getElementById("addInventoryImage").files[0];
    const formData = new FormData();
    formData.append("itemCode", $("#inputNewItemCode").val());
    if (imageFile) {
      formData.append("itemPic", imageFile);
    }
    formData.append("itemDesc", $("#inputNewItemDescription").val());
    formData.append("category", $("#inputNewItemCategory").val());
    formData.append("size", $("#inputNewItemShoeSize").val());
    formData.append("supplierCode", $("#addSupplierCode").val());
    formData.append("unitPriceSale", $("#addSaleUnitPrice").val());
    formData.append("unitPriceBuy", $("#addBuyUnitPrice").val());
    formData.append("qty", $("#addQty").val());
    $.ajax({
      url: `http://localhost:8080/api/inventory`,
      type: "POST",
      data: formData,
      processData: false,
      contentType: false,
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("New Item is added successfully!");
        $("#addNewInventory").modal("hide");
        fetchInventories();
      },
      error: function (error) {
        console.error("Error adding new inventory:", error);
        alert("Failed to add new inventory. Please try again.");
      },
    });
  });

  fetchInventories();
});
