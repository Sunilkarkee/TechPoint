$(document).ready(function () {
    console.log("Document ready. Initiating AJAX call.");

    const urlParams = new URLSearchParams(window.location.search);
    const catId = urlParams.get('cid') || 0;
    const myPosts = urlParams.get('myPosts') !== null;

    console.log("Current catId:", catId);
    console.log("Is 'My Posts' selected?", myPosts);

    // Set the active link and show/hide badges
    $(".c-link").each(function() {
        const linkCatId = $(this).data('cat-id');
        const isActive = (linkCatId == catId) || (linkCatId === "myPosts" && myPosts) || (linkCatId === "0");

        $(this).toggleClass('active', isActive);
        $(this).find('.post-count-badge').toggle(isActive);
    });

    const initialElement = myPosts 
        ? $(`.c-link[data-cat-id='myPosts']`)[0] 
        : $(`.c-link[data-cat-id='${catId || 0}']`)[0];

    getPosts(catId, myPosts, initialElement);

    function getPosts(catId, myPosts, element) {
        $(".c-link").removeClass('active');
        $(element).addClass("active");

        const queryString = myPosts ? `?myPosts=true` : `?cid=${catId}`;
        history.pushState(null, '', queryString);

        $("#loader").show();
        $("#posts-container").empty();

        $.ajax({
            url: "loadPosts.jsp",
            data: { cid: catId, myPosts: myPosts },
            success: function (data) {
                console.log("AJAX call successful. Data received:", data);
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
});
