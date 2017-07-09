package com.matija.dz5.service;

import java.sql.SQLException;

import com.matija.dz5.dao.ContactDao;
import com.matija.dz5.data.Contact;

public class ContactService {

	private static ContactService instance;
	
	private ContactService(){}
	
	public static ContactService get() {
		if (instance == null) {
			instance = new ContactService();
		}
		
		return instance;
	}
	
	public void addNewContact(Contact contact) throws SQLException {
		ContactDao.get().insert(contact);
	}
	
	public Contact findContact(String name, String surname) throws SQLException {
		return ContactDao.get().find(name, surname);
	}
	
	public void deleteContact(Contact contact) throws SQLException {
		ContactDao.get().delete(contact.getId());
	}
}
