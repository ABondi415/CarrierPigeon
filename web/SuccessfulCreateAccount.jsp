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
                                <h1><a href="Login.jsp">Carrier Pigeon</a></h1>
                            </td>
                        </tr>
                </table>
            </div>
            <div id="content">
                <div class="inner">
                    <div class="center">
                        <h2>Account Creation Successful!</h2>
                        <p>Your account has been created!  Please select Enter below to begin tracking!</p>
                        <form action="index.jsp" method="post">
                            <input type="hidden" value="<%= username %>"
                            <input type="submit" value="Enter" />
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