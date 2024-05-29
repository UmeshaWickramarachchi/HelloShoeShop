$(document).ready(function () {
  let selectedItems = [];
  let orderNo = 0;
  $("#createOrder").on("click", function handleNewInventoryForm() {
    fetchItems();
    $("#createNewOrder").modal("show");
  });

  function fetchSales() {
    $.ajax({
      url: "http://localhost:8080/api/sale",
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (data) {
        let salesBody = $("#orderBody");
        salesBody.empty(); // Clear the table body

        data.forEach((sale, index) => {
          let row = `
                    <tr>
                      <th scope="row">${sale.orderNo}</th>
                      <td>${sale.customerName}</td>
                      <td>${sale.totalPrice}</td>
                      <td>${sale.paymentMethod}</td>
                      <td>${sale.purchaseDate}</td>
                      <td>${sale.status}</td>
                      <td><button class="btn btn-sm btn-primary refund" data-id="${sale.orderNo}">Make Refund</button></td>
                    </tr>
                  `;
          salesBody.append(row);
        });
        $(".refund").on("click", function (e) {
          e.preventDefault();
          const orderNo = $(this).data("id");
          fetchOrderByNo(orderNo);
        });
      },
      error: function (error) {
        console.error("Error fetching data:", error);
      },
    });
  }

  function fetchItems() {
    $.ajax({
      url: `http://localhost:8080/api/inventory`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (items) {
        const select = document.getElementById("selectItem");
        select.innerHTML = ""; // Clear existing options

        items.forEach((item) => {
          const option = document.createElement("option");
          option.value = item.name;
          option.text = `${item.itemCode} - ${item.category} - ${item.size}`;
          select.appendChild(option);
        });
      },
      error: function (error) {
        console.error("Error fetching employee details:", error);
      },
    });
  }

  $("#increaseQuantity").on("click", function (e) {
    e.preventDefault();
    const quantityInput = $("#quantity");
    let quantity = parseInt(quantityInput.val());
    quantity++;
    quantityInput.val(quantity);
  });

  // Function to handle decreasing the quantity
  $("#decreaseQuantity").on("click", function (e) {
    e.preventDefault();
    const quantityInput = $("#quantity");
    let quantity = parseInt(quantityInput.val());
    if (quantity > 1) {
      quantity--;
      quantityInput.val(quantity);
    }
  });

  $("#addItem").on("click", function (e) {
    e.preventDefault();
    const selectedItemText = $("#selectItem option:selected").text();
    const parts = selectedItemText.split(" - ");
    const itemCode = parts[0];
    const category = parts[1];
    const size = parts[2];
    const quantity = $("#quantity").val();

    const selectedItem = {
      itemCode: itemCode,
      size: size,
      category: category,
      qty: parseInt(quantity),
    };
    selectedItems.push(selectedItem);
    updateSelectedItemsList();
  });

  function updateSelectedItemsList() {
    const selectedItemsList = $("#selectedItems");
    selectedItemsList.empty();
    selectedItems.forEach((item) => {
      const listItem = `<li>${item.itemCode} - ${item.category} - ${item.size} - Quantity: ${item.qty}</li>`;
      selectedItemsList.append(listItem);
    });
  }

  $("#searchCustomer").on("click", function () {
    let contactNo = $("#contactNo").val();
    $.ajax({
      url: `http://localhost:8080/api/customer/contact/${contactNo}`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        if (response) {
          console.log(response);
          $("#name").val(response.customerName);
        } else {
          $("#name").val("");
          alert("Customer not found. Please enter the customer name.");
        }
      },
      error: function (error) {
        console.error("Error fetch customer:", error);
        alert("Failed to fetche customer. Please try again.");
      },
    });
  });

  $("#pay").on("click", function () {
    const cashier = window.sessionStorage.getItem("loggedUser");
    let loyality = 0;
    if ($("#hasLoyalityCard").val() === "Has") {
      loyality = 1;
    } else {
      loyality = 0;
    }
    orderNo = generateOrderNumber();
    $.ajax({
      url: `http://localhost:8080/api/sale`,
      type: "POST",
      data: JSON.stringify({
        orderNo: orderNo,
        customerName: $("#name").val(),
        contactNo: $("#contactNo").val(),
        hasLoyalityCard: loyality,
        cashierName: cashier,
        items: selectedItems,
      }),
      contentType: "application/json",
      dataType: "json",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        $("#createNewOrder").modal("hide");
        $("#Payment").modal("show");
        $("#Payment").on("shown.bs.modal", function () {
          fetchOrderByNo(orderNo);
        });
      },
      error: function (error) {
        console.error("Error fetch customer:", error);
        alert("Failed to fetche customer. Please try again.");
      },
    });
  });

  $("#addNew").on("click", function () {
    const cardFields = document.getElementById("cardFields");
    const paymentCard = document.getElementById("paymentCard").checked;
    if (paymentCard) {
      $.ajax({
        url: `http://localhost:8080/api/sale/place-order`,
        type: "POST",
        data: JSON.stringify({
          orderNo: orderNo,
          paymentMethod: "Card",
          cardNo: $("#cardNo").val(),
          exDate: $("#expireDate").val(),
          cnn: $("#cnn").val(),
        }),
        contentType: "application/json",
        dataType: "json",
        headers: {
          Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
        },
        success: function (response) {
          console.log(response);
          alert("Order Placed successfully!");
          $("#Payment").modal("hide");
          fetchSales();
        },
        error: function (error) {
          console.error("Error place Order:", error);
          alert("Failed to place the order. Please try again.");
        },
      });
    }
    else{
      $.ajax({
        url: `http://localhost:8080/api/sale/place-order`,
        type: "POST",
        data: JSON.stringify({
          orderNo: orderNo,
          paymentMethod: "Cash",
        }),
        contentType: "application/json",
        dataType: "json",
        headers: {
          Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
        },
        success: function (response) {
          console.log(response);
          alert("Order Placed successfully!");
          $("#Payment").modal("hide");
          fetchSales();
          clearFields();
        },
        error: function (error) {
          console.error("Error place Order:", error);
          alert("Failed to place the order. Please try again.");
        },
      });
    }
  });

  function fetchOrderByNo(orderNo) {
    console.log(orderNo);
    $.ajax({
      url: `http://localhost:8080/api/sale/${orderNo}`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        if ($("#Payment").is(":visible")) {
          console.log(response.totalPrice);
          $("#billAmount").val(response.totalPrice);
        } else {
          $("#inputOrderNo").val(response.orderNo);
          $("#makeRefund").modal("show");
        }
      },
      error: function (error) {
        console.error("Error fetching order:", error);
        alert("Failed to fetch order. Please try again.");
      },
    });
  }

  $("#payment").on("input", function() {
    var totalCost = parseFloat($("#billAmount").val());
    var paymentAmount = parseFloat($(this).val());
    var balance = paymentAmount - totalCost;
    $("#balance").val(balance.toFixed(2));
});

  function clearFields() {
    $("#name").val("");
    $("#contactNo").val("");
    $("#cardNo").val("");
    $("#expireDate").val("");
    $("#cnn").val("");
    selectedItems = [];
    $("#selectedItems").empty();
    $("#paymentCard").prop("checked", false);
    $("#paymentCash").prop("checked", false);
    $('#billAmount').val("");
    $('#payment').val("");
    $('#balance').val("");
}

  fetchSales();
});

function generateOrderNumber() {
  const date = new Date();
  const pad = (num, size) => {
    let s = num.toString();
    while (s.length < size) s = "0" + s;
    return s;
  };

  const year = date.getFullYear();
  const month = pad(date.getMonth() + 1, 2);
  const day = pad(date.getDate(), 2);
  const hours = pad(date.getHours(), 2);
  const minutes = pad(date.getMinutes(), 2);
  const seconds = pad(date.getSeconds(), 2);

  const timestamp = `${year}${month}${day}${hours}${minutes}${seconds}`;

  const orderPart = pad(Math.floor(Math.random() * 10000), 4);

  return `ORD${timestamp}${orderPart}`;
}

function toggleCardFields() {
  const cardFields = document.getElementById("cardFields");
  const paymentCard = document.getElementById("paymentCard").checked;
  if (paymentCard) {
    cardFields.style.display = "block";
  } else {
    cardFields.style.display = "none";
  }
}
