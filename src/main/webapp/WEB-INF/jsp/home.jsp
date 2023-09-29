<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
</head>
<body>
    <ul>
        <li><a href="/">home</a></li>
        <li><a href="/login">login</a></li>
        <li><a href="/protected">protected</a></li>
        <sec:authorize access="isAuthenticated">
            <li>
                <form:form action="/logout" method="post" >
                    <button type="submit">logout</button>
                </form:form>
            </li>
        </sec:authorize>
    </ul>  
</body>
</html>