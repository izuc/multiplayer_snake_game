<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <div class="member_box_container">
                    <div class="member_box_header"></div>
                    <div class="member_box_repeat">
                        <div class="member_box_picture">
                            <img class="member_image" src="avatars/${param.displayPicture}" />
                            <c:choose>
                                <c:when test="${param.status eq 1}">
                                    <div class="member_online"></div>
                                </c:when>
                                <c:otherwise>
                                    <div class="member_offline"></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="member_box_content">
                            <a href="profile.jsp?user=${param.userName}" class="member_box_username">${param.userName}</a>
                            <div class="messagebox">
                                 <div class="message_header"></div>
                                 <div class="message_repeat">
                                     <div class="message_text">
                                          ${param.message}
                                     </div>
                                 </div>
                                 <div class="message_footer"></div>
                            </div>
                        </div>
                    </div>
                    <div class="member_box_footer"></div>
                </div>