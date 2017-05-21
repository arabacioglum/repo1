<%@ include file="includes/header.jsp" %>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Please sign up</h3>
	  </div>
	  <div class="panel-body">
	    <form:form modelAttribute="signupForm" role="form" >
	    	<form:errors/>
	    	<div class="form-group" >
	    		<form:label path="email"><spring:message code="label.email" /></form:label>
	    		<form:input path="email" type="email" class="form-control" />
	    		<form:errors cssClass="error" path="email" />
	    	</div>
	    	<div class="form-group" >
	    		<form:label path="password"><spring:message code="label.password" /></form:label>
	    		<form:password path="password" class="form-control"/>
	    		<form:errors cssClass="error" path="password" />
	    	</div>
	    	<div class="form-group" >
	    		<form:label path="verifyPassword"><spring:message code="label.verifyPassword" /></form:label>
	    		<form:password path="verifyPassword" class="form-control"/>
	    		<form:errors cssClass="error" path="verifyPassword" />
	    	</div>
	    	<button type="submit" class="btn btn-default" >Submit</button>
	    </form:form>
	  </div>
	</div>
<%@ include file="includes/footer.jsp" %>
