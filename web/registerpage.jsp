<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Sign Up Page</title>

        <!-- Style sheets -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="css/mystyles.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

        <style>
            .banner-background {
                clip-path: polygon(30% 0%, 70% 0%, 100% 0, 100% 91%, 78% 98%, 51% 90%, 0 100%, 0 0);
            }
            .select-custom {
                width: 150px; /* Set the desired width */
            }
        </style>
    </head>
    <body>
        <%@include file="navbar.jsp" %> 

        <main class="primary-background banner-background" style="padding-bottom: 140px; padding-top: 30px;">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 offset-md-3">
                        <div class="card">
                            <div class="card-header text-center primary-background text-white">
                                <h5>Provide your Details...</h5>
                            </div>

                            <div class="card-body">

                                <div   class="container text-center">
                                    <img id="profile-pic-preview" src="Images/default.png" alt="Profile Picture Preview">

                                </div>

                                <form action="RegisterServlet" method="post" id="reg-form" enctype="multipart/form-data">


                                    <div class="mb-3">
                                        <label for="user_name" class="form-label">User Name</label>
                                        <input id="username" name="user_name" type="text" class="form-control" placeholder="Enter Your Name">
                                    </div>

                                    <div class="mb-3">
                                        <label for="exampleInputEmail1" class="form-label">Email address</label>
                                        <input id="email" name="user_email" type="text" class="form-control" placeholder="Enter Your Email">
                                        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="exampleInputPhone" class="form-label">Phone Number</label>
                                        <input id="phone" name="phone_number" type="tel" class="form-control" placeholder="Enter Your Phone Number">
                                    </div>

                                    <div class="mb-3">
                                        <label for="exampleInputPassword" class="form-label">Password</label>
                                        <div class="input-group">
                                            <input id="password" name="user_password" type="password" class="form-control" placeholder="Enter Your Password">
                                            <button type="button" class="btn btn-outline-secondary" onclick="togglePassword()">
                                                <i id="passwordEye" class="fa fa-eye"></i>
                                            </button>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="gender">Gender:</label>
                                        <select id="gender" name="gender" class="select-custom">
                                            <option value="none" selected>Select your gender</option>
                                            <option value="male">Male</option>
                                            <option value="female">Female</option>
                                            <option value="other">Other</option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <div class="form-group">
                                            <textarea name="about" id="about" class="form-control" rows="5" placeholder="Enter something about yourself"></textarea>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="profile-pics" class="form-label">Profile Pic:</label>

                                        <input type="file" name="profile_pic" id="profile_pic" class="form-control" accept="image/*">

                                    </div>

                                    <div class="mb-3 form-check">
                                        <input id="terms" name="check_box" type="checkbox" class="form-check-input" id="Check1">
                                        <label class="form-check-label" for="exampleCheck1">Agree terms and conditions</label>
                                    </div>

                                    <div class="container text-center m-2 d-none" id="loader">
                                        <i class="fa fa-refresh fa-spin fa-2x ms-2" id="custom-icon"></i>
                                        <h5>Please wait<strong>...</strong></h5>
                                    </div>

                                    <div class="container text-center">
                                        <button id="custom-button" type="submit" class="btn btn-primary">Submit</button>
                                    </div>


                                </form>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- JavaScript dependencies -->
        <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="JS/registerHandler.js"></script>
        <script src="JS/myjs.js"></script>
        <!-- External JavaScript file -->
    </body>
</html>
