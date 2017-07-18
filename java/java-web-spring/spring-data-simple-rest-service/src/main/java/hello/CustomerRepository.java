package hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>{


	public List<Customer> findAllByFirstNameAndLastName(String firstName, String lastName);

	public List<Customer> findAllByFirstName(String firstName);

	public List<Customer> findAllByLastName(String lastName);
}
