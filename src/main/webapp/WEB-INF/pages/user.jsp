<%@ include file="includes/header.jsp" %>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Profile</h3>
		</div>
		
		<div class="panel-body">
			<dl class="dl-horizontal">
				<dt>Name</dt>
				<dd><c:out value="${user.name}"></c:out></dd>
				<dt>Email</dt>
				<dd><c:out value="${user.email}"></c:out></dd>
				<dt>Roles</dt>
				<dd><c:out value="${user.roles}"></c:out></dd>
			</dl>
		</div>
		<c:if test="${user.editable}">
			<div class="panel-footer">
				<a class="btn btn-link" href="/users/${user.id}/edit" >Edit</a>
				<a class="btn btn-link" href="/users/${user.id}/change-password" >Change Password</a>
				<a class="btn btn-link" href="/users/${user.id}/change-email" >Change Email</a>
			</div>
		</c:if>
	</div>
<%@ include file="includes/footer.jsp" %>
