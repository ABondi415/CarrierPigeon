<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
     <title>Carrier Pigeon</title>
     <link rel="stylesheet" type="text/css" href="/CarrierPigeon/css/main.css" />
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div id="wrapper">
     <div id="side">
          <div class="inner">
              <div id='sidebar'>
                  <table>
                      <tr>
                          <td>Logout</td>
                      </tr>
                  </table>
              </div>
          </div>
     </div>
     <div id="content">
          <div class="inner">
              <div id='title'>
                  <table>
                      <tr>
                          <td><img src="/CarrierPigeon/img/logo.jpg" alt='logo'/></td>
                          <td><h1><a href="index.jsp">Carrier Pigeon</a></h1></td>
                          <td><h4><a href="#trackingModal">New Package</a></h4></td>
                      </tr>
                  </table>
              </div>
              <div id='main_content'>
                  <div id='inner_content'>
                      <p>Carrier pigeon is a composition of Web Services created to make the tracking of multiple packages easier.
                      To begin tracking a package, select the "New Package" heading at the top of the page.</p>
                  </div>
              </div>
              <div id="trackingModal" class="modalDialog">
                  <div>
                      <a href="#close" title="Close" class="close">X</a>
                      <h2>Enter Package Information</h2>
                      <form action="">
                          <table>
                              <tr>
                                  <td>Carrier:</td>
                                  <td><select name="carrier">
                                    <option value="default"></option>
                                    <option value="FedEx">FedEx</option>
                                    <option value="UPS">UPS</option>
                                    <option value="USPS">USPS</option>
                                    </select>
                                  </td>
                              </tr>
                              <tr>
                                  <td>Tracking Number:</td>
                                  <td><input type="text" name="Tracking Number"/></td>
                              </tr>
                          </table>
                          <div id="track_package_button">
                          <br/>
                              <input type="submit" value="Track" />
                          </div>
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
    
<script src="js/index.js"></script>
</body>
</html>