<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
</head>
<body>
    <h2>Sign Up</h2>
    <form:form action="" method="post" modelAttribute="form" autocomplete="off">
        <fieldset>
            <form:label path="email">email</form:label>
            <form:input path="email"/>
        </fieldset>
        <fieldset>
            <form:label path="password">password</form:label>
            <form:password path="password"/>
        </fieldset>
        <fieldset>
            <form:label path="name">name</form:label>
            <form:input path="name"/>
        </fieldset>
        <fieldset>
            <form:label path="phone">phone</form:label>
            <form:input path="phone"/>
        </fieldset>
        <button type="submit">Register</button>
    </form:form>
</body>
</html>