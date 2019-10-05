<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>patients</title>
</head>
<body>
    <h1>Patients</h1>
    <form action="/newPatient" method="post">
        Full name: <input type="text" name="fullname"/><br />
        <input type="submit" value ="submit"/>
    </form>
</body>
</html>
