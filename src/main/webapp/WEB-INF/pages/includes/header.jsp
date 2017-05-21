<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html;charset=ISO-8859-9" pageEncoding="ISO-8859-9" %>
<!DOCTYPE html>
<html lang="tr">
<head>
  <title><spring:message code="label.title"/></title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href="/public/css/styles.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  	var qty;
	function updateCart(){
		var products = document.getElementsByClassName("prodTable");
    	var order;
    	var orderList = [];
    	var txt;
    	for (var i = 0; i < products.length; i++){
        	qty = 0;
        	txt = "";
			for (var r = 0, n = products[i].rows.length; r < n; r++) {
				var txt1 = allDescendants(products[i].rows[r]);
				if (txt1 !== undefined){
					txt += txt1;
				}
	        }
			if (txt.trim().length > 0){
				if (qty > 0){
					order = {\u00dcR\u00dcN:txt.trim(), ADET:qty};
					orderList.push(order);
				}
			}
		}
    	createTable(orderList);
    	var checkoutButton = document.getElementById("checkoutButton");
    	if (orderList.length > 0){
    		checkoutButton.disabled = false;
    	} else {
    		checkoutButton.disabled = true;
    	}
	}
	function allDescendants (node) {
		var txt = "";
	    for (var i = 0; i < node.childNodes.length; i++) {
	      var child = node.childNodes[i];
	      if (child.id === "prodName0" || child.id === "prodName1" || child.id === "prodName2"){
	    	txt = child.innerHTML;
      		return txt.trim();
      	  } else if (child.id === "quantity0" || child.id === "quantity1" || child.id === "quantity2"){
	    	  qty += Number(child.value);
      	  } else {
      		var txt1 = allDescendants(child);
      	  }
	    }
	}
	
	function createTable(list){
		var col = [];
		// EXTRACT VALUE FOR HTML HEADER. 
		for(var i=0; i<list.length; i++){
			for (var key in list[i]){
				if (col.indexOf(key) === -1){
					col.push(key);
				}
			}
		}
		// CREATE DYNAMIC TABLE.
        var table = document.createElement("table");
		
     	// CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.
     	var tr = table.insertRow(-1);                   // TABLE ROW.
     	
     	for (var i = 0; i < col.length; i++) {
            var th = document.createElement("th");      // TABLE HEADER.
            th.style.cssText = "color:red;";
            th.innerHTML = col[i];
            tr.appendChild(th);
        }
     	
     	// ADD JSON DATA TO THE TABLE AS ROWS.
     	for (var i=0; i<list.length; i++){
     		tr = table.insertRow(-1);
     		for (var j=0; j<col.length; j++){
     			var tabCell = tr.insertCell(-1);
     			tabCell.innerHTML = list[i][col[j]];
     			tabCell.style.cssText = "text-align: left;";
     		}
     	}
     	var divContainer = document.getElementById("cartContainer");
     	if (divContainer.lastChild != null){
     		divContainer.removeChild(divContainer.lastChild);
     	}
     	divContainer.innerHtml = "";
     	divContainer.appendChild(table);
	}
	
	function calcTotal(){
		orderAmounts = document.getElementsByClass("amount");
		var totalAmount = 0;
		for (var i=0; i<orderAmounts.length; i++){
			totalAmount += orderAmounts[i].value;
		}
		return totalAmount;
	}
  </script>
<!--   <script src="/javascript/updateCart.js"></script> -->
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
      background-color: skyblue;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: skyblue;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }
	
    .prodTable {
		margin: auto;
    	width: 80%;
    	border: 3px solid skyblue;
    	padding: 10px;
	}
	
	img {
	    display: block;
	    margin: 0 auto;
	}
  </style>
</head>
<body>

<nav class="navbar">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
		<ul class="nav navbar-nav navbar-right">
			<sec:authorize access="isAnonymous()">
				<li>
					<a href="<c:url value='/signup' />">
						<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> 
						Sign up
					</a>
				</li>
				<li>
					<a href="/login">Sign in <span class="glyphicon glyphicon-log-in"></span></a>
				</li>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                      <span class="glyphicon glyphicon-user"></span>
                      <sec:authentication property="principal.user.name" /> <b class="caret"></b>
                  </a>
                  <ul class="dropdown-menu">
                     <li><a href="/users/<sec:authentication property='principal.user.id' />"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                     <li>
	                    <c:url var="logoutUrl" value="/logout" />
		               	<form:form	id="logoutForm" action="${logoutUrl}" method="post">
					    </form:form>
					    <a href="#" onclick="document.getElementById('logoutForm').submit()"><span class="glyphicon glyphicon-log-out"></span> Sign out</a>
                     </li>
                  </ul>
              </li>
            </sec:authorize>
		</ul>
    </div>
  </div>
</nav>
<sec:authorize access="hasRole('ROLE_UNVERIFIED')">
			<div class="alert alert-warning alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				Your email id is unverified. <a href="/users/resend-verification-mail">Click here</a> to get verifiction mail again.
			</div>
		</sec:authorize>
		<c:if test="${not empty flashMessage}">
			<div class="alert alert-${flashKind} alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				${flashMessage}
			</div>
		</c:if>
