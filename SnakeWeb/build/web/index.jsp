<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="template/header.jsp" />
<c:choose>
    <c:when test="${page eq 'members'}">
        <jsp:include page="pages/members.jsp" />
        <jsp:include page="template/side_bar.jsp" />
        <jsp:include page="template/footer.jsp" />
    </c:when>
        <c:when test="${page eq 'profile'}">
        <jsp:include page="pages/profile.jsp" />
        <jsp:include page="template/side_bar.jsp" />
        <jsp:include page="template/footer.jsp" />
        <script type="text/javascript">
                Event.observe('RequestBattle_Button', 'click', function() {
                     RequestBattle();
                });
        </script>
    </c:when>
    <c:when test="${page eq 'battle'}">
        <jsp:include page="pages/battle.jsp" />
        <jsp:include page="template/bottom_bar.jsp" />
        <jsp:include page="template/footer.jsp" />
    </c:when>
    <c:when test="${page eq 'scores'}">
        <jsp:include page="pages/scores.jsp" />
        <jsp:include page="template/side_bar.jsp" />
        <jsp:include page="template/footer.jsp" />
    </c:when>
    <c:otherwise>
            <c:choose>
                <c:when test="${empty sessionScope.LoggedInUser}">
                    <jsp:include page="pages/register.jsp" />
                    <jsp:include page="template/side_bar.jsp" />
                    <jsp:include page="template/footer.jsp" />
                    <script type="text/javascript">
                        Event.observe('registerUserButton', 'click', function() {
                             RegisterForm();
                        });
                    </script>
                </c:when>
                <c:otherwise>
                    <jsp:include page="pages/overview.jsp" />
                    <jsp:include page="template/side_bar.jsp" />
                    <jsp:include page="template/footer.jsp" />
                    <script type="text/javascript">
                        Event.observe(window, 'load', function() {
                             ProcessAvatar();
                        });
                        Event.observe('updateUserButton', 'click', function() {
                             UpdateDetails();
                        });
                    </script>
                </c:otherwise>
            </c:choose>
    </c:otherwise>
</c:choose>
<c:if test="${not empty sessionScope.LoggedInUser}">
            <script type="text/javascript">
                Event.observe(window, 'load', function() {
                     GetBattleRequests();
                });
                Event.observe(window, 'load', function() {
                     GetSentBattleRequests();
                });
                Event.observe(window, 'load', function() {
                     BattleLoadCheck();
                });
            </script>
</c:if>

