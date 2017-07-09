package com.matija.dz5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.matija.dz5.data.Contact;
import com.matija.dz5.data.Contact.EmailAddress;
import com.matija.dz5.data.Contact.PhoneNumber;

public class ContactDao {

	private static ContactDao instance;
	private static Connection conn;
	private static Statement statement;
	
	private static final String FIND_ONE_BY_NAME_AND_SURNAME_TEMPLATE = "SELECT * FROM dz5.contact c WHERE c.name='%s' and c.surname='%s'";
	private static final String INSERT_ONE = "INSERT INTO dz5.contact(id,name,surname) values(?,?,?)";
	private static final String DELETE_ONE_TEMPLATE = "DELETE FROM dz5.contact c WHERE c.id='%s'";
	
	private static AtomicLong id = new AtomicLong(0);
	
	private ContactDao(){}
	
	public static ContactDao get() throws SQLException {
		if (instance == null) {
			instance = new ContactDao();
			conn = ConnectionManager.getConnection();
		}
		return instance;
	}

	
	
	public void insert(Contact contact) throws SQLException {
		// since we do not want contact with duplicate name and surname together combined
		//there is no reason to check for duplicates in java, but make a unique constraint on (name,surname)
		contact.setId(id.incrementAndGet());
		
		PreparedStatement prepStatement= conn.prepareStatement(INSERT_ONE);
		prepStatement.setLong(1, contact.getId());
		prepStatement.setString(2, contact.getName());
		prepStatement.setString(3, contact.getSurname());
		prepStatement.executeUpdate();
		
		List<? extends Object> childObjects = contact.getEmailAddresses();
		
		if (childObjects != null && !childObjects.isEmpty()) {
			for(Object emailAddress : childObjects) {
				EmailAddress address = (EmailAddress) emailAddress;
				address.setContact(contact);
				EmailAddressDao.get().insert(address);
			}
		}
		childObjects = contact.getPhoneNumbers();
		if (childObjects != null && !childObjects.isEmpty()) {
			for (Object childObject : childObjects) {
				PhoneNumber number = (PhoneNumber) childObject;
				number.setContact(contact);
				PhoneNumberDao.get().insert(number);
			}
		}
	}
	
	public Contact find(String name, String surname) throws SQLException {
		statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String findOneByNameAndSurname = String.format(FIND_ONE_BY_NAME_AND_SURNAME_TEMPLATE, name, surname);
		ResultSet rs = statement.executeQuery(findOneByNameAndSurname);
		
		if (rs.first()) {
			Contact contact = new Contact(rs.getLong(1), rs.getString(2), rs.getString(3), null, null);
			contact.setEmailAddresses(EmailAddressDao.get().find(contact.getId()));
			contact.setPhoneNumbers(PhoneNumberDao.get().find(contact.getId()));
			return contact;
		}
		
		return null;
	}
	
	public void delete(long contactId) throws SQLException {
		// cascading while deleting is handled by the database
		statement = conn.createStatement();
		String deleteOne = String.format(DELETE_ONE_TEMPLATE, Long.toString(contactId));
		statement.executeUpdate(deleteOne);
	}
	
}
