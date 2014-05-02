<%@page import="Data.TrackingStatus"%>
<%@page import="Data.TrackingStatusController"%>
<%@page import="helpers.CarrierTypeHelper"%>
<%@page import="Data.TrackingInformation"%>
<%@page import="Data.UserController"%>
<%@page import="java.util.List"%>
<%@page import="Data.TrackingInformationController"%>
<% 
    String username = request.getParameter("Username"); 
    String trackingNumber = request.getParameter("TrackingNumber");
    
    TrackingInformationController infoController = new TrackingInformationController();
    TrackingStatusController statusController = new TrackingStatusController();
    TrackingInformation associatedInfo = infoController.getTrackingInfoByTrackingNumber(trackingNumber);
    List<TrackingStatus> statuses = statusController.getTrackingStatusByTrackingInfoId(associatedInfo.getId());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Carrier Pigeon</title>
        <link rel="stylesheet" type="text/css" href="/CarrierPigeon/css/main.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" src="js/vendor/jquery/jquery.js"></script>
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <table width="100%">
                    <col width="140">
                        <tr>
                            <td>
                                <img src="/CarrierPigeon/img/logo.jpg" alt='logo'/>

                            </td>
                            <td>
                                <h1><a href=<%= "index.jsp?Username=" + username %>>Carrier Pigeon</a></h1>
                            </td>
                            <td>
                                <nav>
                                    <ul id="menu">
                                        <li><a href=<%= "index.jsp?Username=" + username %>>Home</a></li>
                                        <li><a href=<%= "NewPackage.jsp?Username=" + username %>>New Package</a></li>
                                        <li><a href=<%= "ViewPackages.jsp?Username=" + username %>>View Packages</a></li>
                                        <li><a href="Logout.jsp">Logout</a></li>
                                    </ul>
                                </nav>
                            </td>
                        </tr>
                </table>
            </div>
            <div id="content">
                <div class="inner">
                    <div class="center">
                        <h2>Package Status</h2>
                        <table id="table">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>City</th>
                                    <th>State</th>
                                </tr>
                            </thead>
                            <tbody class="scroll">
                                <% for (int i = 0; i < statuses.size(); i++){ 
                                    TrackingStatus currentStatus = statuses.get(i);
                                %>
                                <tr>
                                    <td><%= currentStatus.getStatusDate().toString() %></td>
                                    <td><%= currentStatus.getStatusCity() %></td>
                                    <td><%= currentStatus.getStatusState() %></td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                        <form action="ViewPackages.jsp" method="get" style="padding-left: 15%;">
                            <input type="hidden" name="Username" value="<%= username %>"/>
                            <input type="submit" value="Return to Packages"/>
                        </form>
                    </div>
                </div>
            </div>
            <div id="footer">
                <div class="inner">
                    <p>Carrier Pigeon - Spring 2014</p>
                </div>
            </div>
        </div>
        <script src="js/main.js"></script>
    </body>
</html>