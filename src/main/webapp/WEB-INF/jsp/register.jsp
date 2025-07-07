<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register - URL Shortener</title>
</head>
<body>
    <h2>Register</h2>
    <form method="post" action="register">
        <input type="text" name="username" placeholder="Username" required /><br><br>
        <input type="password" name="password" placeholder="Password" required /><br><br>
        <button type="submit">Register</button>
    </form>
    <p><a href="login">Already have an account? Login</a></p>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
</body>
</html>
