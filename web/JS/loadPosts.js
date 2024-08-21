/* global Swal */

$(document).ready(function () {
    console.log("Document ready. Initiating AJAX call.");

    // Extract URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const catId = urlParams.get('cid') || 0;
    const myPosts = urlParams.get('myPosts') !== null;

    console.log("Current catId:", catId);
    console.log("Is 'My Posts' selected?", myPosts);

    // Set the active link and show/hide badges
    $(".c-link").each(function() {
        const linkCatId = $(this).data('cat-id');
        const isActive = (linkCatId === catId) || 
                         (linkCatId === "myPosts" && myPosts) || 
                         (linkCatId === "0");

        $(this).toggleClass('active', isActive);
        $(this).find('.post-count-badge').toggle(isActive);
    });

    // Determine which element to activate
    const initialElement = myPosts 
        ? $(`.c-link[data-cat-id='myPosts']`)[0] 
        : $(`.c-link[data-cat-id='${catId}']`)[0];

    // Fetch and display posts
    getPosts(catId, myPosts, initialElement);

    function getPosts(catId, myPosts, element) {
        // Deactivate all links and activate the clicked one
        $(".c-link").removeClass('active');
        $(element).addClass("active");

        // Update query string
        const queryString = myPosts ? `?myPosts=true` : `?cid=${catId}`;
        history.pushState(null, '', queryString);

        // Show loader and clear previous posts
        $("#loader").show();
        $("#posts-container").empty();

        // AJAX request to fetch posts
        $.ajax({
            url: "loadPosts.jsp",
            data: { cid: catId, myPosts: myPosts },
            success: function (data) {
                $("#loader").hide();
                $("#posts-container").html(data).show();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error loading posts:", textStatus, errorThrown);
                $("#loader").hide();
                $("#posts-container").html("<p class='text-danger'>Error loading posts. Please try again later.</p>");
            }
        });
    }

    // Handle delete button click
    $(document).on('click', '.delete-post', function () {
        var postId = $(this).data('post-id');
        var $button = $(this); // Save reference to the clicked button

        $.ajax({
            url: 'DeletePostServlet', 
            type: 'POST',
            data: { postId: postId },
            success: function (response) {
               console.log("from server",response);
                
                
                    Swal.fire({
                        title: "Deleted!",
                        text: "Post deleted successfully",
                        icon: "success"
                    }).then(() => {
                        $button.closest('.post-item').remove(); // Remove the post item from the DOM
                    });
                
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error:", textStatus, errorThrown);
                Swal.fire({
                    title: "Error!",
                    text: "There was an error deleting the post.",
                    icon: "error"
                });
            }
        });
    });
});
