<%@page import="com.techpoint.helper.PgmDbConnector"%>
<%@page import="com.techpoint.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.techpoint.daos.PostDao"%>

<!-- Modal -->
<div class="modal fade" id="dopost-Modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Write Post</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="addpostForm" action="AddPostServlet" method="post" enctype="multipart/form-data">
                    <div class="form-group mb-3">
                        <select id="postcat" name="catId" class="form-control" style="display: none;">
                            <option selected disabled value="">---select category---</option>
                            <%
                                PostDao postd = new PostDao(PgmDbConnector.makeConnection());
                                List<Category> list = postd.getAllCategories();
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
                            <div class="dropdown-item" onclick="selectCategory('<%= cat.getCid()%>', '<%= cat.getName()%>', 'catpics/<%= cat.getPhoto()%>')">
                                <img src="catpics/<%= cat.getPhoto()%>" alt="<%= cat.getName()%>">
                                <span><%= cat.getName()%></span>
                            </div>
                            <%
                                }
                            %>
                        </div>
                    </div>

                    <!-- User content inputs -->
                    <div class="form-group mb-3">
                        <input type="text" name="title" class="form-control" placeholder="Enter Post Title" required>
                    </div>

                    <div class="form-group mb-2">
                        <textarea name="content" class="form-control" placeholder="Write your content....." required></textarea>
                    </div>

                    <div class="form-group mb-2">
                        <textarea name="code" class="form-control" placeholder="Write your code (if any)"></textarea>
                    </div>

                    <div class="form-group">
                        <label>Upload your pic: </label>
                        <input type="file" name="pic" class="form-control">
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn nav-background text-white">Post</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- java script is imported in profile.jsp and this modal is also imported in profile.jsp as it is the part of profile.jsp-->

