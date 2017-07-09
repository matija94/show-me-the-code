create schema dz5;

create table dz5.contact
(
	id bigint NOT NULL,
	name varchar(50) NOT NULL,
  	surname varchar(50) NOT NULL,
	CONSTRAINT const_contact_name_surname UNIQUE (name,surname),
  	CONSTRAINT pk_contact PRIMARY KEY(id)
);

create table dz5.phone_number
(
	id bigint NOT NULL,
	number varchar(50) NOT NULL,
	description varchar(50),
	contactId bigint,
  	CONSTRAINT pk_number PRIMARY KEY(id),
        CONSTRAINT fk_contact_phone_number FOREIGN KEY (contactId)
        REFERENCES dz5.contact (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE
);

create table dz5.email_address
(
	id bigint NOT NULL,
	address varchar(50) NOT NULL,
  	description varchar(50),
	contactId bigint,
	CONSTRAINT pk_email_address PRIMARY KEY(id),
        CONSTRAINT fk_contact_email_address FOREIGN KEY (contactId)
        REFERENCES dz5.contact (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE
);
