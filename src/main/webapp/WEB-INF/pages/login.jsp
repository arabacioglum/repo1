<%@ include file="includes/header.jsp" %>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Please sign in</h3>
		</div>
	</div>
	
	<div class="panel-body">
	
		<c:if test="${param.error != null}">
			<div class="alert alert-danger">
				Invalid user name and password
			</div>
		</c:if>
		
		<c:if test="${param.logout != null}">
			<div class="alert alert-danger">
				You have been logged out
			</div>
		</c:if>
		
		<form:form role="form" method="post">
			<div class="form-group">
				<label for="username">Email address</label>
				<input id="username" name="username" type="email" class="form-control">
				<p class="help-block">Enter your email address.</p>
			</div>

			<div class="form-group">
				<label for="password">Email address</label>
				<input id="password"  name="password" type="password" class="form-control">
				<form:errors cssClass="error" path="password" />
			</div>
			
			<div class="form-group">
				<div class="checkbox">
					<label>
						<input type="checkbox"> Remember me
					</label>
				</div>
			</div>
			
			<button type="submit" class="btn btn-primary">Sign in</button>
			<a class="btn btn-default" href="/forgot-password">Forgot Password</a>
		</form:form>
	</div>
	
<%@ include file="includes/footer.jsp" %>
