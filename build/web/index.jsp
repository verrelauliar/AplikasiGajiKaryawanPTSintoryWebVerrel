<%-- 
    Document   : index.jsp
    Created on : Nov 19, 2025, 1:16:20 PM
    Author     : Verrel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>Informasi Gaji Karyawan</title>
    </head>
    <body bgcolor="#808080">
        <%
            String menu = "<br><b>Master Data</b><br>"
                    + "<a href=.>Karyawan</a><br>"
                    + "<a href=.>Pekerjaan</a><br>"
                    + "<b>Transaksi</b><br>"
                    + "<a href=.>Gaji</a><br>"
                    + "<b>Laporan</b><br>"
                    + "<a href=.>Gaji</a><br>"
                    + "<a href=LoginController>Login</a><br>";

            String topMenu = "<nav><ul>"
                    + "<li><a href=.>Home</a></li>"
                    + "<li><a href=javascript:void(0);>Master Data</a>" // Ubah # jadi javascript:void(0);
                    + "<ul>"
                    + "<li><a href=.>Karyawan</a></li>"
                    + "<li><a href=.>Pekerjaan</a>"
                    + "</ul>"
                    + "</li>"
                    + "<li><a href=javascript:void(0);>Transaksi</a>" // Ubah # jadi javascript:void(0);
                    + "<ul>"
                    + "<li><a href=.>Gaji</a>"
                    + "</ul>"
                    + "</li>"
                    + "<li><a href=javascript:void(0);>Laporan</a>" // Ubah # jadi javascript:void(0);
                    + "<ul>"
                    + "</ul>"
                    + "</li>"
                    + "<li><a href=LoginController>Login</a></li>"
                    + "</ul>"
                    + "</nav>";

            // Cek apakah Controller mengirimkan "konten"
            String konten = (String) request.getAttribute("konten");
            if (konten == null) {
                konten = "<br><h1>Selamat Datang</h1>";
            }

            String userName = ""; 

            if (!session.isNew()) {
                try {
                    userName = session.getAttribute("userName").toString();
                } catch (Exception ex) {
                }

                if (!((userName == null) || userName.equals(""))) {
                    // Menaruh userName DI BAWAH konten (setelah Selamat Datang)
                    konten = konten + "<h2>" + userName + "</h2>";
                }

                try {
                    menu = session.getAttribute("menu").toString();
                } catch (Exception ex) {
                }

                try {
                    topMenu = session.getAttribute("topMenu").toString();
                } catch (Exception ex) {
                }
            }
        %>
        
        <center>
        <table width="80%" bgcolor="#eeeeee">
            <tr>
                <td colspan="2" align="center">
                    <br>
                    <h2 style="margin-bottom: 0px; margin-top: 0px;">
                        Informasi Gaji Karyawan
                    </h2>
                    <h1 style="margin-bottom: 0px; margin-top: 0px;">
                        PT Sintory Verrel
                    </h1>
                    <h4 style="margin-bottom: 0px; margin-top: 0px;">
                        Jl. Surya Kencana No.99 Pamulang, Tangerang Selatan, Banten
                    </h4>
                    <br>
                </td>
            </tr>

            <tr height="400">
                <td width="200" align="center" valign="top" bgcolor="#eeeeee">
                    <br>
                    
                    <div id="menu">
                        <%=menu%>    
                    </div>
                </td>
                <td align="center" valign="top" bgcolor="#ffffff">
                    <%=topMenu%>
                    <br>
                    <%=konten%>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center" bgcolot="#eeeeff">
                    <small>
                        Copyright &copy; 2017 PT Sintory by Verrel Aulia Rahman~<br>
                        Jl. Surya Kencana No.99 Pamulang, Tangerang Selatan, Banten<br>
                    </small> 
                </td>
            </tr>
        </table>
    </center>
    </body>
</html>