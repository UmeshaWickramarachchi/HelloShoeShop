let currentUser = null;
$(document).ready(function () {
  $("#login-form").submit(function (e) {
    e.preventDefault();
    const email = $("#email").val();
    const password = $("#password").val();
    $.ajax({
      url: `http://localhost:8080/api/auth/login`,
      type: "POST",
      data: JSON.stringify({
        email: email,
        password: password,
      }),
      contentType: "application/json",
      dataType: "json",
    })
      .done(function (data) {
        window.sessionStorage.setItem("jwt", data.jwt);
        loadUserDetails();
      })
      .fail(function (error) {
        console.error("Error:", error);
      });
  });

  function loadUserDetails() {
    const serviceToken = window.sessionStorage.getItem("jwt");
    $.ajax({
      url: `http://localhost:8080/api/auth/profile`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + serviceToken,
      }
    })
      .done(function (user) {
        console.log("Logged-in User:", user);
        window.sessionStorage.setItem("loggedUser",user.employeeName);
        window.sessionStorage.setItem("loggedUserRole",user.appUser.role);
        navigateToDashboard(user);
      })
      .fail(function (error) {
        console.error("Error fetching user details:", error);
      });
  }

  function navigateToDashboard(user) {
    if (user.appUser.role === "ADMIN" || user.appUser.role === "SUPER_ADMIN" ) {
      window.location.href = "/adminDashboard.html";
    } else if (user.appUser.role === "USER") {
      window.location.href = "/dashboard.html";
    } else {
      console.error("Unknown user role:", user.role);
      alert("Unknown user role. Please contact support.");
    }
  }

});

function logout(){
  window.sessionStorage.removeItem("jwt");
  window.sessionStorage.removeItem("loggedUser");
  window.sessionStorage.removeItem("loggedUserRole");
  currentUser = null;
  window.location.href = "/index.html";
}
