<%@ include file="includes/header.jsp"%>

<div class="container-fluid text-center">
	<div class="row content">
		<form:form modelAttribute="orderForm" role="form">
			<form:errors />
			<div class="col-sm-12 text-left">
				<div style="text-align: center">
					<table style="width: 100%">
						<tr style="width: 80%;">
							<td style="width: 10%;"><img alt="LOGO"
								src="/images/logo.jpg" height="100px" /></td>
							<td style="text-align: center; width: 80%; color: steelblue">
								<h1>
									<spring:message code="label.title" />
								</h1>
							</td>
							<td style="width: 10%;"></td>
						</tr>
					</table>
				</div>
				<div class="table-responsive">
					<table id="orderItems" class="table" style="border-style: solid;">
						<tr>
							<th style="padding: 8px;"><spring:message code="header.product"/></th>
							<th style="padding: 8px;"><spring:message code="header.quantity"/></th>
							<th style="padding: 8px;"><spring:message code="header.price"/></th>
							<th style="padding: 8px;"><spring:message code="header.total"/></th>
						</tr>
						<c:forEach items="${orderForm.productOrders}" var="order" varStatus="stat">
							<tr>
								<td style="padding: 8px;"><form:input path="productOrders[${stat.index}].product.productName" value="${product.productName}" readonly="true" size="50"/></td>
								<td style="padding: 8px;"><form:input path="productOrders[${stat.index}].quantity" value="${quantity}" readonly="true"/></td>
								<td style="padding: 8px;"><form:input path="productOrders[${stat.index}].price" value="${price}" readonly="true"/></td>
								<td style="padding: 8px;"><form:input path="productOrders[${stat.index}].amount" value="${amount}" readonly="true"/></td>
							</tr>
					<!-- 		
							<tr>
								<td style="padding: 8px;"><c:out value="${order.product.productName}" /></td>
								<td style="padding: 8px;"><c:out value="${order.quantity}" /></td>
								<td style="padding: 8px;"><c:out value="${order.price}" /></td>
								<td class="amount" style="padding: 8px;"><c:out value="${order.amount} TL" /></td>
							</tr> -->
						</c:forEach>
						<p/>
						<tr style="font-style: oblique; font-weight: bold;">
							<td style="padding: 8px;" colspan="3"><spring:message code="header.total"/></td>
							<td style="padding: 8px;"><c:out value="${orderForm.totalAmount} TL" /></td>
						</tr>
					</table>
				</div>
				<table>
					<tr>
						<td style="width: 25%; padding-right: 8px;">
							<div class="form-group" >
					    		<form:label path="firstName"><spring:message code="label.firstName"/></form:label>
					    		<form:input path="firstName" type="text" class="form-control" maxLength="50"/>
					    		<form:errors cssClass="error" path="firstName" />
					    	</div>
					    </td>
					    <td style="width: 25%; padding-right: 8px;">
							<div class="form-group" >
					    		<form:label path="lastName"><spring:message code="label.lastName" /></form:label>
					    		<form:input path="lastName" type="text" class="form-control" maxLength="50"/>
					    		<form:errors cssClass="error" path="lastName" />
					    	</div>
				    	</td>
			    	</tr>
		    	</table>
		    	<p/>
		    	<h3><spring:message code="label.teslimatAdresi" /></h3>
				<div class="form-group" >
		    		<form:label path="province"><spring:message code="label.province" /></form:label>
		    		<form:select path="province">
		    			<form:options items="${provinceList}" itemLabel="provinceName"/>
		    		</form:select>
		    		<form:errors cssClass="error" path="province" />
		    	</div>
				<div class="form-group" >
		    		<form:label path="addressLine1"><spring:message code="label.addressLine1" /></form:label>
		    		<form:input path="addressLine1" type="text" class="form-control" maxLength="100" style="width: 60%"/>
		    		<form:errors cssClass="error" path="addressLine1" />
		    	</div>
				<div class="form-group" >
		    		<form:label path="addressLine2"><spring:message code="label.addressLine2" /></form:label>
		    		<form:input path="addressLine2" type="textarea"  class="form-control" maxLength="100" style="width: 60%"/>
		    		<form:errors cssClass="error" path="addressLine2" />
		    	</div>
		    	<p/>
		    	<h3><spring:message code="label.faturaAdresi" /></h3>
				<div class="form-group" >
		    		<form:label path="invoiceProvince"><spring:message code="label.province" /></form:label>
		    		<form:select path="invoiceProvince">
		    			<form:options items="${provinceList}" itemLabel="provinceName"/>
		    		</form:select>
		    		<form:errors cssClass="error" path="invoiceProvince" />
		    	</div>
				<div class="form-group" >
		    		<form:label path="invoiceAddressLine1"><spring:message code="label.addressLine1" /></form:label>
		    		<form:input path="invoiceAddressLine1" type="text" class="form-control" maxLength="100" style="width: 60%"/>
		    		<form:errors cssClass="error" path="invoiceAddressLine1" />
		    	</div>
				<div class="form-group" >
		    		<form:label path="invoiceAddressLine2"><spring:message code="label.addressLine2" /></form:label>
		    		<form:input path="invoiceAddressLine2" type="text" class="form-control" maxLength="100" style="width: 60%"/>
		    		<form:errors cssClass="error" path="invoiceAddressLine2" />
		    	</div>
				<div class="form-group" style="padding: 8px; font-size: large; font-weight: bold;">
		    		<form:radiobutton path="paymentMethod" value="TRANSFER"/><spring:message code="radio.transfer" />
		    		<form:radiobutton path="paymentMethod" value="CREDIT_CARD"/><spring:message code="radio.creditCard" />
		    		<form:radiobutton path="paymentMethod" value="ON_DELIVERY"/><spring:message code="radio.onDelivery" />
		    		<form:errors cssClass="error" path="paymentMethod" />
		    	</div>
		    	<div class="form-group" >
		    		<form:label path="email"><spring:message code="label.email" /></form:label>
		    		<form:input path="email" type="email" class="form-control"/>
		    		<form:errors cssClass="error" path="email" />
		    	</div>
				<div style="padding: 8px;">
					<input id="saveButton" type="submit" class="btn btn-default" value="<spring:message code="button.save" />"/>
				</div>
			</div>
		</form:form>
	</div>

</div>

<%@ include file="includes/footer.jsp"%>
