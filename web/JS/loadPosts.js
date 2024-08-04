$(document).ready(function () {
    console.log("Document ready. Initiating AJAX call.");

    const urlParams = new URLSearchParams(window.location.search);
    const catId = urlParams.get('cid') || 0;

    console.log("Current catId:", catId);

    // Set the active link based on the category ID
    $(".c-link").each(function() {
        const linkCatId = $(this).data('cat-id');
        console.log("linkCatId:", linkCatId);
        if (linkCatId == catId) {
            $(this).addClass('active');
            console.log("Match found and 'active' class added.");
        } else {
            $(this).removeClass('active');
        }
    });

    // If there's a category ID, use it; otherwise, default to "All Posts"
    const initialElement = catId ? $(`.c-link[data-cat-id='${catId}']`)[0] : $('.c-link')[0];
    getPosts(catId, initialElement);

    // Function to get posts
    function getPosts(catId, element) {
        $(".c-link").removeClass('active');

        // Link becomes active immediately on click
        $(element).addClass("active");

        history.pushState(null, '', `?cid=${catId}`);

        $("#loader").show();
        $("#posts-container").html("");

        $.ajax({
            url: "loadPosts.jsp",
            data: {cid: catId},
            success: function (data, textStatus, jqXHR) {
                console.log("AJAX call successful. Data received:");
                $("#loader").hide();
                $("#posts-container").show();
                $("#posts-container").html(data);
                console.log("Content loaded into #posts-container");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error loading posts:", textStatus, errorThrown);
                $("#loader").hide();
                $("#posts-container").html("<p class='text-danger'>Error loading posts. Please try again later.</p>");
            }
        });
    }
});
