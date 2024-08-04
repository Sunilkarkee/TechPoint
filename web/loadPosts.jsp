
<%@page import="java.util.List"%>
<%@page import="com.programmershub.entities.Post"%>
<%@page import="com.programmershub.daos.PostDao"%>
<%@page import="com.programmershub.helper.PgmDbConnector"%>

<div class="row no-gutters">

    <%
        Thread.sleep(300);
        PostDao pd = new PostDao(PgmDbConnector.makeConnection());
        int cId = Integer.parseInt(request.getParameter("cid"));
        List<Post> postList=null;
        if(cId==0){
         postList = pd.getAllPosts();
        }else{
        postList = pd.getPostsByCategoryId(cId);
        }
        if(postList.size()==0){
        out.println("<h5 class='display-5 text-center'>No Posts yet in this Category</h5>");
        return;
        }
        for (Post ps : postList) {
    %>
    <div class="b-col col-md-4">

        <div class="b-card card mb-4">
            <img src="blogsPics/<%=ps.getPPic() %>" class="card-img-top fixed-size-img" alt="...">
           
            <div class="card-body">
                
                <h5 class="card-title"><%= ps.getPTitle() %></h5>
                
                <p class="card-text"><%=ps.getPContent() %></p>
                
                <pre><%=ps.getPCode() %></pre>
               
                <a href="#" class="b-link btn btn-primary">More</a>
            </div>
        
        </div>
    </div>


<%
    }
%>


</div>

