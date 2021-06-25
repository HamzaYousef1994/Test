package app.controller.login;

import app.model.user.User;
import app.model.user.UserRepository;
import app.model.user.helper.ForgotPassword;
import app.model.user.helper.ForgotPasswordRepo;
import app.model.user.helper.UserPassword;
import app.service.email.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Random;


@Controller
public class ForgotPasswordController {


    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final ForgotPasswordRepo forgotPasswordRepo;

    private final EmailService emailService;

    private boolean emailNotExist = false;

    public ForgotPasswordController(PasswordEncoder encoder, UserRepository userRepository,
                                    ForgotPasswordRepo forgotPasswordRepo, EmailService emailService) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.forgotPasswordRepo = forgotPasswordRepo;
        this.emailService = emailService;
    }


    @GetMapping("forgot-password")
    public String forgotPasswordPage(Model model) {

        model.addAttribute("emailNotExist", emailNotExist);

        emailNotExist = false;

        return "login/forgot-password";
    }


    @PostMapping("submit-email")
    public String submitEmail(String email, Model model) {

        User user = userRepository.findUserByEmail(email);

        if (user != null) {

            int code = new Random().nextInt(100000) + 100000;

            ForgotPassword forgotPassword = new ForgotPassword();

            forgotPassword.setEmail(user.getEmail());
            forgotPassword.setUserID(user.getId());

            forgotPassword.setCode(code);

            forgotPasswordRepo.save(forgotPassword);


            String message = "Reset Password Code :  " + code;

            model.addAttribute("email", user.getEmail());

            new Thread(() -> emailService.sendEmail(message,"Reset Password", user.getEmail())).start();


            return "login/submit-code";

        }

        emailNotExist = true;

        return "redirect:/forgot-password";

    }


    @PostMapping("code-check")
    public String codeCheck(int code, String email, Model model) {


        boolean isExists = forgotPasswordRepo.existsForgotPasswordByCodeAndEmail(code, email);

        if (isExists) {

            model.addAttribute("email", email);

            model.addAttribute("code", code);

            return "login/reset-password";

        } else {

            model.addAttribute("email", email);

            model.addAttribute("incorrectCode", true);

            return "login/submit-code";

        }


    }


    @PostMapping("reset-password")
    public String resetPassword(int code, String email, @Valid UserPassword userPassword,
                                Model model, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {

            boolean isExists = forgotPasswordRepo.existsForgotPasswordByCodeAndEmail(code, email);

            if (isExists) {

                ForgotPassword forgotPassword = forgotPasswordRepo.findForgotPasswordByCodeAndEmail(code, email);

                if (userPassword.getNewPassword().trim().equals(userPassword.getRePassword().trim())) {

                    User user = userRepository.findById(forgotPassword.getUserID()).get();

                    String pass = encoder.encode(userPassword.getNewPassword());

                    user.setPassword(pass);

                    userRepository.save(user);

                    forgotPasswordRepo.deleteForgotPasswordByUserID(forgotPassword.getUserID());

                    return "login/login";

                }

            }
        }


        model.addAttribute("email", email);

        model.addAttribute("code", code);

        return "login/reset-password";

    }


}