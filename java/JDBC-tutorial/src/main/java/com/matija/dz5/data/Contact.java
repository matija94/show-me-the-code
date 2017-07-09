package com.matija.dz5.data;

import java.util.List;

public class Contact {

	

	private Long id;
	private String name;
	private String surname;
	private List<PhoneNumber> phoneNumbers;
	private List<EmailAddress> emailAddresses;

	public Contact(){}
	
	public Contact(Long id, String name, String surname, List<PhoneNumber> phoneNumbers,
			List<EmailAddress> emailAddresses) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phoneNumbers = phoneNumbers;
		this.emailAddresses = emailAddresses;
	}

	
	
	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", surname=" + surname + ", phoneNumbers=" + phoneNumbers
				+ ", emailAddresses=" + emailAddresses + "]";
	}
	
	public static class PhoneNumber {

		private Long id;
		private String number;
		private String description;
		private Contact contact;
		
		
		public PhoneNumber() {}
		
		public PhoneNumber(Long id, String number, String description) {
			this.id = id;
			this.number = number;
			this.description = description;
		}

		public String getNumber() {
			return number;
		}

		public String getDescription() {
			return description;
		}
	
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}

		@Override
		public String toString() {
			return "PhoneNumber [id=" + id + ", number=" + number + ", description=" + description + "]";
		}
		
	}
	
	public static class EmailAddress {

		private Long id;
		private String address;
		private String description;
		private Contact contact;
		
		public EmailAddress(){}
		
		public EmailAddress(Long id, String address, String description) {
			this.id = id;
			this.address = address;
			this.description = description;
		}

		
		public String getAddress() {
			return address;
		}

		public String getDescription() {
			return description;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}

		@Override
		public String toString() {
			return "EmailAddress [id=" + id + ", address=" + address + ", description=" + description + "]";
		}
		
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(List<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
	
	
	
}
