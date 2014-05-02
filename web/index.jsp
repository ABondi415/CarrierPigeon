<% String username = request.getParameter("Username"); %>
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
                    <h3>Welcome to Carrier Pigeon!</h3>
                    <p>The <b>New Package</b> tab can be utilized to begin tracking a new package.</p>
                    <p>Use the <b>View Packages</b> tab to view all currently tracked packages and their associated tracking statuses.</p>
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