
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage = "true" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sorry! Something went Wrong</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="css/mystyles.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>
    <body>
        <div class="container text-center " style="height:400px; width: 500px;">
            
                <img src="Images/imgerror.png" class="img-fluid" alt="error"/>

      
            <h4 class=" display-4">Sorry ! Something went wrong... </h4>
           
            <%= exception %>

            <a href="index.jsp" class="btn primary-background btn-lg text-white mt-3">Home</a>

        </div>
    </body>
</html>
