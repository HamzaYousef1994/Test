package app.controller.dashboard;

import app.model.user.User;
import app.model.user.UserRepository;
import app.model.user.details.MyUserDetails;
import app.model.user.helper.UserPassword;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;


@Controller
@Secured("ROLE_ADMIN")
public class ProfileController {


    private final UserRepository userRepo;

    private final PasswordEncoder encoder;

    private boolean updatePasswordSuccessful;

    private boolean updatePasswordError;

    private boolean updateProfileSuccessful;

    private boolean updateProfileError;


    public ProfileController(UserRepository userRepo, PasswordEncoder encoder) {

        this.userRepo = userRepo;
        this.encoder = encoder;
    }


    @GetMapping("profile")
    public String profilePage(Model model) {

        User user = Objects.requireNonNullElseGet(userRepo.findFirstBy(), User::new);

        model.addAttribute("user", user);

        setMessages(model);

        return "dashboard/profile";
    }


    @PostMapping("edit-profile")
    public String editProfile(Authentication authentication, User u) {

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        Optional<User> optionalUser = userRepo.findById(userDetails.getId());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            user.setEmail(u.getEmail());
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());

            userRepo.save(user);

            updateProfileSuccessful = true;

        } else
            updatePasswordError = true;


        return "redirect:/profile";
    }


    @PostMapping("edit-password")
    public String editPassword(Authentication authentication, @Valid UserPassword userPassword) {


        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        Optional<User> optionalUser = userRepo.findById(userDetails.getId());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if (encoder.matches(userPassword.getOldPassword(), user.getPassword())) {

                if (userPassword.getNewPassword().equals(userPassword.getRePassword())) {

                    user.setPassword(encoder.encode(userPassword.getNewPassword()));

                    userRepo.save(user);

                    updatePasswordSuccessful = true;

                } else
                    updatePasswordError = true;

            } else
                updatePasswordError = true;

        }

        return "redirect:/profile";
    }




    /* ============================================== Helper Method =============================================== */


    private void setMessages(Model model) {

        model.addAttribute("updatePasswordSuccessful", updatePasswordSuccessful);
        updatePasswordSuccessful = false;

        model.addAttribute("updatePasswordError", updatePasswordError);
        updatePasswordError = false;

        model.addAttribute("updateProfileSuccessful", updateProfileSuccessful);
        updateProfileSuccessful = false;

        model.addAttribute("updateProfileError", updateProfileError);
        updateProfileError = false;

    }

}