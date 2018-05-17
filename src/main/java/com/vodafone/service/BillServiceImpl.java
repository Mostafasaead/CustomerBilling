package com.vodafone.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.dao.BillDAO;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Bill;
import com.vodafone.model.Customer;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	BillDAO billDAO;
	@Autowired
	CustomerService customerService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BillServiceImpl.class);

	@Override
	public List<Bill> findAllBills(long customerID) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Customer customer = customerService.findById(customerID);
		if (customerID > 0 && customer != null) {
			return billDAO.findAll(customer);

		} else {
			LOGGER.error("user not found");
			throw new UserNotFoundException();
		}

	}

}
