package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Contact;
import za.ac.cput.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService implements IContactService{

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact read(int id) {
        return this.contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact update(Contact contact) {
        if (contactRepository.existsById(contact.getContactID())) {
            return contactRepository.save(contact);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}