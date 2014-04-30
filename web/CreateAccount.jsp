<%@page import="Data.User"%>
<%@page import="java.util.List"%>
<%@page import="Data.UserController"%>
<%
    String action = request.getParameter("action");
    UserController controller = new UserController();
    List<User> users = controller.getUsers();
    boolean existingUser = false;
    
    if (action != null){
    
    if (action.equals("CreateAccount")) {
        // We really want to create a new account.
        if (request.getParameter("Email") != null) {
            for (User user : users){
                if(user.getUsername().equals(request.getParameter("Username"))){
                    existingUser = true;
                    break;
                }
            }
            if (!existingUser){
                User newUser = new User();
                newUser.setUsername(request.getParameter("Username"));
                newUser.setPassword(request.getParameter("Password"));
                newUser.setEmail(request.getParameter("Email"));
                controller.insertUser(newUser);
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", "http://localhost:8080/CarrierPigeon/SuccessfulCreateAccount.jsp");
            }
            else {
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", "http://localhost:8080/CarrierPigeon/InvalidCreateAccount.jsp");
            }
        }
    } else if (action.equals("Login")) {
        for (User user: users){
            if (user.getUsername().equals(request.getParameter("Username")) && user.getPassword().equals(request.getParameter("Password"))){
                existingUser = true;
                break;
            }
        }
        if (existingUser){
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "http://localhost:8080/CarrierPigeon/index.jsp");
        }
        else {
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "http://localhost:8080/CarrierPigeon/InvalidLogin.jsp");
        }
    } else {
        // Go back to login.
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", "http://localhost:8080/CarrierPigeon/Login.jsp");
    }
    }
%>

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
                        <h2>Create Account</h2>
                        <p>Please enter your account information below: </p>
                        <form action="CreateAccount.jsp" method="post">
                            <input type="hidden" name="action" id="action"/>
                            <table width="50%">
                                <tr>
                                    <td>Username:</td>
                                    <td><input type="text" name="Username" /></td>
                                </tr>
                                <tr>
                                    <td>Password:</td>
                                    <td><input type="password" name="Password" /></td>
                                </tr>
                                <tr>
                                    <td>Email Address:</td>
                                    <td><input type="email" name="Email"/></td>
                                </tr>
                                <tr>
                                    <td class="leftSubmit"><input type="submit" name="CreateAccount" value="Submit" /></td>
                                    <td><input type="submit" name="back" value="Back" /></td>
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