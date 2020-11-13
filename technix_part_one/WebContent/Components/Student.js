$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateStudentForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var method = ($("#hidStudentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "technixAPI",
		type : method,
		data : $("#formStudent").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onDoctorPaymentSaveComplete(response.responseText, status);
		}
	});
});

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidStudentIDSave").val($(this).closest("tr").find('#hidStudentIDUpdate').val());
	$("#code").val($(this).closest("tr").find('td:eq(0)').text());
	$("#FirstName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#LastName").val($(this).closest("tr").find('td:eq(2)').text());
	$("#DOB").val($(this).closest("tr").find('td:eq(3)').text());
	$("#UserName").val($(this).closest("tr").find('td:eq(4)').text());
	$("#Password").val($(this).closest("tr").find('td:eq(5)').text());
});

function onStudentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divDoctorPaymentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidStudentIDSave").val("");
	$("#formStudent")[0].reset();
}

$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "technixAPI",
		type : "DELETE",
		data : "StudentID=" + $(this).data("studentid"),
		dataType : "text",
		complete : function(response, status)
		{
			onStudentDeleteComplete(response.responseText, status);
		}
	});
});

function onStudentDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divStudentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function validateStudentForm()
{
	// Studentcode
	if ($("#code").val().trim() == "")
	{
		return "Insert code.";
	}
	
	// FirstName
	if ($("#FirstName").val().trim() == "")
	{
		return "Insert FirstName.";
	}
	
	//LastName-------------------------------
	if ($("#LastName").val().trim() == "")
	{
		return "Insert LastName.";
	}
	
	
	
	// DOB------------------------
	if ($("#DOB").val().trim() == "")
	{
		return "! please Insert the DOB in this Format 'YYYY/MM/DD'";
	}
	
	
	//UserName-------------------------------
	if ($("#UserName").val().trim() == "")
	{
		return "Insert UserName.";
	}
	
	//Password-------------------------------
	if ($("#Password").val().trim() == "")
	{
		return "Insert Pssword.";
	}
	return true;
}

function valdate() {
    var regdate = /^(19[0-9][0-9]|20[0-9][0-9])\/(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/;
    if (formStudent.DOB.value.match(regdate)) {
      return true;
    } else {
      alert("! please Insert the DOB in this Format 'YYYY/MM/DD'");
      formStudent.DOB.value = "";
      formStudent.DOB.focus();
      return false;}
    }