/* global Swal */

$(document).ready(function () {
    $('#reg-form').on('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const username = $('#username').val().trim();
        const email = $('#email').val().trim();
        const password = $('#password').val().trim();
        const gender = $('#gender').val();
        const phoneNumber = $('#phone').val().trim();
        const termsAccepted = $('#terms').prop('checked'); // Checkbox validation

        if (!username || !email || !password || !phoneNumber) {
            Swal.fire({
                title: "Oops!",
                text: "Please fill out all required fields."
            });
            return; // Exit function if validation fails
        }

        if (gender === 'none') {
            Swal.fire({
                title: "Oops!",
                text: "Please select your gender."
            });
            return;
        }

        if (!termsAccepted) {
            Swal.fire({
                title: "Oops!",
                text: "Please accept terms and conditions."
            });
            return;
        }

        // Create FormData object from the form
        let form = $(this).serialize();

        // Hide the submit button and show the loader
        $('#custom-button').hide();
        $('#loader').removeClass('d-none');

        $.ajax({
            url: "RegisterServlet", // Servlet endpoint
            type: 'POST',
            data: form, // Pass FormData object as the data to be sent
            success: function (response) {
                console.log('Form submitted successfully.');
                console.log(response);

                // Show the submit button and hide the loader
                $('#custom-button').show();
                $('#loader').addClass('d-none');

                // Reset form after successful submission
                if (response.includes('Registration successful.')) {
                    $('#reg-form')[0].reset();
                    $('#gender').val('none');
                    Swal.fire({
                        title: "Congrats!",
                        text: "Registered Successfully, Redirecting to login page...",
                        icon: "success"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location = "login.jsp";
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
            error: function (jqxhr, textstatus, errorThrown) {
                console.error('Form submission failed:', errorThrown);
                $('#response').html('Form Submission Failed: ' + errorThrown);
                $('#response').html('Server error: ' + jqxhr.responseText);

                // Show the submit button and hide the loader
                $('#custom-button').show();
                $('#loader').addClass('d-none');
            }
        });
    });
});
