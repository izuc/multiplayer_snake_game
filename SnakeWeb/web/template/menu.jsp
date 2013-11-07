<div id="menu_buttons">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${empty sessionScope.LoggedInUser}">
        <form id="UserLoginForm" method="POST" action="LoginUser">
                <table id="login_box">
                        <tr valign="top">
                                <td width="50%"><label for="loginUserName">Username:</label></td>
                                <td width="50%"><input type="text" name="loginUserName" id="loginUserName" class="required validate-alphanum" maxlength="20" size="15" /></td>
                        </tr>
                        <tr valign="top">
                                <td width="50%"><label for="loginPassword">Password:</label></td>
                                <td width="50%"><input type="password" name="loginPassword" id="loginPassword" class="required validate-alphanum" maxlength="20" size="15" /></td>
                        </tr>
                        <tr valign="top">
                                <td colspan="2" style="text-align: center;"><input id="loginAction" type="button" value="Log In" style="color: #FFFFFF;" class="buttonSubmit" /></td>
                        </tr>
                </table>
        </form>
    </c:when>
    <c:otherwise>
        <a id="Logout_Button" href="LoginUser?action=logout" title="Logout"></a>
    </c:otherwise>
</c:choose>
</div>

