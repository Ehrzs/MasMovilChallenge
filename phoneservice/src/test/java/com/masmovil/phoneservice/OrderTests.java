package com.masmovil.phoneservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.masmovil.phoneservice.domain.Order;
import com.masmovil.phoneservice.domain.Phone;
import com.masmovil.phoneservice.repository.OrderRepository;
import com.masmovil.phoneservice.repository.PhoneRepository;
import com.masmovil.phoneservice.service.OrderResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneServiceApplication.class)
public class OrderTests {

	private static final String DEFAULT_CUSTOMER_NAME = "John";
	private static final String DEFAULT_CUSTOMER_SURNAME = "Doe";
	private static final String DEFAULT_CUSTOMER_EMAIL = "john.doe@gmail.com";

	private static final String DEFAULT_PHONE_1_IMAGE = "urlImage";
	private static final String DEFAULT_PHONE_1_NAME = "iCheap";
	private static final String DEFAULT_PHONE_1_DESCRIPTION = "Cheapest phone ever";
	private static final Double DEFAULT_PHONE_1_PRICE = 1.99;

	private static final String DEFAULT_PHONE_2_IMAGE = "urlImage 2";
	private static final String DEFAULT_PHONE_2_NAME = "iExpensive";
	private static final String DEFAULT_PHONE_2_DESCRIPTION = "Best phone ever because it´s expensive";
	private static final Double DEFAULT_PHONE_2_PRICE = 1599.99;

	private MockMvc restOrderMockMvc;

