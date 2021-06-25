package app.controller.home;


import app.model.category.CategoryRepo;
import app.model.contact.Contact;
import app.model.contact.ContactRepo;
import app.model.email.ClientMessage;
import app.model.section.Section;
import app.model.section.SectionRepo;
import app.service.captcha.CaptchaService;
import app.service.email.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {


    private final SectionRepo sectionRepo;

    private final ContactRepo contactRepo;

    private final CategoryRepo categoryRepo;

    private final CaptchaService captchaService;

    private final EmailService emailService;


    public HomeController(SectionRepo sectionRepo, ContactRepo contactRepo, CategoryRepo categoryRepo,
                          CaptchaService captchaService, EmailService emailService) {

        this.sectionRepo = sectionRepo;
        this.contactRepo = contactRepo;
        this.categoryRepo = categoryRepo;
        this.captchaService = captchaService;
        this.emailService = emailService;

    }


    @GetMapping({"/", "home"})
    public String index(Model model) {


        List<Section> sections = sectionRepo.findAll();

        Contact contact = Objects.requireNonNullElseGet(contactRepo.findFirstBy(), Contact::new);

        model.addAttribute("eqInfo", contact);

        model.addAttribute("categories", categoryRepo.findAllByOrderByCategoryOrder());


        sections.forEach(section -> {

            if (section.getName().equals("Home"))
                model.addAttribute("home", section);

            if (section.getName().equals("About"))
                model.addAttribute("about", section);

            if (section.getName().equals("Organizations"))
                model.addAttribute("organizations", section);

            if (section.getName().equals("Contact"))
                model.addAttribute("contact", section);

            if (section.getName().equals("How it Works"))
                model.addAttribute("howItWorks", section);

        });

        return "home/index";
    }


    @PostMapping("/send-message")
    public String sendMessage(HttpServletRequest request, @Valid ClientMessage clientMessage) {

        String reCaptchaResponse = request.getParameter("g-recaptcha-response");

        float score = captchaService.verify(reCaptchaResponse);

        if (score > 0.5) {

            String message = "Name : " + clientMessage.getName() + "\n\n" + "Email : " + clientMessage.getEmail() + "\n\n"
                    + "Subject : " + clientMessage.getSubject() + "\n\n\n"
                    + clientMessage.getMessage();

            new Thread(() -> emailService.sendEmail(message, "Client Message",
                    "info@eq-jo.com")).start();

        }

        return "redirect:/home";
    }


}
