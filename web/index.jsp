
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Technology Blog</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="css/mystyles.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        
        <style>

            .banner-background{
                clip-path: polygon(30% 0%, 70% 0%, 100% 0, 100% 91%, 78% 98%, 51% 90%, 0 100%, 0 0);
            }

        </style>
    </head>
    <body>
        
        <!--Navbar-->
        <%@include file="navbar.jsp" %> 

       
        <!--banner-->
        <div class="banner-background">
            <div class="container-fluid bg-primary text-light py-5">
                <div class="custom-left-margin">
                    <h5 class="display-4 fade-in"><strong>Welcome To TechPoint</strong></h5>
                    <p style="text-align: justify;">A programming language is a system of notation for writing computer programs.
                        Programming languages are described in terms of their syntax (form) and semantics (meaning), usually defined by a formal language. Languages usually provide features such as a type system, variables and mechanisms for error handling. An implementation of a programming language is required in order to execute programs, namely a compiler or an interpreter. An interpreter directly executes the source code, while a compiler produces an executable program.
                    </p>
                    <p style="text-align: justify;">There exist lots of programming languages at today's date, few of them are: <strong>Java, Python, C ,C++, Java Script, HTML, CSS.</strong></p>
                   
                    <div class="btns">
                        <a href="registerpage.jsp"><button type="button" class="btn btn-primary btn-outline-light btn-md"><i class="fa fa-user-plus"></i> Start for free</button></a>
                        <a href="login.jsp"><button type="button" class="btn btn-primary btn-outline-light btn-md"><i class="fa fa-user-circle-o fa-spin"></i> LogIn</button></a>
                    </div>
                </div> 
            </div>
        </div>
        <!--cards-->
        <div class="card-container">
            <%@include file="cards.jsp" %> 
            <%@include file="cards.jsp" %> 
        </div>




        <!--Java-Script-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>   

    </body>
</html>
