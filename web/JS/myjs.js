
//form page paswword fiel both signup page and login page 


function togglePassword() {
    const passwordField = document.getElementById('password');
    const passwordEye = document.getElementById('passwordEye');
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        passwordEye.classList.remove('fa-eye');
        passwordEye.classList.add('fa-eye-slash');
    } else {
        passwordField.type = 'password';
        passwordEye.classList.remove('fa-eye-slash');
        passwordEye.classList.add('fa-eye');
    }
}

//to remove msg from login page after 4 sec
setTimeout(function() {
        var alertMessage = document.getElementById('alertMessage');
        if (alertMessage) {
            alertMessage.style.display = 'none';
        }
    }, 4000);