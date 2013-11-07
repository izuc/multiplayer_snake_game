<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:if test="${not empty sessionScope.LoggedInUser}" >
                <c:if test="${requestScope.battle_amount > 0}">
                    <div class="online_users_container">
                        <c:forEach var="battleRequest" items="${requestScope.battles}">
                                <div class="online_row">
                                    <a href="profile.jsp?user=${battleRequest.opponent.userName}" class="online_user online_split">${battleRequest.opponent.userName}</a>
                                    <a href="cancel_request.jsp?user=${battleRequest.opponent.userName}" id="cancelButton" class="online_user online_split">Cancel</a>
                                </div>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>
 