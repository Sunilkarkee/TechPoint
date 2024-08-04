<%@page import="com.programmershub.entities.Messages"%>
<%@page import="com.programmershub.entities.User"%>
<%
    // Check if user is logged in
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile</title>

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <!-- SweetAlert2 CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

        <!-- Custom CSS -->

        <link href="css/profile.css" rel="stylesheet" type="text/css"/>
        

    </head>
    <body>

        <!-- Nav bar -->

        <nav class="navbar navbar-expand-lg navbar-dark nav-background">
            <div class="container-fluid">
                <a class="navbar-brand" href="index.jsp">TechPoint</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active navbar-hover" aria-current="page" href="index.jsp">Home</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-white navbar-hover" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Categories
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Programming Languages</a></li>
                                <li><a class="dropdown-item" href="#">Data Structures</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="#">Project Implementation</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white navbar-hover" href="#">Contact Us</a>
                        </li>
                    </ul>

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0" id="dopostbtn" >
                        <li class="nav-item">
                            <a class="nav-link text-white navbar-hover" href="#!" data-bs-toggle="modal" data-bs-target="#dopost-Modal"><i class="fa fa-edit"></i> DoPost</a>
                        </li>
                    </ul>





                    <div id="userloginout">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link navbar-hover" href="#!" data-bs-toggle="modal" data-bs-target="#profile-modal">
                                    <i class="fa fa-user-circle"></i> <%= user.getName()%>
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link navbar-hover" href="Logout"><span class="fa fa-user-plus"></span> Logout</a>
                            </li>

                        </ul>
                    </div>
                </div>
            </div>
        </nav>

        <!--end of nav bar-->




        <!-- Display Messages -->
        <%
            Messages m = (Messages) session.getAttribute("msg");
            if (m != null) {
        %>
        <div id="alertMessage" class="alert <%= m.getCssClass()%>" role="alert">
            <%= m.getContent()%>
        </div>
        <%
                session.removeAttribute("msg");
            }
        %>



        <!--categories modal imported-->
        
         <%@include file="displayCategories.jsp" %>
        
        
        <!-- Profile Modal -->

        <div class="modal fade" id="profile-modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">

            <div class="modal-dialog">

                <div class="modal-content">

                    <div class="modal-header text-center primary-background text-white">
                        <h5 class="modal-title" id="staticBackdropLabel">TechPoint</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>


                    <div class="modal-body">
                        <div class="container text-center">
                            <img id="profileimg" src="profilepics/<%= user.getProfile()%>" alt="Profile Picture">
                            <br>
                            <h5 class="modal-title" id="staticBackdropLabel"><%= user.getName()%></h5>  


                            <div id="profile-details">


                                <table class="table table-hover">
                                    <tr>
                                        <th scope="row">Name</th> 
                                        <td><%= user.getName()%></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Email</th> 
                                        <td><%= user.getEmail()%></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Phone</th> 
                                        <td><%= user.getPhone_number()%></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Gender</th> 
                                        <td><%= user.getGender().toUpperCase()%></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Status</th> 
                                        <td><%= user.getAbout()%></td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Registered on</th> 
                                        <td><%= user.getCreated_at().toString()%></td>
                                    </tr>
                                </table>
                            </div>



                            <!-- Profile Editing Form -->


                            <div id="profile-edit" style="display:none">
                                <h3 class="mt-2">Edit your info here</h3>
                                <form id="editForm" action="EditServlet" method="post" enctype="multipart/form-data">
                                    <table class="table table-hover">
                                        <tr>
                                            <th scope="row">ID</th> 
                                            <td><%= user.getId()%></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Name</th> 
                                            <td><input class="form-control" type="text" name="user_name" value="<%= user.getName()%>"></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Email</th> 
                                            <td><input class="form-control" type="email" name="user_email" value="<%= user.getEmail()%>"></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Phone</th> 
                                            <td><input class="form-control" type="tel" name="user_phone" value="<%= user.getPhone_number()%>"></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Password</th> 
                                            <td><input class="form-control" type="password" name="user_pwd" value="<%= user.getPassword()%>"></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Gender</th> 
                                            <td><%= user.getGender().toUpperCase()%></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Status</th> 
                                            <td><textarea rows="5" class="form-control" name="user_about"><%= user.getAbout()%></textarea></td>
                                        </tr>
                                        <tr>
                                            <th scope="row">New Profile Picture</th> 
                                            <td><input class="form-control" type="file" name="image"></td>
                                        </tr>
                                    </table> 
                                    <div class="container">
                                        <button type="submit" class="btn btn-outline-primary">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button id="edit-btn" type="button" class="btn btn-primary">Edit</button>
                    </div>
                </div>
            </div>
        </div> 


        <!--add-post modal imported-->                                
                                        
        <%@include file="addpostmodal.jsp" %>

        <!-- JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>

        <script src="JS/profile.js"></script>
        <script src="JS/addpostmodal.js"></script>

    </body>
</html>
