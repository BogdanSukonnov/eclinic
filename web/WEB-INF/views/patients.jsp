<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>patients</title>
</head>
<body>
    <h1>Patients</h1>
    <form action="/newPatient" method="post">
        <label>Full name:
            <input type="text" name="fullName"/>
        </label><br>
        <input type="submit" />
    </form>
</body>
</html>
