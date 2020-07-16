<%--
  Created by IntelliJ IDEA.
  User: issuser
  Date: 2020/7/12/012
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<body>
    <form id="messageForm">
        <div style="width: 100%;">
            <label>name：</label>
            <div><input type="text" id="name" name="name"/></div>
        </div>
        <div style="width: 100%;">
            <label>messageId：</label>
            <div><input type="text" id="messageId" name="messageId"/></div>
        </div>
        <div style="width: 100%;">
            &nbsp;
        </div>
        <div style="width: 100%;">
            <input type="button" id="sendBut" value="发送"/>
        </div>
    </form>
</body>
</html>
<script type="text/javascript">
    //发送消息到mq
    $('#sendBut').click(function() {
        $.ajax({
            url:'${systemctx}/com/ddlij/rabbitmq/testSendMessage.json',
            type: 'POST',
            dataType: 'json',
            data:{"name" : $("#name").val(),"messageId" : $("#messageId").val()},
            success:function(data){
                alert("发送成功");
            }
        });
    });
</script>
