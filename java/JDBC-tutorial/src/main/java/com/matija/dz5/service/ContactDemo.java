package com.matija.dz5.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.matija.dz5.data.Contact;
import com.matija.dz5.data.Contact.EmailAddress;
import com.matija.dz5.data.Contact.PhoneNumber;

public class ContactDemo {

	
	public static void main(String[] args) throws SQLException {
		
		List<EmailAddress> addresses = new ArrayList<>();
		addresses.add(buildAddress("Milentija Popovica", "private"));
		List<PhoneNumber> numbers = new ArrayList<>();
		numbers.add(buildPhoneNumber("060", "private"));
		
		List<Contact> contacts = new ArrayList<>();
		contacts.add(buildContact(0, "Matija", "Lukovic", addresses, numbers));
		contacts.add(buildContact(0, "Matija", "Lukovic", addresses, numbers)); // adding same (name,surname) combination would throw an sqlException for duplicate key value since we have a constraint in DB

		for (Contact contact : contacts ) {
			ContactService.get().addNewContact(contact);
		}
		Contact contact = ContactService.get().findContact("Matija", "Lukovic");
		System.out.println(contact);
	
	
		
		ContactService.get().deleteContact(buildContact(1l, "Matija", "Lukovic", null, null));
	}
	
	private static Contact buildContact(long id, String name, String surname, List<EmailAddress> addresses, List<PhoneNumber> phoneNumbers) {
		Contact contact = new Contact();
		contact.setId(id);
		contact.setEmailAddresses(addresses);
		contact.setPhoneNumbers(phoneNumbers);
		contact.setName(name);
		contact.setSurname(surname);
		return contact;
	}
	
	private static EmailAddress buildAddress(String addressVal, String description) {
		EmailAddress address = new EmailAddress();
		address.setAddress(addressVal);
		address.setDescription(description);
		return address;
	}
	
	private static PhoneNumber buildPhoneNumber(String phoneNumberVal, String description) {
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setNumber(phoneNumberVal);
		phoneNumber.setDescription(description);
		return phoneNumber;
	}
}
