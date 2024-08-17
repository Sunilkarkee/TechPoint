<%@ page import="com.techpoint.entities.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="com.techpoint.helper.PgmDbConnector" %>
<%@ page import="com.techpoint.daos.PostDao" %>



<%
    // Initialize the PostDao object
    PostDao pdo = new PostDao(PgmDbConnector.makeConnection());

    // Retrieve all posts
    List<Post> postList = pdo.getAllPosts();
%>


<div class="custom-container">
    <div class="row mt-4">
        <%
            // Loop through each post and display it
            for (Post pst : postList) {
        %>
        <div class="col-md-4 ">
            <div class="card mx-3 my-4">
                <img src="blogsPics/<%=pst.getPPic()%>" class="card-img-top fixed-size-img" alt="...">

                <div class="card-body">
                    <h5 class="card-title"><%= pst.getPTitle()%></h5>
                    <p style="text-align: justify;" class="card-text"><%= pst.getPContent()%></p>
                    
                    <% 
                    String  redirectUrl =" login.jsp";
                       if (session.getAttribute("currentUser") != null) {
        // User is logged in, redirect to the single post page
        redirectUrl= "displaySinglePost.jsp?postId=" + pst.getPId();
    }
                    
                    %>
                 
                    <a href="login.jsp" class="btn cstm-clr text-light">Read More</a>
                </div>
            </div>   
        </div>
        <%
            } // End of for loop
%>
    </div>
</div>