	private Order order;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	public static Order createEntity() {

		Order order = new Order();
		order.setName(DEFAULT_CUSTOMER_NAME);
		order.setSurname(DEFAULT_CUSTOMER_SURNAME);
		order.setEmail(DEFAULT_CUSTOMER_EMAIL);

		Phone phone = new Phone();
		phone.setImage(DEFAULT_PHONE_1_IMAGE);
		phone.setName(DEFAULT_PHONE_1_NAME);
		phone.setDescription(DEFAULT_PHONE_1_DESCRIPTION);
		phone.setPrice(DEFAULT_PHONE_1_PRICE);

		order.setPhones(new ArrayList<>());	
		order.getPhones().add(phone);	

		phone = new Phone();
		phone.setImage(DEFAULT_PHONE_2_IMAGE);
		phone.setName(DEFAULT_PHONE_2_NAME);
		phone.setDescription(DEFAULT_PHONE_2_DESCRIPTION);
		phone.setPrice(DEFAULT_PHONE_2_PRICE);

		order.getPhones().add(phone);

		return order;
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final OrderResource orderResource = new OrderResource(orderRepository);
		this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource).build();
	}

	@Before
	public void initTest() {
		order = createEntity();
	}

	@Test
	public void getOrderList() throws Exception {

		Phone phone = phoneRepository.saveAndFlush(order.getPhones().get(0));
		order.getPhones().get(0).setId(phone.getId());
		phone = phoneRepository.saveAndFlush(order.getPhones().get(1));
		order.getPhones().get(1).setId(phone.getId());
		order = orderRepository.saveAndFlush(order);

		restOrderMockMvc.perform(get("/order")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
				.andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_CUSTOMER_SURNAME.toString())))
				.andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_CUSTOMER_EMAIL.toString())))
				.andExpect(jsonPath("$.[*].phones.[0].id").value(hasItem(order.getPhones().get(0).getId().intValue())))
				.andExpect(jsonPath("$.[*].phones.[0].name").value(hasItem(DEFAULT_PHONE_1_NAME.toString())))
				.andExpect(jsonPath("$.[*].phones.[0].description").value(hasItem(DEFAULT_PHONE_1_DESCRIPTION.toString())))
				.andExpect(jsonPath("$.[*].phones.[0].image").value(hasItem(DEFAULT_PHONE_1_IMAGE.toString())))
				.andExpect(jsonPath("$.[*].phones.[0].price").value(hasItem(DEFAULT_PHONE_1_PRICE.doubleValue())))
				.andExpect(jsonPath("$.[*].phones.[1].id").value(hasItem(order.getPhones().get(1).getId().intValue())))
				.andExpect(jsonPath("$.[*].phones.[1].name").value(hasItem(DEFAULT_PHONE_2_NAME.toString())))
				.andExpect(jsonPath("$.[*].phones.[1].description").value(hasItem(DEFAULT_PHONE_2_DESCRIPTION.toString())))
				.andExpect(jsonPath("$.[*].phones.[1].image").value(hasItem(DEFAULT_PHONE_2_IMAGE.toString())))
				.andExpect(jsonPath("$.[*].phones.[1].price").value(hasItem(DEFAULT_PHONE_2_PRICE.doubleValue())));
	}

	@Test
	public void getOrder() throws Exception {

		Phone phone = phoneRepository.saveAndFlush(order.getPhones().get(0));
		order.getPhones().get(0).setId(phone.getId());
		phone = phoneRepository.saveAndFlush(order.getPhones().get(1));
		order.getPhones().get(1).setId(phone.getId());
		order = orderRepository.saveAndFlush(order);

		restOrderMockMvc.perform(get("/order/{id}", order.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(order.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_CUSTOMER_NAME.toString()))
				.andExpect(jsonPath("$.surname").value(DEFAULT_CUSTOMER_SURNAME.toString()))
				.andExpect(jsonPath("$.email").value(DEFAULT_CUSTOMER_EMAIL.toString()))
				.andExpect(jsonPath("$.phones.[0].id").value(order.getPhones().get(0).getId().intValue()))
				.andExpect(jsonPath("$.phones.[0].name").value(DEFAULT_PHONE_1_NAME.toString()))
				.andExpect(jsonPath("$.phones.[0].description").value(DEFAULT_PHONE_1_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.phones.[0].image").value(DEFAULT_PHONE_1_IMAGE.toString()))
				.andExpect(jsonPath("$.phones.[0].price").value(DEFAULT_PHONE_1_PRICE.doubleValue()))
				.andExpect(jsonPath("$.phones.[1].id").value(order.getPhones().get(1).getId().intValue()))
				.andExpect(jsonPath("$.phones.[1].name").value(DEFAULT_PHONE_2_NAME.toString()))
				.andExpect(jsonPath("$.phones.[1].description").value(DEFAULT_PHONE_2_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.phones.[1].image").value(DEFAULT_PHONE_2_IMAGE.toString()))
				.andExpect(jsonPath("$.phones.[1].price").value(DEFAULT_PHONE_2_PRICE.doubleValue()));
	}

	@Test
	public void addOrder() throws Exception {
		
		Phone phone = phoneRepository.saveAndFlush(order.getPhones().get(0));
		order.getPhones().get(0).setId(phone.getId());
		phone = phoneRepository.saveAndFlush(order.getPhones().get(1));
		order.getPhones().get(1).setId(phone.getId());

		String orderString = restOrderMockMvc
				.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(order)))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		order = mapper.readValue(orderString, Order.class);

		restOrderMockMvc.perform(get("/order/{id}", order.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(order.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_CUSTOMER_NAME.toString()))
				.andExpect(jsonPath("$.surname").value(DEFAULT_CUSTOMER_SURNAME.toString()))
				.andExpect(jsonPath("$.email").value(DEFAULT_CUSTOMER_EMAIL.toString()))
				.andExpect(jsonPath("$.phones.[0].id").value(order.getPhones().get(0).getId().intValue()))
				.andExpect(jsonPath("$.phones.[0].name").value(DEFAULT_PHONE_1_NAME.toString()))
				.andExpect(jsonPath("$.phones.[0].description").value(DEFAULT_PHONE_1_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.phones.[0].image").value(DEFAULT_PHONE_1_IMAGE.toString()))
				.andExpect(jsonPath("$.phones.[0].price").value(DEFAULT_PHONE_1_PRICE.doubleValue()))
				.andExpect(jsonPath("$.phones.[1].id").value(order.getPhones().get(1).getId().intValue()))
				.andExpect(jsonPath("$.phones.[1].name").value(DEFAULT_PHONE_2_NAME.toString()))
				.andExpect(jsonPath("$.phones.[1].description").value(DEFAULT_PHONE_2_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.phones.[1].image").value(DEFAULT_PHONE_2_IMAGE.toString()))
				.andExpect(jsonPath("$.phones.[1].price").value(DEFAULT_PHONE_2_PRICE.doubleValue()));
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		JavaTimeModule module = new JavaTimeModule();
		mapper.registerModule(module);

		return mapper.writeValueAsBytes(object);
	}

}
