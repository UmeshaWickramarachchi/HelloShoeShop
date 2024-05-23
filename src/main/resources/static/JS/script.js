$(document).ready(function () {
  const hamburger = document.querySelector("#toggle-btn");
  hamburger.addEventListener("click", function () {
    document.querySelector("#sidebar").classList.toggle("expand");
  });
  // Function to load section content from separate HTML files
  function loadSection(url) {
    $(".section").hide(); // Hide all sections
    $("#loading").show(); // Show loading indicator (optional)

    $.ajax({
      url: url,
      dataType: "html",
      success: function (data) {
        $("#content").html(data);
        $("#loading").hide(); 
      },
      error: function () {
        $("#loading").hide();
        $("#content").html("<p>Error loading section.</p>"); 
      },
    });
  }

  // Load customer section by default
  loadSection("home.html");

  $("#home-nav").click(function (e) {
    e.preventDefault();
    loadSection("home.html");
  });

  $("#customer-nav").click(function (e) {
    e.preventDefault();
    loadSection("customer.html");
  });

  $("#employee-nav").click(function (e) {
    e.preventDefault();
    loadSection("employee.html");
  });

  $("#supplier-nav").click(function (e) {
    e.preventDefault();
    loadSection("supplier.html");
  });

  $("#store-nav").click(function (e) {
    e.preventDefault();
    loadSection("sales.html");
  });

  $("#inventory-nav").click(function (e) {
    e.preventDefault();
    loadSection("inventory_service.html");
  });
  
});
