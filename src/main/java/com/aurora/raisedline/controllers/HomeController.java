package com.aurora.raisedline.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aurora.raisedline.dto.OrderForm;
import com.aurora.raisedline.dto.OrderListForm;
import com.aurora.raisedline.dto.ProductOrder;
import com.aurora.raisedline.dto.ProductTrio;
import com.aurora.raisedline.entities.Product;
import com.aurora.raisedline.entities.Province;
import com.aurora.raisedline.exceptions.OrderInsertException;
import com.aurora.raisedline.repositories.OrderRepository;
import com.aurora.raisedline.repositories.ProductRepository;
import com.aurora.raisedline.repositories.ProvinceRepository;
import com.aurora.raisedline.services.OrderService;
import com.aurora.raisedline.util.Constants.PaymentMethod;
import com.aurora.raisedline.util.RaisedLineUtil;


@Controller
@SessionAttributes(value = {"orderListForm", "provinceList", "orderForm"})
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private ProductRepository productInventory;
	private ProvinceRepository provinceInventory;
	private List<Province> provinceList = new ArrayList<>();
	
	private OrderService orderService;
	
	@Autowired
	public HomeController(ProductRepository productInventory, ProvinceRepository provinceInventory,
			OrderRepository cityInventory){
		this.productInventory = productInventory;
		this.provinceInventory = provinceInventory;
	}
	
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String getProductTrios(Model model){
		List<Product> productList = productInventory.findAll();
		Iterator<Product> iterator = productList.iterator();
		List<ProductTrio> productTrioList = new ArrayList<>();
		int sayac = 0;
		ProductTrio productTrio = null;	
		OrderListForm orderListForm = new OrderListForm();	
		ProductOrder productOrder = null;
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();
			switch (sayac % 3) {
			case 0:
				productTrio = new ProductTrio();
				productOrder = prepareOrderItem(product);
				productTrio.setProductOrder0(productOrder);
				break;
			case 1:
				productOrder = prepareOrderItem(product);
				productTrio.setProductOrder1(productOrder);
				break;
			case 2:
				productOrder = prepareOrderItem(product);
				productTrio.setProductOrder2(productOrder);
				productTrioList.add(productTrio);
				break;
			}
			sayac++;
		}
		if (sayac % 3 > 0){
			productTrioList.add(productTrio);
		}
		orderListForm.setProductTrios(productTrioList);
		model.addAttribute("orderListForm", orderListForm);
		return "home";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String createShoppingCart(@ModelAttribute("orderListForm") @Valid OrderListForm orderListForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			HttpServletRequest request){
		return "redirect:/order-entry";
	}
	
	@RequestMapping(value = "/order-entry")
	public String getOrderForm(Model model, @ModelAttribute("orderListForm") OrderListForm orderListForm){
		List<ProductOrder> productOrders = new ArrayList<>();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for(ProductTrio pTrio : orderListForm.getProductTrios()){
			if (pTrio.getProductOrder0() != null && pTrio.getProductOrder0().getQuantity() > 0){
				productOrders.add(pTrio.getProductOrder0());
				totalAmount = totalAmount.add(pTrio.getProductOrder0().getProduct().getProductPrice().multiply(new BigDecimal(pTrio.getProductOrder0().getQuantity())));
			}
			if (pTrio.getProductOrder1() != null && pTrio.getProductOrder1().getQuantity() > 0){
				productOrders.add(pTrio.getProductOrder1());
				totalAmount = totalAmount.add(pTrio.getProductOrder1().getProduct().getProductPrice().multiply(new BigDecimal(pTrio.getProductOrder1().getQuantity())));
			}
			if (pTrio.getProductOrder2() != null && pTrio.getProductOrder2().getQuantity() > 0){
				productOrders.add(pTrio.getProductOrder2());
				totalAmount = totalAmount.add(pTrio.getProductOrder2().getProduct().getProductPrice().multiply(new BigDecimal(pTrio.getProductOrder2().getQuantity())));
			}
		}
		OrderForm orderForm = new OrderForm();
		orderForm.setProductOrders(productOrders);
		orderForm.setTotalAmount(totalAmount);
		orderForm.setPaymentMethod(PaymentMethod.ON_DELIVERY);
		orderForm.setInvoiceProvince(provinceInventory.findByProvinceCode("34"));
		orderForm.setProvince(provinceInventory.findByProvinceCode("34"));
		model.addAttribute("orderForm", orderForm);
		if (provinceList.isEmpty()){
			provinceList = provinceInventory.findAll();
		}
		model.addAttribute(provinceList);
		return "order-entry";
	}

	@RequestMapping(value = "/order-entry", method=RequestMethod.POST)
	public String order(@ModelAttribute("orderForm") @Valid OrderForm orderForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws ServletException{
		logger.info("Name " + orderForm.getFirstName() + " " + orderForm.getLastName());
		if (result.hasErrors()){
			return "order-entry";
		}
		try {
			orderService.insertOrder(orderForm);
		} catch (Exception e) {
			RaisedLineUtil.flash(redirectAttributes, "error", "orderFailed");
			e.printStackTrace();
		}
		RaisedLineUtil.flash(redirectAttributes, "success", "orderSuccessful");
		
		return "redirect:/home";
	}
	

	private ProductOrder prepareOrderItem(Product product) {
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProduct(product);
		productOrder.setQuantity(0);
		return productOrder;
	}

	public List<Province> getProvinceList() {
		if (provinceList.isEmpty()){
			provinceList = provinceInventory.findAll();
		}
		return provinceList;
	}

}
