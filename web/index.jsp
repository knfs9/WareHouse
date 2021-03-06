<%@ page import="nc.edu.warehouse.UI.AreasDrawer" %>
<%@ page import="nc.edu.warehouse.WhOptimizer" %>
<%@ page language="java" contentType="text/html; UTF-8"
         pageEncoding="ISO-8859-1" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>Warehouse</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="myFunctions.js"></script>
</head>

<body>

<form method="post" action="index.jsp">
    <div class="sizes">
        <select name="choosesizes" style="width: 50px;">
            <option value="size2x2">2x2</option>
            <option value="size3x3">3x3</option>
            <option value="size4x4">4x4</option>
        </select>
    </div>
    <input type="submit" value="set" style="width: 50px; margin-top: 20px;"/>

</form>

<form id="hiddenForm" method="post" action="index.jsp">
    <input name="delete" id="deleteID" type="hidden" value="id" style="width: 50px;"/>
</form>

<%!WhOptimizer whOptimizer = new WhOptimizer(); %>

<div class="outputLabel" style="margin-left: 300px">
    <%=whOptimizer.getActionInfo()%>
</div>


<%
    String str[];

    if (request.getParameterValues("choosesizes") != null) {
        str = request.getParameterValues("choosesizes");
        switch (str[0]) {
            case "size2x2":
                whOptimizer.placeBox(2);
                break;
            case "size3x3":
                whOptimizer.placeBox(3);
                break;
            case "size4x4":
                whOptimizer.placeBox(4);
                break;
        }
        response.sendRedirect("index.jsp");
        //if div selected in param will be box id from database
    }

    if( request.getParameter("delete") !=  null && !request.getParameter("delete").trim().equals("id")){
        whOptimizer.deleteAndUpdate(Integer.valueOf(request.getParameter("delete")));
        response.sendRedirect("index.jsp");
    }
%>

<%=AreasDrawer.drawAreas()%>

</body>
</html>


