<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dashboard - URL Shortener</title>
</head>
<body>
    <h2>Welcome to URL Shortener</h2>

    <form method="post" action="shorten">
        <input type="text" name="url" placeholder="Enter full URL" required /><br><br>

        <!-- Only show custom code input if user is logged in -->
        <c:if test="${not empty sessionScope.userId}">
            <input type="text" name="custom" placeholder="Custom short code (optional)" /><br><br>
        </c:if>

        <input type="text" name="domain" placeholder="Your domain (default: http://localhost:9090)" /><br><br>

        <button type="submit">Shorten</button>
    </form>

    <c:if test="${not empty msg}">
        <p style="color:green;">${msg}</p>
    </c:if>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <c:if test="${not empty shortUrl}">
        <p>Shortened URL:
            <a href="${shortUrl}" target="_blank">${shortUrl}</a>
        </p>
    </c:if>
</body>
</html>
