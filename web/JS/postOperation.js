$(document).ready(function () {
    $(document).on("submit", "form[id^='edit-post-form-']", function (event) {
        event.preventDefault();
        console.log("Edit form submitted");

        let form = new FormData(this);
        let currentForm = $(this); // Save reference to the current form

        // Now requesting to server
        $.ajax({
            url: "UpdatePostServlet",
            type: 'POST',
            data: form,
            success: function (response) {
                console.log("Success:", response);

                if (response.trim().includes('Update successful')) {
                    Swal.fire({
                        title: "Updated!",
                        text: "Post updated successfully",
                        icon: "success"
                    }).then(() => {
                        currentForm.closest('.modal').modal('hide'); 
                        
                        let defaultImageSrc = currentForm.find('input[name="oldPic"]').val();
                        currentForm.find('img.card-img-top').attr('src', 'blogsPics/' + defaultImageSrc);
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
                Swal.fire({
                    title: "Error!",
                    text: "There was an error updating the post.",
                    icon: "error"
                });
            },
            processData: false,
            contentType: false
        });
    });

    // Image preview functionality
    $(document).on('change', "input[type='file'][name='pic']", function () {
        const fileInput = this;
        const file = fileInput.files[0];
        const form = $(fileInput).closest('form');
        const previewImg = form.find('img.card-img-top'); // Target the image preview element within the same form

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewImg.attr('src', e.target.result);
            };
            reader.readAsDataURL(file);
        } else {
            // Use the old image if no new file is selected
            let oldImageSrc = form.find('input[name="oldPic"]').val();
            previewImg.attr('src', 'blogsPics/' + oldImageSrc);
        }
    });

    $('#dopost-Modal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus');
    });
});
