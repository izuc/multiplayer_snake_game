<div id="content_box">
    <div id="register_header"></div>
    <div id="content_repeat">
        <form id="registerForm" action="" enctype="multipart/form-data">
            <table class="form_table">
                <tr valign="top">
                    <td colspan="2"><div id="info_message" style="display: none;"></div></td>
                </tr>
                <tr valign="top"> 
                    <td width="50%">
                        <label for="userName">Username:</label>
                    </td>
                    <td width="50%">
                        <input name="userName" id="userName" class="required validate-alphanum" type="text" size="25" />
                    </td>
                </tr>
                <tr valign="top">
                    <td width="50%">
                        <label for="password">Password:</label>
                    </td>
                    <td width="50%">
                        <input name="password" id="password" class="required validate-alphanum" type="password" size="25" />
                    </td>
                </tr>
                <tr valign="top"> 
                    <td width="50%">
                        <label for="emailAddress">Email:</label>
                    </td>
                    <td width="50%">
                        <input name="emailAddress" id="emailAddress" type="text" class="required validate-email" size="25" />
                    </td>
                </tr>
                <tr valign="top">
                    <td width="50%">
                        <label for="answer">Verification:</label>
                    </td>
                    <td width="50%">
                        <img src="StickyImg" /> <br />
                        <input id="verification" type="text" class="required validate-alphanum" size="25" />
                    </td>
                </tr>
                <tr valign="top">
                    <td colspan="2" style="text-align: center;"><input id="registerUserButton" class="buttonSubmit" style="color: #FFFFFF;" type="button" value="Register" /></td>
                </tr>
             </table>
         </form>
     </div>
     <div id="content_footer"></div>
</div>