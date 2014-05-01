<%@page import="Data.TrackingInformationController"%>
<%@page import="Data.UserController"%>
<%@page import="java.sql.Date"%>
<%@page import="services.Broker"%>
<%@page import="services.BrokerIF"%>
<%@page import="helpers.CarrierTypeHelper"%>
<%@page import="Data.TrackingInformation"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%
    UserController controller = new UserController();
    TrackingInformationController tiController = new TrackingInformationController();
    String carrier = request.getParameter("carrier");
    //String trackingNumber = request.getParameter("Tracking Number");
    String trackingNumber = "584007099279";
    String zipCode = request.getParameter("DestZipCode");
    Date mailingDate = Date.valueOf(request.getParameter("MailingDate"));
    String username = request.getParameter("Username");
    //int userId = controller.getUserIdByName(username);
    int userId=controller.getUserIdByName("test");
    TrackingInformation trackingInfo = new TrackingInformation(trackingNumber, CarrierTypeHelper.StringToCarrierType(carrier));
    trackingInfo.setDestZipCode(zipCode);
    trackingInfo.setMailingDate(mailingDate);
    trackingInfo.setUserId(userId);
    tiController.insertTrackingInfo(trackingInfo);
    BrokerIF broker = new Broker();
    broker.route(trackingInfo);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Carrier Pigeon</title>
        <link rel="stylesheet" type="text/css" href="/CarrierPigeon/css/main.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" src="js/vendor/jquery-1.11.0.js"></script>
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
                                <h1><a href="index.jsp">Carrier Pigeon</a></h1>
                            </td>
                        </tr>
                </table>
            </div>
            <div id="content">
                <div class="inner">
                    <div class="login">
                        <h2>New Package Added!</h2>
                        <ul>
                            <li>
                                <p>
                                    <b>Package Carrier: </b>
                                    <%= carrier%>
                                </p>
                            </li>
                            <li>
                                <p>
                                    <b>
                                        Tracking Number: <%= trackingNumber%>
                                    </b>
                                </p>
                            </li>
                        </ul>
                        <div id="submit_button">
                            <form action="index.jsp">
                                <input type="submit" value="Done"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div id="footer">
                <div class="inner">
                    <p>Carrier Pigeon - Spring 2014</p>
                </div>
            </div>
        </div>
        <script src="js/login.js"></script>
    </body>
</html>