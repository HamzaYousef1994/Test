package app.controller.dashboard;

import app.model.contact.Contact;
import app.model.contact.ContactRepo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@Secured("ROLE_ADMIN")
public class ContactController {


    private final ContactRepo contactRepo;

    private boolean updateContactSuccessfully;

    private boolean updateContactError;


    public ContactController(ContactRepo contactRepo) {

        this.contactRepo = contactRepo;
    }


    @GetMapping("contact")
    public String contactPage(Model model) {

        Contact contact = Objects.requireNonNullElseGet(contactRepo.findFirstBy(), Contact::new);

        model.addAttribute("contact", contact);

        setMessages(model);

        return "dashboard/contact";
    }


    @PostMapping("update-contact")
    public String updateContact(Contact contact) {

        contactRepo.save(contact);

        updateContactSuccessfully = true;

        return "redirect:/contact";
    }






    /* ============================================== Helper Method =============================================== */

    private void setMessages(Model model) {

        model.addAttribute("updateContactSuccessfully", updateContactSuccessfully);
        updateContactSuccessfully = false;

        model.addAttribute("updateContactError", updateContactError);
        updateContactError = false;

    }


}
