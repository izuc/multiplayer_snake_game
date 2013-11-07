<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <c:if test="${not empty users}" >
                <div class="online_users_container">
                    <c:forEach var="user" items="${users}">
                       <a href="profile.jsp?user=${user.userName}" class="online_row online_user">${user.userName}</a>
                    </c:forEach>
                </div>
            </c:if>