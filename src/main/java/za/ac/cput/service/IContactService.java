package za.ac.cput.service;

import za.ac.cput.domain.Contact;

import java.util.List;

public interface IContactService {
    Contact create(Contact contact);
    Contact read(int id);
    Contact update(Contact contact);
    boolean delete(int id);
    List<Contact> getAllContacts();
}
