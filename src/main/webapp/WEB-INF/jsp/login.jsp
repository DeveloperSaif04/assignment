<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login - URL Shortener</title>
</head>
<body>
    <h2>Login</h2>
    <form method="post" action="login">
        <input type="text" name="username" placeholder="Username" required /><br><br>
        <input type="password" name="password" placeholder="Password" required /><br><br>
        <button type="submit">Login</button>
    </form>
    <p><a href="register.jsp">Don't have an account? Register</a></p>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
</body>
</html>
