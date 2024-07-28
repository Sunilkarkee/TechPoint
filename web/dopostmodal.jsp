


<%@page import="com.programmershub.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.programmershub.daos.PostDao"%>
<%@page import="com.programmershub.helper.PgmDbConnector"%>
<!-- Button trigger modal -->


<!-- Modal -->
<div class="modal fade" id="dopost-Modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Write Post</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <div class="form-group mb-3">
                    <select id="postcat" class="form-control" style="display: none;">
                        <option selected disabled value="">---select category---</option>
                        <%
                            PostDao postd = new PostDao(PgmDbConnector.makeConnection());
                            List<Category> list = postd.getCategories();
                            for (Category cat : list) {
                        %>
                        <option value="<%= cat.getCid()%>" data-name="<%= cat.getName()%>" data-img="catpics/<%= cat.getPhoto()%>"><%= cat.getName()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <div class="custom-dropdown mb-3">
                    <div class="dropdown-display" onclick="toggleDropdown()">
                        <span id="selected-category-text">---select category---</span>
                    </div>
                    <div id="dropdown-list" class="dropdown-list">
                        <%
                            for (Category cat : list) {
                        %>
                        <div onclick="selectCategory('<%= cat.getCid()%>', '<%= cat.getName()%>', 'catpics/<%= cat.getPhoto()%>')">
                            <img src="catpics/<%= cat.getPhoto()%>" alt="X">
                            <span><%= cat.getName()%></span>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>

                <!--user contents-->

                <div class="form-group mb-3">
                    <input type="text" class="form-control" placeholder="Enter Post Title">
                </div>

                <div class="form-group mb-2">
                    <textarea class="form-control" placeholder="Write your content....."></textarea>
                </div>

                <div class="form-group mb-2">
                    <textarea class="form-control" placeholder="Write your code(if any)"></textarea>
                </div>

                <div class="form-group">
                    <label>Upload your pic: </label>
                    <br>
                    <input type="file"  placeholder="Enter your Pic">
                </div>



            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Upload</button>
            </div>
        </div>
    </div>
</div>






<!-- JavaScript -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

<script>
                            $(document).ready(function () {
                                const myModal = document.getElementById('dopost-Modal');
                                const myInput = document.getElementById('myInput');

                                if (myModal && myInput) {
                                    myModal.addEventListener('shown.bs.modal', () => {
                                        myInput.focus();
                                    });
                                }
                            });
</script>

