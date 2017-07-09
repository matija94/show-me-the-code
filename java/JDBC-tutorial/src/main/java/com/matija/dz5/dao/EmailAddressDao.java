package com.matija.dz5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.matija.dz5.data.Contact.EmailAddress;

public class EmailAddressDao {

	private static EmailAddressDao instance;
	private static Connection con;
	private static Statement statement;
	
	private static final String INSERT_ONE = "INSERT INTO dz5.email_address(id, address, description, contactid) VALUES(?,?,?,?)";
	private static final String FIND_ALL_BY_CONTACT_ID_TEMPLATE = "SELECT a.id, a.address, a.description FROM dz5.contact c INNER JOIN dz5.email_address a ON c.id=%s";
	private static final String DELETE_ONE_TEMPLATE = "DELETE FROM dz5.email_address a WHERE a.id=%s";
	
	private static AtomicLong id = new AtomicLong(0);
	
	private EmailAddressDao() {}
	
	public static EmailAddressDao get() throws SQLException {
		if (instance == null) {
			instance = new EmailAddressDao();
			con = ConnectionManager.getConnection();
		}
		return instance;
	}
	
	public long insert(EmailAddress emailAddress) throws SQLException {
		statement = con.prepareStatement(INSERT_ONE);
		PreparedStatement prepState = (PreparedStatement) statement;
		prepState.setLong(1, id.incrementAndGet());
		prepState.setString(2, emailAddress.getAddress());
		prepState.setString(3, emailAddress.getDescription());
		prepState.setLong(4, emailAddress.getContact().getId());
		prepState.executeUpdate();
		return id.get();
	}
	
	public List<EmailAddress> find(long contactId) throws SQLException {
		String findAllByContactIdQ = String.format(FIND_ALL_BY_CONTACT_ID_TEMPLATE, Long.toString(contactId));
		statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = statement.executeQuery(findAllByContactIdQ);
		
		List<EmailAddress> addresses = new ArrayList<EmailAddress>();
		
		while(rs.next()) {
			addresses.add(new EmailAddress(rs.getLong(1), rs.getString(2), rs.getString(3)));
		}

		return addresses;
	}
	
	public void delete(long emailAddressId) throws SQLException {
		statement=con.createStatement();
		String deleteOneById = String.format(DELETE_ONE_TEMPLATE, Long.toString(emailAddressId));
		statement.executeUpdate(deleteOneById);
	}
	
	
}
