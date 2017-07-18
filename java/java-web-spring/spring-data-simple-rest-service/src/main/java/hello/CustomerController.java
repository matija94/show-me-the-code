package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	CustomerRepository repo;
	
	@RequestMapping("/customer")
	public List<Customer> customers(@ModelAttribute CustomerParams params) {

		List<Customer> customers = new ArrayList<>();
		
		String firstname = params.getFirstName();
		String lastname = params.getLastName();
	
		if (firstname != null && lastname != null) {
			customers = repo.findAllByFirstNameAndLastName(firstname, lastname);
		}
		
		else if (firstname != null) {
			customers = repo.findAllByFirstName(firstname);
		}
		
		else if (lastname != null) {
			customers = repo.findAllByLastName(lastname);
		}
		
		// both are empty, let's return all
		else {
			customers = (List<Customer>) repo.findAll();
		}
		
		return customers;
	}
	
}
