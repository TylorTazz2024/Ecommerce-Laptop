package za.ac.cput.service;

import java.util.List;
import za.ac.cput.domain.Contact;


public interface IContactService {
    Contact create(Contact contact);
    Contact read(int id);
    Contact update(Contact contact);
    boolean delete(int id);

    List<Contact> getAllContacts();
      }
