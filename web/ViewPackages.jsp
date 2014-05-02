<%@page import="helpers.CarrierTypeHelper"%>
<%@page import="Data.TrackingInformation"%>
<%@page import="Data.UserController"%>
<%@page import="java.util.List"%>
<%@page import="Data.TrackingInformationController"%>
<% 
    String username = request.getParameter("Username"); 
    UserController userController = new UserController();
    int userId = userController.getUserIdByName(username);
    TrackingInformationController infoController = new TrackingInformationController();
    List<TrackingInformation> trackingInformation = infoController.getTrackingInfoByLoginUser(userId);
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
                        <h2>Packages</h2>
                        <table id="table" class="transactions">
                            <thead>
                                <tr>
                                    <th>Tracking Number</th>
                                    <th>Carrier</th>
                                    <th>Destination Zip Code</th>
                                    <th>Mailing Date</th>
                                </tr>
                            </thead>
                            <tbody class="scroll">
                                <% for (int i = 0; i < trackingInformation.size(); i++){ 
                                    TrackingInformation currentInfo = trackingInformation.get(i);
                                %>
                                <tr>
                                    <td><%= currentInfo.getTrackingNumber() %></td>
                                    <td><%= CarrierTypeHelper.CarrierTypeToString(currentInfo.getCarrier()) %></td>
                                    <td><%= currentInfo.getDestZipCode() %></td>
                                    <td><%= currentInfo.getMailingDate().toString() %></td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                        <form action="ViewStatuses.jsp" method="post">
                            <input type="hidden" name="Username" value="<%= username %>"/>
                            <input type="hidden" name="TrackingNumber"/>
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
        <script src="js/PackageTable.js"></script>
    </body>
</html>