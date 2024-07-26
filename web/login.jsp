<%@page import="com.programmershub.entities.Messages"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- Custom CSS -->
        <link href="css/mystyles.css" rel="stylesheet" type="text/css"/>


        <style>
            .banner-background {
                clip-path: polygon(30% 0%, 70% 0%, 100% 0, 100% 91%, 78% 98%, 51% 90%, 0 100%, 0 0);
            }

        </style>
    </head>
    <body>
        <%@include file="navbar.jsp" %>

        <main class="d-flex align-items-center primary-background banner-background" style="height:100vh; ">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-4 col-md-6 col-sm-8">
                        <div class="card">
                            <div class="card-header primary-background text-white text-center">
                                <i class="fa fa-user-circle fa-3x"></i>
                                <br>
                                <p>LogIn here</p>
                            </div>

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
                            <div class="card-body">
                                <form action="LoginServlet" method="post">
                                    <div class="mb-3">
                                        <label for="identifier" class="form-label">ID</label>
                                        <input name="Identifier" type="text" class="form-control" id="identifier" placeholder="Email or Phone" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="exampleInputPassword" class="form-label">Password</label>
                                        <div class="input-group">
                                            <input name="Password" type="password" class="form-control" id="password" required>
                                            <button type="button" class="btn btn-outline-secondary" onclick="togglePassword()">
                                                <i id="passwordEye" class="fa fa-eye"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="mb-3 form-check">
                                        <input type="checkbox" class="form-check-input" id="exampleCheck1">
                                        <label class="form-check-label" for="exampleCheck1">Check me out</label>
                                    </div>
                                    <div class="container text-center">
                                        <button type="submit" class="btn btn-primary">LogIn</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

        <script src="JS/myjs.js" ></script>


    </body>
</html>
