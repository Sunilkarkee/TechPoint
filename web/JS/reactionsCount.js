/* global Swal */

function doReaction(postId, userId, reactionType) {
    fetch('ReactionServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            'operation': reactionType,
            'uid': userId,
            'pid': postId
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            console.error('Error:', data.error);
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            });
        } else {
            // Update like and dislike counters
            const likeCounter = document.getElementById('like-counter-' + postId);
            const dislikeCounter = document.getElementById('dislike-counter-' + postId);

            if (likeCounter && dislikeCounter) {
                likeCounter.textContent = data.likeCount;
                dislikeCounter.textContent = data.dislikeCount;

                // Get the like and dislike icons
                const likeIcon = likeCounter.previousElementSibling;
                const dislikeIcon = dislikeCounter.previousElementSibling;

                if (likeIcon && dislikeIcon) {
                    // Reset icons
                    likeIcon.classList.remove('fa-thumbs-up', 'fa-thumbs-o-up');
                    dislikeIcon.classList.remove('fa-thumbs-down', 'fa-thumbs-o-down');

                    // Change icon based on the reaction
                    if (reactionType === 'LIKE') {
                        likeIcon.classList.add('fa-thumbs-up');
                    } else if (reactionType === 'DISLIKE') {
                        dislikeIcon.classList.add('fa-thumbs-down');
                    }
                }
            }
        }
    })
    .catch(error => {
        console.error('Fetch error:', error);
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Failed to process your request!'
        });
    });
}
