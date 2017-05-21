<%@ include file="includes/header.jsp"%>

<div class="container-fluid text-center">
	<div class="row content">

		<form:form method="post" action="${pageContext.request.contextPath}/home"
			modelAttribute="orderListForm" role="form" enctype="multipart/form-data"
			class="form-horizontal">
			<form:errors />
			<div class="col-sm-10 text-left">
				<div style="text-align: center">
					<table style="width: 100%">
						<tr style="width: 80%;">
							<td style="width: 10%;"><img alt="LOGO"
								src="/images/logo.jpg" height="100px" /></td>
							<td style="text-align: center; width: 80%; color: steelblue"><h1>
									<spring:message code="label.title" />
								</h1></td>
							<td style="width: 10%;"></td>
						</tr>
					</table>
				</div>
				<div class="table-responsive">
					<table class="table">
						<c:forEach items="${orderListForm.productTrios}" var="productTrio" varStatus="stat">
							<tr>
								<td style="width: 30%;">
									<table class="prodTable">
										<tr style="text-align: center; font-weight: bold;">
											<td id="prodName0" style="padding: 8px;">
												<c:out value="${productTrio.productOrder0.product.productName}" />
											</td>
										</tr>
										<tr>
											<td style="padding: 8px;"><img
												src="/images/Product${productTrio.productOrder0.product.productId}.jpg" />
											</td>
										</tr>
										<tr style="text-align: center;">
											<td style="padding: 8px;"><c:out
													value="${productTrio.productOrder0.product.productDescription}" />
											</td>
										</tr>
										<tr style="text-align: left; font-weight: bold;">
											<td style="padding: 8px;"><spring:message
													code="label.price" /> <fmt:formatNumber
													value="${productTrio.productOrder0.product.productPrice}"
													type="currency" currencySymbol="TRY" /></td>
										</tr>
										<tr style="text-align: left;">
											<td id="qtyForm0" style="padding: 4px;">
												<div class="form-group">
													<form:label path="productTrios[${stat.index}].productOrder0.quantity"
														class="col-sm-5 form-inline">
														<spring:message code="label.quantity" />
													</form:label>
													<div class="col-sm-5">
														<form:input id="quantity0"
															path="productTrios[${stat.index}].productOrder0.quantity"
															class="form-control" maxlength="10"
															onchange="updateCart()" />
														<form:errors cssClass="error"
															path="productTrios[${stat.index}].productOrder0.quantity" />
													</div>
													<div class="col-sm-2"></div>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td style="width: 30%;">
									<table class="prodTable">
										<tr style="text-align: center; font-weight: bold;">
											<td id="prodName1" style="padding: 8px;"><c:out
													value="${productTrio.productOrder1.product.productName}" /></td>
										</tr>
										<tr style="text-align: center;">
											<td style="padding: 8px;"><img
												src="/images/Product${productTrio.productOrder1.product.productId}.jpg" />
											</td>
										</tr>
										<tr style="text-align: center;">
											<td style="padding: 8px;"><c:out
													value="${productTrio.productOrder1.product.productDescription}" />
											</td>
										</tr>
										<tr style="text-align: left; font-weight: bold;">
											<td style="padding: 8px;"><spring:message
													code="label.price" /> <fmt:formatNumber
													value="${productTrio.productOrder1.product.productPrice}"
													type="currency" currencySymbol="TRY" /></td>
										</tr>
										<tr style="text-align: left;">
											<td id="qtyForm1" style="padding: 8px;">
												<div class="form-group">
													<form:label path="productTrios[${stat.index}].productOrder1.quantity"
														class="col-sm-5">
														<spring:message code="label.quantity" />
													</form:label>
													<div class="col-sm-5">
														<form:input id="quantity0"
															path="productTrios[${stat.index}].productOrder1.quantity"
															class="form-control" maxlength="10"
															onchange="updateCart()" />
														<form:errors cssClass="error"
															path="productTrios[${stat.index}].productOrder1.quantity" />
													</div>
													<div class="col-sm-2"></div>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td style="width: 30%;">
									<table class="prodTable">
										<tr style="text-align: center; font-weight: bold;">
											<td id="prodName2" style="padding: 8px;"><c:out
													value="${productTrio.productOrder2.product.productName}" /></td>
										</tr>
										<tr style="text-align: center;">
											<td style="padding: 8px;"><img
												src="/images/Product${productTrio.productOrder2.product.productId}.jpg" />
											</td>
										</tr>
										<tr style="text-align: center;">
											<td style="padding: 8px;"><c:out
													value="${productTrio.productOrder2.product.productDescription}" />
											</td>
										</tr>
										<tr style="text-align: left; font-weight: bold;">
											<td style="padding: 8px;"><spring:message
													code="label.price" /> <fmt:formatNumber
													value="${productTrio.productOrder2.product.productPrice}"
													type="currency" currencySymbol="TRY" /></td>
										</tr>
										<tr style="text-align: left;">
											<td id="qtyForm0" style="padding: 8px;">
												<div class="form-group">
													<form:label path="productTrios[${stat.index}].productOrder2.quantity"
														class="col-sm-5">
														<spring:message code="label.quantity" />
													</form:label>
													<div class="col-sm-5">
														<form:input id="quantity0"
															path="productTrios[${stat.index}].productOrder2.quantity"
															class="form-control" maxlength="10"
															onchange="updateCart()" />
														<form:errors cssClass="error"
															path="productTrios[${stat.index}].productOrder2.quantity" />
													</div>
													<div class="col-sm-2"></div>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div class="col-sm-2 sidenav" style="background-color: #FFF5EE;">
				<h1 style="color: red;">
					<spring:message code="header.shoppingCart" />
				</h1>
				<div id="cartContainer"></div>
				<div style="padding: 8px;">
					<input id="checkoutButton" type="submit" value="<spring:message code="button.checkout" />" class="btn btn-default" disabled="disabled"/>
				</div>
			</div>
		</form:form>
	</div>
</div>

<%@ include file="includes/footer.jsp"%>
