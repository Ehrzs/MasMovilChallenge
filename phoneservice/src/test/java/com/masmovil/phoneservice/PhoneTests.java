package com.masmovil.phoneservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.masmovil.phoneservice.domain.Phone;
import com.masmovil.phoneservice.repository.PhoneRepository;
import com.masmovil.phoneservice.service.PhoneResource;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneServiceApplication.class)
public class PhoneTests {

	private static final Integer DEFAULT_ID = 1;
	private static final String DEFAULT_IMAGE = "urlImage";
	private static final String DEFAULT_NAME = "iCheap";
	private static final String DEFAULT_DESCRIPTION = "Cheapest phone ever";
	private static final Double DEFAULT_PRICE = 1.99;

	private static final String EDITED_DESCRIPTION = "Cheapest phone ever - edited";

	private MockMvc restPhoneMockMvc;

	private Phone phone;

	@Autowired
	private PhoneRepository phoneRepository;

	public static Phone createEntity() {
		Phone phone = new Phone();
		phone.setId(DEFAULT_ID);
		phone.setImage(DEFAULT_IMAGE);
		phone.setName(DEFAULT_NAME);
		phone.setDescription(DEFAULT_DESCRIPTION);
		phone.setPrice(DEFAULT_PRICE);
		return phone;
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final PhoneResource phoneResource = new PhoneResource(phoneRepository);
		this.restPhoneMockMvc = MockMvcBuilders.standaloneSetup(phoneResource).build();
	}

	@Before
	public void initTest() {
		phone = createEntity();
	}

	@Test
	public void getPhoneList() throws Exception {

		phoneRepository.saveAndFlush(phone);

		restPhoneMockMvc.perform(get("/phone")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].id").value(hasItem(phone.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
				.andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
				.andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
	}

	@Test
	public void getPhone() throws Exception {

		phone = phoneRepository.saveAndFlush(phone);

		restPhoneMockMvc.perform(get("/phone/{id}", phone.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(phone.getId().toString()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
				.andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
	}

	@Test
	public void addPhone() throws Exception {

		phone.setId(null);

		String phoneString = restPhoneMockMvc
				.perform(post("/phone").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(phone)))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		phone = mapper.readValue(phoneString, Phone.class);

		restPhoneMockMvc.perform(get("/phone/{id}", phone.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(phone.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
				.andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
	}

	@Test
	public void editPhone() throws Exception {

		phone = phoneRepository.saveAndFlush(phone);
		phone.setDescription(EDITED_DESCRIPTION);

		restPhoneMockMvc
				.perform(put("/phone").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(phone)))
				.andExpect(status().isAccepted());

		restPhoneMockMvc.perform(get("/phone/{id}", phone.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(phone.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
				.andExpect(jsonPath("$.description").value(EDITED_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
				.andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		JavaTimeModule module = new JavaTimeModule();
		mapper.registerModule(module);

		return mapper.writeValueAsBytes(object);
	}

}
