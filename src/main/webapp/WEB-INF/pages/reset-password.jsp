<%@ include file="includes/header.jsp" %>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Please sign up</h3>
	  </div>
	  <div class="panel-body">
	    <form:form modelAttribute="resetPasswordForm" role="form" >
	    	<form:errors/>
	    	
	    	<div class="form-group" >
	    		<form:label path="password">Password</form:label>
	    		<form:password path="password" class="form-control" placeHolder="Enter Password" />
	    		<form:errors cssClass="error" path="password" />
	    		<p class="help=block">Enter Password</p>
	    	</div>
	    	
	    	<div class="form-group" >
	    		<form:label path="retypePassword">Retype Password</form:label>
	    		<form:password path="retypePassword" class="form-control" placeHolder="Retype Password" />
	    		<form:errors cssClass="error" path="retypePassword" />
	    		<p class="help=block">Retype Password</p>
	    	</div>
	    	
	    	<button type="submit" class="btn btn-default" >Submit</button>
	    </form:form>
	  </div>
	</div>
<%@ include file="includes/footer.jsp" %>