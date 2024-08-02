/* global Swal */

$(document).ready(function () {
    
    //image preview functionality
    
     $('#profile-pic-preview').attr('src', 'Images/default.png');

    // Image preview functionality
    $('#profile_pic').on('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#profile-pic-preview').attr('src', e.target.result);
            };
            reader.readAsDataURL(file);
        } else {
            $('#profile-pic-preview').attr('src', 'Images/default.png');
        }
    });
    
    
    
    
    
    
    
    
    
    
    
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
        
        const formData = new FormData();
        formData.append('user_name', $('#username').val().trim());
        formData.append('user_email', $('#email').val().trim());
        formData.append('user_password', $('#password').val().trim());
        formData.append('gender', $('#gender').val());
        formData.append('phone_number', $('#phone').val().trim());
        formData.append('about', $('#about').val().trim());
        formData.append('profile_pic', $('#profile_pic')[0].files[0]);

        // Hide the submit button and show the loader
        $('#custom-button').hide();
        $('#loader').removeClass('d-none');

        $.ajax({
            url: "RegisterServlet", // Servlet endpoint
            type: 'POST',
            data: formData, // Pass FormData object as the data to be sent

            processData: false,
            contentType: false,

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
