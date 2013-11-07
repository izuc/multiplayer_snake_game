    <div id="content_box">
        <div id="overview_header"></div>
         <div id="content_repeat">
                <div id="profile_container">
                        <div id="left_section">
                            <img id="profile_image" src="avatars/${LoggedInUser.displayPicture}" />
                            <div id="uploadImage" class="Blank_Button">Upload</div>
                        </div>
                        <form id="UserUpdateForm" action="index.jsp" method="post">
                            <div id="right_section">
                                <div id="info_message" style="width: 98%; display: none;"></div>
                                <div class="group">
                                      <span class="label">Email Address:</span>
                                      <span class="content"><input type="text" id="emailAddress" class="required validate-email" value="${LoggedInUser.emailAddress}"/></span>
                                </div>
                                <div class="group">
                                      <span class="label">Status Comment:</span>
                                      <span class="content">
                                            <textarea id="message" class="validate-message" rows="5">${LoggedInUser.message}</textarea>
                                      </span>
                                 </div>
                                 <input id="updateUserButton" type="button" class="buttonSubmit" style="color: #FFFFFF;" value="Update" />
                            </div>
                        </form>
                  </div>
         </div>
         <div id="content_footer"></div>
    </div>
