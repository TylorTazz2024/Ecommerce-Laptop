package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Contact;
import za.ac.cput.factory.ContactFactory;
import za.ac.cput.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Contact create(@RequestBody Contact contact) {

        Contact newContact = ContactFactory.createContact(contact.getEmail(), contact.getPhoneNumber());
        return service.create(newContact);
    }

    @GetMapping("/read/{id}")
    public Contact read(@PathVariable int id) {
        return service.read(id);
    }

    @PutMapping("/update")
    public Contact update(@RequestBody Contact contact) {
        return service.update(contact);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return service.delete(id);
    }

    @GetMapping("/all")
    public List<Contact> getAll() {
        return service.getAllContacts();
    }
}
