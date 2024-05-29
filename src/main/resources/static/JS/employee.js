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

function handleNewEmployeeForm() {
  handleImagePreview("addEmployeeImage", "addpreview");
  $("#addNewEmployee").modal("show");
}

$(document).ready(function () {
  let image;
  function fetchEmployees() {
    $.ajax({
      url: "http://localhost:8080/api/employee",
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (data) {
        let employeesBody = $("#employeesBody");
        employeesBody.empty(); // Clear the table body
        const userRole = window.sessionStorage.getItem("loggedUserRole");
        data.forEach((employee, index) => {
          let row = `
            <tr>
              <th scope="row">${employee.employeeCode}</th>
              <td>${employee.employeeName}</td>
              <td>${employee.designation}</td>
              <td>${employee.dateOfJoin}</td>
              <td>${employee.email}</td>
              <td>${employee.contactNo}</td>
          `;
          if (userRole === "ADMIN" || userRole === "SUPER_ADMIN") {
            row += `
              <td><button class="btn btn-sm btn-primary employeeEditBtn" data-id="${employee.email}">Edit</button></td>
            `;
          }

          row += `</tr>`;
          employeesBody.append(row);
        });
        $(".employeeEditBtn").on("click", function (e) {
          e.preventDefault();
          const employeeId = $(this).data("id");
          fetchEmployeeDetails(employeeId);
        });
      },
      error: function (error) {
        console.error("Error fetching data:", error);
      },
    });
  }

  function fetchEmployeeDetails(employeeId) {
    $.ajax({
      url: `http://localhost:8080/api/employee/${employeeId}`,
      type: "GET",
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (employee) {
        $("#inputEmployeeCode").val(employee.employeeCode);
        $("#inputEmployeeName").val(employee.employeeName);
        $("#inputStatus").val(employee.status);
        $("#inputDesignation").val(employee.designation);
        $("#inputDOB").val(employee.dob);
        $("#inputDOJ").val(employee.dateOfJoin);
        $("#inputBranch").val(employee.attachedBranch);
        $("#inputEmployeeAddress1").val(employee.addressLine1);
        $("#inputEmployeeAddress2").val(employee.addressLine2);
        $("#inputEmployeeAddress3").val(employee.city);
        $("#inputEmployeeAddress4").val(employee.state);
        $("#inputEmployeeAddress5").val(employee.postalCode);
        $("#inputEmployeeContact").val(employee.contactNo);
        $("#inputEmail").val(employee.email);
        $("#inputCaseOfEmergency").val(employee.emergancyInformer);
        $("#inputEmergencyContact").val(employee.emergancyContactDetails);

        $("#inputGender option").each(function () {
          console.log($(this).text());
          if ($(this).text() == employee.gender) {
            $(this).prop("selected", true);
          }
        });

        // Set the default selected value for the role field
        $("#inputRole option").each(function () {
          if ($(this).text() == employee.accessRole) {
            $(this).prop("selected", true);
          }
        });
        const byteCharacters = atob(employee.employeePic);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);

        // Step 2: Create Blob object from binary data
        const blob = new Blob([byteArray], { type: "image/jpeg" });

        // Step 3: Create File object with desired filename and MIME type
        const file = new File([blob], "employeePic.jpg", {
          type: "image/jpeg",
        });
        image = file;
        $("#editpreview").attr(
          "src",
          "data:image/jpeg;base64," + employee.employeePic
        );

        handleImagePreview("inputEmployeeImage", "editpreview");
        $("#editEmployeeModal").modal("show");
      },
      error: function (error) {
        console.error("Error fetching employee details:", error);
      },
    });
  }

  $("#saveChanges").on("click", function () {
    const empimage =
      document.getElementById("inputEmployeeImage").files[0] === undefined
        ? image
        : document.getElementById("inputEmployeeImage").files[0];
    const formData = new FormData();
    formData.append("employeePic", empimage);
    formData.append("employeeName", $("#inputEmployeeName").val());
    formData.append("gender", $("#inputGender").val());
    formData.append("status", $("#inputStatus").val());
    formData.append("designation", $("#inputDesignation").val());
    formData.append("accessRole", $("#inputRole").val());
    formData.append("dob", $("#inputDOB").val());
    formData.append("dateOfJoin", $("#inputDOJ").val());
    formData.append("attachedBranch", $("#inputBranch").val());
    formData.append("addressLine1", $("#inputEmployeeAddress1").val());
    formData.append("addressLine2", $("#inputEmployeeAddress2").val());
    formData.append("city", $("#inputEmployeeAddress3").val());
    formData.append("state", $("#inputEmployeeAddress4").val());
    formData.append("postalCode", $("#inputEmployeeAddress5").val());
    formData.append("contactNo", $("#inputEmployeeContact").val());
    formData.append("email", $("#inputEmail").val());
    formData.append("emergancyInformer", $("#inputCaseOfEmergency").val());
    formData.append(
      "emergancyContactDetails",
      $("#inputEmergencyContact").val()
    );
    const email = $("#inputEmail").val();

    $.ajax({
      url: `http://localhost:8080/api/employee/${email}`,
      type: "PATCH",
      data: formData,
      processData: false,
      contentType: false,
      headers: {
        Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
      },
      success: function (response) {
        alert("Employee details updated successfully!");
        $("#editEmployeeModal").modal("hide");
        fetchEmployees(); // Refresh the table
      },
      error: function (error) {
        console.error("Error updating employee details:", error);
        alert("Failed to update employee details. Please try again.");
      },
    });
  });

  fetchEmployees();
});

