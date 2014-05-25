<%-- 
    Document   : chat
    Created on : 20/05/2011, 18:54:40
    Author     : DANILO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd"
    xmlns:p="http://primefaces.prime.com.tr/ui"
 xmlns:f="http://java.sun.com/jsf/core"
 xmlns:h="http://java.sun.com/jsf/html"
 xmlns:oi="http://java.sun.com/jsf/facelets"

   >
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat Alfa Informática</title>
         
        <link type="text/css" rel="stylesheet" href="css/flick/jquery-ui-1.8.9.custom.css" />
        <link type="text/css" rel="stylesheet" href="/AlfaInfor/css/layout.css" />
    </head>
    <body>
        <div id="cabecalho" >
           
        </div>
        <div id="menuCabecalho">
            <oi:include src="/menu.xhtml"/>
        </div>
        <div id="menuEsquero" >
        </div>
        <div id="conteudo">
            <center><h:outputText value="E-mail" style="font-size: 26pt" /></center>
            <center>  <iframe src="http://settings.messenger.live.com/Conversation/IMMe.aspx?invitee=b0bb3e6961180c2d@apps.messenger.live.com&mkt=pt-BR" width="300" height="300" style="border: solid 1px black; width: 300px; height: 300px;" frameborder="0" scrolling="no"></iframe> </center>
            <br/>

        </div>
        <div id="conteudo">
         <center><a href="http://localhost:8080/PaginaIncial/"> <input type="submit" value="Voltar" name="voltar" /> </a></center>
        </div>
        </body>

</html>
