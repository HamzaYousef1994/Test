package app;

import app.model.section.Section;
import app.model.section.SectionRepo;
import app.model.user.User;
import app.model.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppRunner implements CommandLineRunner {


    private final UserRepository userRepository;

    private final SectionRepo sectionRepo;

    private final PasswordEncoder encoder;


    public AppRunner(UserRepository userRepository, SectionRepo sectionRepo, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.sectionRepo = sectionRepo;
        this.encoder = encoder;
    }


    @Override
    public void run(String... args) {


        if (userRepository.count() == 0) {

            User admin = new User();

            admin.setUserName("admin");

            admin.setPassword(encoder.encode("Admin@1234"));

            admin.setRole("ROLE_ADMIN");

            admin.setFirstName("eQ");
            admin.setLastName("eQ");
            admin.setEmail("info@eq-jo.com");

            admin.setEnabled(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            admin.setAccountNonExpired(true);

            userRepository.save(admin);

        }


        if (sectionRepo.count() == 0) {

            Section home = new Section();
            home.setName("Home");

            Section about = new Section();
            about.setName("About");

            Section contact = new Section();
            contact.setName("Contact");

            Section organizations = new Section();
            organizations.setName("Organizations");

            Section howItWorks = new Section();
            howItWorks.setName("How it Works");

            sectionRepo.save(home);
            sectionRepo.save(about);
            sectionRepo.save(organizations);
            sectionRepo.save(contact);
            sectionRepo.save(howItWorks);
            

        }


    }


}
