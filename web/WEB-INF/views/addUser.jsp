<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>patients</title>
</head>
<body>
    <h1>Add user</h1>
    <form action="${pageContext.request.contextPath}/admin/addUser" method="post">
        <label>login:
            <input type="text" name="username"/>
        </label><br>
        <label>password:
            <input type="text" name="password"/>
        </label><br>
        <input type="submit" />
    </form>
</body>
</html>
