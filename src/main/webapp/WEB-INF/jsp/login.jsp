<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
</head>
<body>
    <h2>Sign In</h2>
    <form:form action="" method="post" modelAttribute="form" autocomplete="off">
        <fieldset>
            <form:label path="email">email</form:label>
            <form:input path="email"/>
        </fieldset>
        <fieldset>
            <form:label path="password">password</form:label>
            <form:password path="password"/>
        </fieldset>
        <button type="submit">Login</button>
    </form:form>
</body>
</html>