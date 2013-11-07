<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content_box">
    <div id="members_header"></div>
    <div id="content_repeat">
        <div class="pagination">
            <ul>
                  <li><a href="members.jsp?page=${currentpage}&action=prev" class="prevnext${prev_page}">« previous</a></li>
                      <c:forEach var="i" begin="${1}" end="${pagecount}">  
                           <li>
                                <c:choose>
                                    <c:when test="${currentpage eq i}">
                                        <a href="members.jsp?page=${i}" class="currentpage">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="members.jsp?page=${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                      </c:forEach>
                  <li><a href="members.jsp?page=${currentpage}&action=next" class="prevnext${next_page}">next »</a></li>
            </ul>
        </div>
          <c:forEach var="member" items="${members}">
                <jsp:include page="/template/member_box.jsp" flush="true">
                    <jsp:param name="displayPicture" value="${member.displayPicture}"/>
                    <jsp:param name="status" value="${member.status}"/>
                    <jsp:param name="userName" value="${member.userName}"/>
                    <jsp:param name="message" value="${member.message}"/>
                </jsp:include>
          </c:forEach> 
     </div>
     <div id="content_footer"></div>
</div>