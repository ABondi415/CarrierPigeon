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
                                <h1><a href="Login.jsp">Carrier Pigeon</a></h1>
                            </td>
                        </tr>
                </table>
            </div>
            <div id="content">
                <div class="inner">
                    <div class="login">
                        <h2>Welcome!</h2>
                        <p>Carrier pigeon is a composition of Web Services created to make the tracking of multiple packages easier.</p>
                        <p>If you have an account, you may login below.  Otherwise, select <b>Create an Account</b> and begin tracking in just a few minutes!</p>
                        <form action="CreateAccount.jsp" method="post">
                            <input type="hidden" name="action" id="action" value=""/>
                            <table width="50%">
                                <tr>
                                    <td>Username:</td>
                                    <td><input type="text" name="Username"/></td>
                                </tr>
                                <tr>
                                    <td>Password:</td>
                                    <td><input type="password" name="Password"/></td>
                                </tr>
                                <tr>
                                    <td class="leftSubmit"><input type="submit" name="Login" value="Login" /></td>
                                    <td><input type="submit" name="CreateAccount" value="Create Account" /></td>
                                </tr>
                            </table>
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