<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%
	ArrayList<String> sintomas= (ArrayList<String>) request.getAttribute("sintomas");
%>

<!DOCTYPE html>

<html>
    <head>
        <title>Resultado da análise</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="assets/css/styles.css"/>
    </head>
    <body style="color: rgb(18,115,103);width: 1132px;">
      
		<h1 class="text-left shadow-lg" style="color: rgb(25,106,96);background: 
		rgba(88,188,245,0.45);border-bottom-left-radius:10px;border-right-width:0px:">
		Análise de cobertur - RT PCR (COVID)
		</h1>
		<h2 style="margin: 46px;height:40px;padding:0px;text-align:left;">
		Resultado:<%=request.getAttribute("result")%> 
		</h2>
		
		<div class="table-responsive table-bordered" style="width: 400px; 
		border-top-style:none;border-top-left-radius:10px;border-top-right-radius:10px;
		border-bottom-left-radius:10px;border-top-right-radius:10px;
		box-shadow: 4px 4px;margin:39px;">
			<table class="table table-bordered table-sm">
				<thead style="color: rgb(190,38,38);">
				<%
				if (!sintomas.isEmpty()){%>
					<tr style="with: 397px;">
						<th style="width:530px;"> Sintomas informados no RM:</th>
					</tr>
				</thead>
				<tbody>
					<% for(String sint: sintomas){%>
					<tr>
						<td><% out.println(sint); %></td>
					</tr>
					<%
					}
				}
				else {%>
					<tr>
						<td> Não foram localizados sintomsas</td>
					</tr>
						<%
				}%>
				</tbody>
			</table>
		</div>
		<script src="assets/js/jquery-3.5.1.min.js"></script>
		<script src="assets/bootstrap/js/bootstrap.min.js"></script>
		
    </body>
</html>


