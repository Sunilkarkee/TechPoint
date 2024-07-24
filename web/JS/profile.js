/* global Swal */

$(document).ready(function () {
    let editStatus = false;

    $("#edit-btn").click(function () {
        if (!editStatus) {
            $('#profile-details').hide();
            $('#profile-edit').show();
            $("#edit-btn").text('Cancel');
            editStatus = true;
        } else {
            $('#profile-details').show();
            $('#profile-edit').hide();
            $("#edit-btn").text('Edit');
            editStatus = false;
        }
    });

    $("#editForm").on('submit', function (event) {
        event.preventDefault();

        // Use AJAX to submit the form
        $.ajax({
            type: 'POST',
            url: 'EditServlet', // Change this URL to your servlet URL
            data: new FormData(this), // Send form data
            contentType: false,
            processData: false,
            success: function (response) {
                // Handle success response
                if (response.includes('Update successful.')) {
                    $('#editForm')[0].reset();
                    Swal.fire({
                        title: "Congrats!",
                        text: "Update Successful.",
                        icon: "success"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location = "profile.jsp"; // Redirect if needed
                        }
                    });
                } else {
                    Swal.fire({
                        title: "Oops!",
                        text: response,
                        icon: "error"
                    });
                }
            },
            error: function (xhr, status, error) {
                // Handle error response
                Swal.fire({
                    title: "Error!",
                    text: "Something went wrong. Please try again.",
                    icon: "error"
                });
            }
        });
    });
});
       