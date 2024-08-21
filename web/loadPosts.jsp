<%@page import="com.techpoint.entities.Category"%>
<%@page import="com.techpoint.entities.Post"%>
<%@page import="com.techpoint.daos.PostDao"%>
<%@page import="com.techpoint.daos.ReactionDao"%>
<%@page import="com.techpoint.entities.Reaction"%>
<%@page import="com.techpoint.entities.User"%>
<%@page import="com.techpoint.helper.PgmDbConnector"%>
<%@page import="java.util.List"%>

<%
    // Check if the user is logged in
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Initialize DAOs
    PostDao postDao = new PostDao(PgmDbConnector.makeConnection());
    ReactionDao reactionDao = new ReactionDao(PgmDbConnector.makeConnection());

    // Get category ID and myPosts flag from the request parameters
    int categoryId = request.getParameter("cid") != null ? Integer.parseInt(request.getParameter("cid")) : 0;
    boolean isMyPosts = "true".equals(request.getParameter("myPosts"));
    int userId = user.getId();

    // Fetch posts based on the criteria
    List<Post> posts = isMyPosts ? postDao.getPostByUserId(userId)
            : (categoryId == 0) ? postDao.getAllPosts()
                    : postDao.getPostsByCategoryId(categoryId);

    // If no posts are found, display a message
    if (posts.isEmpty()) {
        out.println("<h5 class='display-5 text-center'>No Posts yet in this Category</h5>");
        return;
    }
%>

<div class="row no-gutters">
    <% for (Post post : posts) {
            boolean canEdit = user.getId() == post.getUserId() && isMyPosts;
    %>
    <div class="b-col col-md-4 post-item" data-post-id="<%= post.getPId() %>">
        <div class="b-card card mb-4">
            <img style="max-width:100%; max-height: 400px" src="blogsPics/<%= post.getPPic()%>" class="card-img-top" alt="Post Image">
            <div class="card-body">
                <h5 class="card-title"><%= post.getPTitle()%></h5>
                <p class="card-text"><%= post.getPContent()%></p>
                <div class="card-footer text-center border-0">
                    <!-- Like Button -->
                    <a href="#!" onclick="doReaction(<%= post.getPId()%>, <%= user.getId()%>, 'LIKE')" class="b-link btn cstm-clr text-white btn-sm">
                        <i class="fa fa-thumbs-o-up"></i>
                        <span id="like-counter-<%= post.getPId()%>"><%= reactionDao.countReactionsByType(post.getPId(), Reaction.ReactionType.LIKE)%></span>
                    </a>

                    <!-- Dislike Button -->
                    <a href="#!" onclick="doReaction(<%= post.getPId()%>, <%= user.getId()%>, 'DISLIKE')" class="b-link btn cstm-clr text-light btn-sm">
                        <i class="fa fa-thumbs-o-down"></i>
                        <span id="dislike-counter-<%= post.getPId()%>"><%= reactionDao.countReactionsByType(post.getPId(), Reaction.ReactionType.DISLIKE)%></span>
                    </a>

                    <a href="displaySinglePost.jsp?postId=<%= post.getPId()%>" class="b-link btn cstm-clr text-white btn-sm">Read More</a>

                    <a href="#!" class="b-link btn cstm-clr text-white btn-sm">
                        <i class="fa fa-commenting-o"></i><span>15</span>
                    </a>

                    <% if (canEdit) {%>
                    <div class="dropdown d-inline-block">
                        <a class="btn cstm-clr text-light custom-dropdown-toggle btn-sm" href="#" id="postdrpddn-<%= post.getPId()%>" data-bs-toggle="dropdown" aria-expanded="false">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                                <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                            </svg>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="postdrpddn-<%= post.getPId()%>">
                            <li><a class="dropdown-item" href="#!" data-bs-toggle="modal" data-bs-target="#edit-post-<%= post.getPId()%>">Edit</a></li>
                            <button class="dropdown-item delete-post" data-post-id="<%= post.getPId() %>">Delete</button>

                            <li><a class="dropdown-item" href="#">Share</a></li>
                        </ul>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>

        <% if (canEdit) {%>
        <!-- Modal for editing the post -->
        <div class="modal fade" id="edit-post-<%= post.getPId()%>" tabindex="-1" aria-labelledby="editPostLabel-<%= post.getPId()%>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editPostLabel-<%= post.getPId()%>">Edit Post</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <form id="edit-post-form-<%= post.getPId()%>" action="UpdatePostServlet" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="<%= post.getUserId()%>">
                            <input type="hidden" name="postId" value="<%= post.getPId()%>">
                            <input type="hidden" name="catId" value="<%= post.getCatId()%>">
                            <input type="hidden" name="oldPic" value="<%= post.getPPic()%>">

                            <!-- Category Selection -->
                            <div class="form-group mb-3">
                                <select name="catId" class="form-control" disabled>
                                    <option value="" disabled>---Select Category---</option>
                                    <%
                                        List<Category> categories = postDao.getAllCategories();
                                        for (Category category : categories) {
                                    %>
                                    <option value="<%= category.getCid()%>" <%= (category.getCid() == post.getCatId()) ? "selected" : ""%>><%= category.getName()%></option>
                                    <% }%>
                                </select>
                            </div>

                            <!-- Post Details -->
                            <div class="container text-center mb-3">
                                <img style="max-width:100%; max-height: 400px" src="blogsPics/<%= post.getPPic()%>" class="card-img-top" alt="Post Image">
                            </div>

                            <div class="form-group mb-3">
                                <input type="text" name="title" class="form-control" placeholder="Enter Post Title" value="<%= post.getPTitle()%>" required>
                            </div>

                            <div class="form-group mb-2">
                                <textarea rows="8" name="content" class="form-control" placeholder="Write your content..." required><%= post.getPContent()%></textarea>
                            </div>

                            <div class="form-group mb-2">
                                <textarea rows="8" name="code" class="form-control" placeholder="Write your code (if any)"><%= post.getPCode()%></textarea>
                            </div>

                            <div class="form-group">
                                <label>Upload your pic:</label>
                                <input type="file" name="pic" class="form-control">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn nav-background text-white">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>
</div>


