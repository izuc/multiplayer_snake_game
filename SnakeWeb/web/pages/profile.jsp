    <div id="content_box">
        <div id="overview_header"></div>
         <div id="content_repeat">
             <div id="profile_container">
                <div id="info_message" style="width: 330px; display: none;"></div>
                <jsp:include page="/template/member_box.jsp" flush="true">
                    <jsp:param name="displayPicture" value="${member.displayPicture}"/>
                    <jsp:param name="status" value="${member.status}"/>
                    <jsp:param name="userName" value="${member.userName}"/>
                    <jsp:param name="message" value="${member.message}"/>
                </jsp:include>
             </div>
             <div id="RequestBattle_Button"></div>
         </div>
         <div id="content_footer"></div>
    </div>
