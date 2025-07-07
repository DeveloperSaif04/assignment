<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>URL Shortener</title>
</head>
<body>
    <h2>Welcome to URL Shortener</h2>

    <form action="shorten" method="post">
        <input type="text" name="url" placeholder="Enter long URL" required />
        <button type="submit">Shorten</button>
    </form>

    <p><a href="login.jsp">Login</a> | <a href="register.jsp">Register</a></p>

    <c:if test="${not empty msg}">
        <p style="color: green;">${msg}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
</body>
</html>
