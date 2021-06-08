<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>

    <title><%out.print(request.getParameter("action"));%> mea</title>
    <style>
        form {
            display: inline-block;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3><%out.print(request.getParameter("action"));%> meal</h3>

<hr>
<form method="POST" action='meals' name="frmAddMeal">

    <input type="hidden" readonly="readonly" name="mealid"
           value="${meal.id}"/> <br/>
    DateTime : <input
        type="datetime-local" name="dateTime"
        value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}"/> <br/>
    <br/>
    <input
            type="submit" value="Save"/>
</form>

<form class="form" action="meals">
    <button>Cancel</button>
</form>

</body>
</html>