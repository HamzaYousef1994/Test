package app.controller.dashboard;

import app.model.email.EmailSettings;
import app.model.email.EmailSettingsRepo;
import app.model.user.User;
import app.model.user.UserRepository;
import app.model.user.details.MyUserDetails;
import app.service.email.EmailService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Secured("ROLE_ADMIN")
public class SettingsController {


    private final EmailService emailService;

    private final UserRepository userRepo;

    private final EmailSettingsRepo emailSettingsRepo;


    public SettingsController(EmailService emailService, UserRepository userRepo,
                              EmailSettingsRepo emailSettingsRepo) {

        this.userRepo = userRepo;
        this.emailService = emailService;
        this.emailSettingsRepo = emailSettingsRepo;
    }


    @GetMapping("/settings")
    public String settingsPage(Model model) {

        EmailSettings emailSettings = emailSettingsRepo.findFirstBy();

        if (emailSettings == null)
            emailSettings = new EmailSettings();

        model.addAttribute("emailSettings", emailSettings);

        model.addAttribute("emailErrors", EmailService.emailErrors);

        return "dashboard/settings";
    }


    @GetMapping("send-email")
    public String sendEmail(Authentication authentication) {

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        Optional<User> optionalUser = userRepo.findById(userDetails.getId());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            emailService.sendEmail("Test Email - From App Settings","Test Email", user.getEmail());

        }

        return "redirect:/settings";
    }


    @PostMapping("edit-email")
    public String editEmail(EmailSettings emailSettings) {

        EmailSettings settings = emailSettingsRepo.findFirstBy();

        if (settings == null)
            settings = new EmailSettings();

        if (!emailSettings.getPassword().trim().equals(""))
            settings.setPassword(emailSettings.getPassword().trim());

        settings.setUsername(emailSettings.getUsername().trim());
        settings.setHost(emailSettings.getHost().trim());
        settings.setPort(emailSettings.getPort());

        emailSettingsRepo.save(settings);


        return "redirect:/settings";
    }


}