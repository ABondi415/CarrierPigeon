<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
                            <td>
                                <nav>
                                    <ul id="menu">
                                        <li><a href="index.jsp">Home</a></li>
                                        <li><a href="ViewPackages.jsp">View Packages</a></li>
                                        <li><a href="Logout.jsp">Logout</a></li>
                                    </ul>
                                </nav>
                            </td>
                        </tr>
                        
                </table>
            </div>
            <div id="content">
                <div class="inner">
                    <div class="login">
                        <h2>Enter Package Information</h2>
                        <form action="NewPackageResult.jsp" method="POST">
                            <table>
                                <tr>
                                    <td>Carrier:</td>
                                    <td><select name="carrier" required>
                                            <option value="default"></option>
                                            <option value="FedEx">FedEx</option>
                                            <option value="UPS">UPS</option>
                                            <option value="USPS">USPS</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tracking Number:</td>
                                    <td><input type="text" name="TrackingNumber" required/></td>
                                </tr>
                                <tr>
                                    <td>Destination Zip Code:</td>
                                    <td><input type="text" name="DestZipCode"/>
                                </tr>
                                <tr>
                                    <td>Mailing Date:</td>
                                    <td><input type="date" name="MailingDate"/></td>
                                </tr>
                                <tr>
                                    <td><input type="hidden" name="Username"/></td>
                                </tr>
                            </table>
                            <div id="submit_button">
                                <br/>
                                <input type="submit" value="Track" />
                                <br/>
                            </div>
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
        <script src="js/login.js"></script>
    </body>
</html>