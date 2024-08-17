



<%@page import="com.techpoint.entities.User"%>
<%@page import="com.techpoint.entities.Post"%>
<%@page import="java.util.List"%>
<%@page import="com.techpoint.entities.Category"%>
<%@page import="com.techpoint.helper.PgmDbConnector"%>
<%@page import="com.techpoint.daos.PostDao"%>




<%
    PostDao d = new PostDao(PgmDbConnector.makeConnection());
    List<Category> catList = d.getAllCategories();
    String selectedCatId = request.getParameter("cid") != null ? request.getParameter("cid") : "0";
    String myPostsSelected = request.getParameter("myPosts") != null ? "myPosts" : "";
    
   int userId = (int) session.getAttribute("userId");
   
    int myPostsCount = d.countPostsByUserId(userId);
    
%>

<main>
    <div class="custom-container mt-5">
        <div class="row">
            <!-- First column -->
            <div class="column1">
                <!-- Categories -->
                <div class="list-group">
                    <a href="?cid=0" data-cat-id="0" class="c-link list-group-item list-group-item-action <%= "0".equals(selectedCatId) ? "active" : ""%>" aria-current="true">
                        All Posts
                        <span class="badge badge-pill badge-primary post-count-badge" data-cat-id="0">
                            <%= d.countAllPost()%>
                        </span>
                    </a>

                    <a href="?myPosts=true" data-cat-id="myPosts" class="c-link list-group-item list-group-item-action <%= "myPosts".equals(myPostsSelected) ? "active" : ""%>" aria-current="true">
                        My Posts
                        <span class="badge badge-pill badge-primary post-count-badge" data-cat-id="myPosts">
                            <%= myPostsCount%>
                        </span>
                    </a>

                    <h5 class="mt-3" style="color: #013d85;">Categories:</h5>

                    <% for (Category catg : catList) {%>
                    <a href="?cid=<%= catg.getCid()%>" data-cat-id="<%= catg.getCid()%>" class="c-link list-group-item list-group-item-action <%= String.valueOf(catg.getCid()).equals(selectedCatId) ? "active" : ""%>">
                        <img src="catpics/<%= catg.getPhoto()%>" class="catg-img" alt="X">
                        <span class="catg-name"><%= catg.getName()%></span>
                        <span class="badge badge-pill badge-primary post-count-badge" data-cat-id="<%= catg.getCid()%>">
                            <%= d.countPostsByCategoryId(catg.getCid())%>
                        </span>
                    </a>
                    <% }%>
                </div>
            </div>

            <!-- Second column -->
            <div class="column2">
                <!-- Loader -->
                <div class="container text-center" id="loader">
                    <i class="fa fa-refresh fa-2x fa-spin"></i>
                    <h4>Loading...</h4>
                </div>

                <!-- Posts dynamic content -->
                <div class="container-fluid" id="posts-container" style="padding-right: 0px!important;"></div>
            </div>
        </div>
    </div>
</main>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="JS/loadPosts.js"></script>