function saveEmployee() {
  const formData = new FormData();

  // Append the selected image file to the FormData object
  const imageFile = document.getElementById("addEmployeeImage").files[0];
  if (imageFile) {
    formData.append("employeePic", imageFile);
  }

  // Collect other form data
  formData.append(
    "employeeName",
    document.getElementById("addEmployeeName").value
  );
  formData.append("gender", document.getElementById("addGender").value);
  formData.append("status", document.getElementById("addStatus").value);
  formData.append(
    "designation",
    document.getElementById("addDesignation").value
  );
  formData.append("accessRole", document.getElementById("addRole").value);
  formData.append("dob", document.getElementById("addDOB").value);
  formData.append("dateOfJoin", document.getElementById("addDOJ").value);
  formData.append("attachedBranch", document.getElementById("addBranch").value);
  formData.append(
    "addressLine1",
    document.getElementById("addEmployeeAddress1").value
  );
  formData.append(
    "addressLine2",
    document.getElementById("addEmployeeAddress2").value
  );
  formData.append("city", document.getElementById("addEmployeeAddress3").value);
  formData.append(
    "state",
    document.getElementById("addEmployeeAddress4").value
  );
  formData.append(
    "postalCode",
    document.getElementById("addEmployeeAddress5").value
  );
  formData.append(
    "contactNo",
    document.getElementById("addEmployeeContact").value
  );
  formData.append("email", document.getElementById("addEmail").value);
  formData.append(
    "emergancyInformer",
    document.getElementById("addEmergencyInformer").value
  );
  formData.append(
    "emergancyContactDetails",
    document.getElementById("addEmergencyContact").value
  );
  $.ajax({
    url: "http://localhost:8080/api/employee",
    type: "POST",
    data: formData,
    processData: false,
    contentType: false,
    headers: {
      Authorization: "Bearer " + window.sessionStorage.getItem("jwt"),
    },
    success: function (response) {
      alert("Employee added successfully!");
      $("#addNewEmployee").modal("hide");
      form.reset();
      document.getElementById("addpreview").style.display = "none";
      fetchEmployees();
    },
    error: function (error) {
      console.error("Error:", error);
      alert("Failed to add employee. Please try again.");
    },
  });
}
