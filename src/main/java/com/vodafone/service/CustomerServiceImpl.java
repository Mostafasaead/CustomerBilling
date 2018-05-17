package com.vodafone.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.controller.CustomerRestController;
import com.vodafone.dao.CustomerDAO;
import com.vodafone.dto.CustomerUpdateDTO;
import com.vodafone.dto.CustomerUpdateRepresentation;
import com.vodafone.exception.DatabaseException;
import com.vodafone.exception.ServiceException;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static List<Customer> customers;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	private CustomerDAO customerDAOImpl;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestController.class);

	@PostConstruct
	public void populateList() {
		customers = customerDAO.findAll();
	}

	@Override
	public Customer findById(long id) throws UserNotFoundException {
		customers.forEach(System.out::println);
		Customer customerFound = customers.stream().filter(customer -> id == customer.getId().longValue()).findAny()
				.orElse(null);
		if (customerFound == null) {
			LOGGER.error("user not found");
			throw new UserNotFoundException();
		} else
			return customerFound;
	}

	@Override
	public Customer findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Customer> findAllCustomers() {
		return customerDAO.findAll();
	}

	@Override
	public void deleteAllCustomers() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCustomerExist(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CustomerUpdateRepresentation updateCustomer(CustomerUpdateDTO customerUpdateDTO)
			throws DatabaseException, UserNotFoundException {

		Customer customer = null;

		customer = findById(customerUpdateDTO.getId());

		if (customer == null) {
			throw new UserNotFoundException("Customer with id " + customerUpdateDTO.getId() + " was not found");
		}
		customer.setAddress(customerUpdateDTO.getAddress());
		customer.setFullName(customerUpdateDTO.getFullName());
		customer.setAge(customerUpdateDTO.getAge());
		customer.setMobileNumber(customerUpdateDTO.getMobileNumber());
		customerDAOImpl.save(customer);

		CustomerUpdateRepresentation customerUpdateRepresentation = new CustomerUpdateRepresentation();

		customerUpdateRepresentation.setAddress(customer.getAddress());

		customerUpdateRepresentation.setFullName(customer.getFullName());

		customerUpdateRepresentation.setAge(customer.getAge());
		customerUpdateRepresentation.setId(customer.getId());
		customerUpdateRepresentation.setMobileNumber(customer.getMobileNumber());
		return customerUpdateRepresentation;

	}

	@Override
	public void deleteCustomerById(Long id) throws ServiceException, DatabaseException {

		Customer customer = null;
		try {
			customer = findById(id);
		} catch (Exception ex) {
			LOGGER.error("error finding customer with id " + id + " : " + ex.getMessage());
			throw new ServiceException("Error finding customer id " + id + " : " + ex.getMessage());
		}

		if (customer == null) {
			LOGGER.warn("No customer found with id " + id);
			throw new UserNotFoundException("No customer found with id " + id);

		} else {
			try {
				customerDAOImpl.delete(customer);
				LOGGER.info("Customer with id " + id + " is now gone");
			} catch (Exception ex) {
				LOGGER.error("Error while finding customer with id " + id + " : " + ex.getMessage());
				throw new DatabaseException("Error finding customer with id " + id + " : " + ex.getMessage());

			}
		}
	}
}