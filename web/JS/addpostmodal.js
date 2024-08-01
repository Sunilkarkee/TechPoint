/* global cid */

$(document).ready(function () {
    $('#dopost-Modal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus');
    });
});

function toggleDropdown() {
    var dropdownList = document.getElementById("dropdown-list");
    if (dropdownList.style.display === "none" || dropdownList.style.display === "") {
        dropdownList.style.display = "block";
    } else {
        dropdownList.style.display = "none";
    }
}

function selectCategory(id, name, imgSrc) {
    var select = document.getElementById("postcat");
    var options = select.options;
    for (var i = 0; i < options.length; i++) {
        if (options[i].value === id) {
            options[i].selected = true;
            break;
        }
    }

    var displayText = document.getElementById("selected-category-text");
    displayText.innerHTML = '<img src="' + imgSrc + '" alt="' + name + '" style="width: 20px; height: 20px; margin-right: 10px;">' + name;

    var dropdownList = document.getElementById("dropdown-list");
    dropdownList.style.display = "none";
}

document.addEventListener('click', function(event) {
    var isClickInside = document.querySelector('.custom-dropdown').contains(event.target);
    if (!isClickInside) {
        document.getElementById("dropdown-list").style.display = "none";
    }
});
