<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="online_box">
        <div id="online_header"></div>
        <div class="online_repeat">
            <div id="online_users">

            </div>
        </div>
        <div class="online_footer"></div>
    </div>

    <c:if test="${not empty sessionScope.LoggedInUser}">
            <div class="online_box">
                <div id="battlerequests_header"></div>
                <div class="online_repeat">
                    <div id="battlerequests">

                    </div>
                </div>
                <div class="online_footer"></div>
            </div>
            <div class="online_box">
                <div id="sentrequests_header"></div>
                <div class="online_repeat">
                    <div id="sentrequests">

                    </div>
                </div>
                <div class="online_footer"></div>
            </div>
            <div id="battle_load"></div>
    </c:if>