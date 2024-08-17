
<%@page import="com.techpoint.entities.Reaction"%>
<%@page import="com.techpoint.daos.ReactionDao"%>

<%@page import="com.techpoint.entities.User"%>
<%@page import="java.util.List"%>
<%@page import="com.techpoint.entities.Post"%>
<%@page import="com.techpoint.daos.PostDao"%>
<%@page import="com.techpoint.helper.PgmDbConnector"%>

<%
    // Check if user is logged in
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

    <div class="row no-gutters">

    <%
        Thread.sleep(300);
        PostDao pd = new PostDao(PgmDbConnector.makeConnection());
        int cId = Integer.parseInt(request.getParameter("cid"));
        String myPost = request.getParameter("myPosts");

        int userId = user.getId();

        List<Post> postList = null;

        if ("true".equals(myPost)) {
            postList = pd.getPostByUserId(userId);
        } else {
            if (cId == 0) {
                postList = pd.getAllPosts();
            } else {
                postList = pd.getPostsByCategoryId(cId);
            }
        }
        if (postList.size() == 0) {
            out.println("<h5 class='display-5 text-center'>No Posts yet in this Category</h5>");
            
            return;
        }
        for (Post ps : postList) {
        

  
    %>
    
    
   
    <div class="b-col col-md-4">

        <div class="b-card card mb-4">
            <img src="blogsPics/<%=ps.getPPic()%>" class="card-img-top fixed-size-img" alt="...">

            <div class="card-body">

                <h5 class="card-title"><%= ps.getPTitle()%></h5>

                <p class="card-text"><%=ps.getPContent()%></p>


                <div class="card-footer text-center border-0">

                    <%

                        ReactionDao rd = new ReactionDao(PgmDbConnector.makeConnection());

                    %>

                    <!-- Like Button -->
                    <a href="#!" onclick="doReaction(<%= ps.getPId()%>, <%= user.getId()%>, 'LIKE')" class="b-link btn cstm-clr text-white btn-sm">
                        <i class="fa fa-thumbs-o-up"></i>
                        <span class="like-counter" id="like-counter-<%= ps.getPId()%>">
                            <%= rd.countReactionsByType(ps.getPId(), Reaction.ReactionType.LIKE)%>
                        </span>
                    </a>

                    <!-- Dislike Button -->
                    <a href="#!" onclick="doReaction(<%= ps.getPId()%>, <%= user.getId()%>, 'DISLIKE')" class="b-link btn cstm-clr text-light btn-sm">
                        <i class="fa fa-thumbs-o-down"></i>
                        <span class="dislike-counter" id="dislike-counter-<%= ps.getPId()%>">
                            <%= rd.countReactionsByType(ps.getPId(), Reaction.ReactionType.DISLIKE)%>
                        </span>
                    </a>


                    <a href="displaySinglePost.jsp?postId=<%= ps.getPId()%>" class="b-link btn cstm-clr text-white btn-sm">Read More</a>
                    
                    
                    <a href="#!" class="b-link btn cstm-clr text-white btn-sm"><i class="fa fa-commenting-o"></i><span>15</span></a>

                </div>
            </div>

        </div>
    </div>


    <%
        }
    %>


</div>

