<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:if test="${not empty sessionScope.LoggedInUser}" >
                <c:if test="${requestScope.battle_amount > 0}">
                    <div class="online_users_container">
                        <c:forEach var="battleRequest" items="${requestScope.battles}">
                                <div class="online_row">
                                    <a href="profile.jsp?user=${battleRequest.player.userName}" class="online_user online_split">${battleRequest.player.userName}</a>
                                    <a href="battle.jsp?player=${battleRequest.player.userName}&opponent=${battleRequest.opponent.userName}" class="online_user online_split">Accept</a>
                                </div>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>
