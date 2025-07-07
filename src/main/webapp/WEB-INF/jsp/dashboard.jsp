<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    String user = (String) session.getAttribute("username");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Dashboard - URL Shortener</title>
</head>
<body>
    <h2>Welcome, <%= user %>!</h2>

    <form method="post" action="shorten">
        <input type="text" name="url" placeholder="Enter long URL" required />
        <input type="text" name="custom" placeholder="Custom short code (optional)" />
        <button type="submit">Shorten</button>
    </form>

    <p><a href="logout">Logout</a></p>

    <c:if test="${not empty msg}">
        <p style="color: green;">${msg}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
</body>
</html>
