package com.matija.dz5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.matija.dz5.data.Contact.PhoneNumber;

public class PhoneNumberDao {

	private static PhoneNumberDao instance;
	private static Connection con;
	private static Statement statement;
	
	private static final String INSERT_ONE = "INSERT INTO dz5.phone_number(id, number, description, contactid) VALUES(?,?,?,?)";
	private static final String FIND_ALL_BY_CONTACT_ID_TEMPLATE = "SELECT n.id, n.number, n.description FROM dz5.contact c INNER JOIN dz5.phone_number n ON c.id=%s";
	private static final String DELETE_ONE_TEMPLATE = "DELETE FROM dz5.phone_number n WHERE n.id=%s";
	
	private static AtomicLong id = new AtomicLong(0);
	
	private PhoneNumberDao() {}
	
	public static PhoneNumberDao get() throws SQLException {
		if (instance == null) {
			instance = new PhoneNumberDao();
			con = ConnectionManager.getConnection();
		}
		return instance;
	}
	
	public long insert(PhoneNumber phoneNumber) throws SQLException {
		statement = con.prepareStatement(INSERT_ONE);
		PreparedStatement prepState = (PreparedStatement) statement;
		prepState.setLong(1, id.incrementAndGet());
		prepState.setString(2, phoneNumber.getNumber());
		prepState.setString(3, phoneNumber.getDescription());
		prepState.setLong(4, phoneNumber.getContact().getId());
		prepState.executeUpdate();
		return id.get();
	}
	
	public List<PhoneNumber> find(long contactId) throws SQLException {
		String findAllByContactIdQ = String.format(FIND_ALL_BY_CONTACT_ID_TEMPLATE, Long.toString(contactId));
		statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = statement.executeQuery(findAllByContactIdQ);
		
		List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		
		while(rs.next()) {
			phoneNumbers.add(new PhoneNumber(rs.getLong(1), rs.getString(2), rs.getString(3)));
		}

		return phoneNumbers;
	}
	
	public void delete(long phoneNumberId) throws SQLException {
		statement=con.createStatement();
		String deleteOneById = String.format(DELETE_ONE_TEMPLATE, Long.toString(phoneNumberId));
		statement.executeUpdate(deleteOneById);
	}
	
}
