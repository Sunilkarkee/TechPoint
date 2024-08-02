/* global Swal */

$(document).ready(function () {
    $("#addpostForm").on("submit", function (event) {
        event.preventDefault();
        console.log("Form submitted");
        
//         JS for adding post
        
        let form = new FormData(this);
        // now requesting to server
        $.ajax({
            url: "AddPostServlet",
            type: 'POST',
            data: form,
            success: function (response) {
                console.log("Success:", response);
                // You can add more logic here to handle the response, like showing a success message or redirecting the user

                if (response.trim().includes('Post added successfully')) {
                    $('#addpostForm')[0].reset();
                    // Reset select field
                    $('#postcat').prop('selectedIndex', 0);
                    // Reset custom dropdown
                    $('#selected-category-text').text('---select category---');
                    Swal.fire({
                        title: "Congrats!",
                        text: "Post added Sucessfully",
                        icon: "success"
                    });
                } else {
                    Swal.fire({
                        title: "Oops!",
                        text: response,
                        icon: "error"
                    });
                }
            },
            error: function (response) {
                console.error("Error:", response);
                // You can add more logic here to handle the error, like showing an error message
            },
            processData: false,
            contentType: false
        });
    });
    
    $('#dopost-Modal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus');
    });
});

function toggleDropdown() {
    var dropdownList = document.getElementById("dropdown-list");
    if (dropdownList.style.display === "none" || dropdownList.style.display === "") {
        dropdownList.style.display = "block";
    } else {
        dropdownList.style.display = "none";
    }
}

function selectCategory(id, name, imgSrc) {
    var select = document.getElementById("postcat");
    var options = select.options;
    for (var i = 0; i < options.length; i++) {
        if (options[i].value === id) {
            options[i].selected = true;
            break;
        }
    }

    var displayText = document.getElementById("selected-category-text");
    displayText.innerHTML = '<img src="' + imgSrc + '" alt="' + name + '" style="width: 20px; height: 20px; margin-right: 10px;">' + name;
    var dropdownList = document.getElementById("dropdown-list");
    dropdownList.style.display = "none";
}

document.addEventListener('click', function(event) {
    var isClickInside = document.querySelector('.custom-dropdown').contains(event.target);
    if (!isClickInside) {
        document.getElementById("dropdown-list").style.display = "none";
    }
});
