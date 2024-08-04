<%@page import="java.util.List"%>
<%@page import="com.programmershub.entities.Category"%>
<%@page import="com.programmershub.helper.PgmDbConnector"%>
<%@page import="com.programmershub.daos.PostDao"%>
<link href="css/categoriesDisplay.css" rel="stylesheet" type="text/css"/>


<%
    PostDao d = new PostDao(PgmDbConnector.makeConnection());
    List<Category> catList = d.getAllCategories();
    String selectedCatId = request.getParameter("cid") != null ? request.getParameter("cid") : "0";
%>

<main>
    <div class="custom-container mt-4">
        <div class="row">
            <!-- First column -->
            <div class="column1">
                <!-- Categories -->
                <div class="list-group">
                    <a href="?cid=0" data-cat-id="0" class="c-link list-group-item list-group-item-action <%= "0".equals(selectedCatId) ? "active" : "" %>" aria-current="true">
                        All Posts
                    </a>

                    <!-- Fetching categories using PostDao method getAllCategories -->
                    <% for (Category catg : catList) { %>
                        <a href="?cid=<%= catg.getCid() %>" data-cat-id="<%= catg.getCid() %>" class="c-link list-group-item list-group-item-action <%= String.valueOf(catg.getCid()).equals(selectedCatId) ? "active" : "" %>">
                            <div class="catg-container">
                                <img src="catpics/<%= catg.getPhoto() %>" class="catg-img" alt="X">
                                <span class="catg-name"><%= catg.getName() %></span>
                            </div>
                        </a>
                    <% } %>
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

<!-- Ajax -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="JS/loadPosts.js"></script>
