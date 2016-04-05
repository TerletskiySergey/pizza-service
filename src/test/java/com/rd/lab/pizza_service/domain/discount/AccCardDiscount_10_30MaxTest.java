package com.rd.lab.pizza_service.domain.discount;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.order.status.DefaultNewStatus;
import com.rd.lab.pizza_service.domain.pizza.Pizza;

public class AccCardDiscount_10_30MaxTest {

	private static AccCard card;
	private static List<Pizza> pizzas;
	private static Order order;
	private static Customer cust;

	private Discount disc;

	@BeforeClass
	public static void initTestStat() {
		card = new AccCard(new BigDecimal("1000"));
		Pizza p1 = new Pizza("pizza1", new BigDecimal("1"), Pizza.Type.Meat);
		Pizza p2 = new Pizza("pizza2", new BigDecimal("2"), Pizza.Type.Meat);
		Pizza p3 = new Pizza("pizza3", new BigDecimal("3"), Pizza.Type.Meat);
		Pizza p4 = new Pizza("pizza4", new BigDecimal("4"), Pizza.Type.Meat);
		Pizza p5 = new Pizza("pizza5", new BigDecimal("5"), Pizza.Type.Meat);
		pizzas = Arrays.asList(p1, p2, p3, p4, p5);
		Address addr = new Address("postalCode", "country", "city", "addrLine");
		cust = new Customer("name", addr, card);
		order = new Order(cust, new DefaultNewStatus());
		order.setPizzas(pizzas);
	}

	@Before
	public void initTest() {
		disc = new AccCardDiscount_10_30Max(pizzas, card);
	}

	@Test
	public void testGetDiscount_pizzasIsNull_shouldReturnZero() {
		disc = new AccCardDiscount_10_30Max();
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_cardIsNull_shouldReturnZero() {
		disc = new AccCardDiscount_10_30Max();
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_cardDiscountLessThanPizzas30_shouldReturnCorrectValue() {
		disc = new AccCardDiscount_10_30Max(pizzas, new AccCard(new BigDecimal("10")));
		assertEquals(1, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_cardDiscountHigherThanPizzas30_shouldReturnCorrectValue() {
		assertEquals(4.5, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_emptyCard_shouldReturnZero() {
		disc = new AccCardDiscount_10_30Max(pizzas, new AccCard(BigDecimal.ZERO));
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testGetDiscount_noPizzas_shouldReturnZero() {
		List<Pizza> emptyList = Arrays.asList();
		disc = new AccCardDiscount_10_30Max(emptyList, card);
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testUpdateInstance_incopatibleType_shouldNotReact() {
		assertEquals(4.5, disc.getDiscount().doubleValue(), 0);
		disc.updateInstance(new Object());
		assertEquals(4.5, disc.getDiscount().doubleValue(), 0);
	}

	@Test
	public void testUpdateInstance_compatibleType_shouldUpdate() {
		Order emptyOrder = new Order(cust, new DefaultNewStatus());
		disc.updateInstance(emptyOrder);
		assertEquals(0, disc.getDiscount().doubleValue(), 0);
		disc.updateInstance(order);
		assertEquals(4.5, disc.getDiscount().doubleValue(), 0);
	}
}
