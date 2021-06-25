package app.controller.dashboard;

import app.model.section.Section;
import app.model.section.SectionRepo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
@Secured("ROLE_ADMIN")
public class HomePageController {


    private final SectionRepo sectionRepo;

    private boolean updateSectionSuccessfully;

    private boolean updateSectionError;


    public HomePageController(SectionRepo sectionRepo) {

        this.sectionRepo = sectionRepo;
    }


    @GetMapping({"admin", "home-page"})
    public String index(Model model) {

        model.addAttribute("sections", sectionRepo.findAll());

        return "dashboard/home-page";
    }


    @GetMapping("section")
    public String sectionDetailsPage(int id, Model model) {

        Optional<Section> sectionOptional = sectionRepo.findById(id);

        if (sectionOptional.isPresent()) {

            Section section = sectionOptional.get();

            model.addAttribute("section", section);

        }

        setMessages(model);

        return "dashboard/section";
    }


    @PostMapping("section-update")
    public String sectionUpdate(Section section) {

        sectionRepo.save(section);

        updateSectionSuccessfully = true;

        return "redirect:/section?id=" + section.getId();
    }



    /* ============================================== Helper Method =============================================== */


    private void setMessages(Model model) {

        model.addAttribute("updateSectionSuccessfully", updateSectionSuccessfully);
        updateSectionSuccessfully = false;

        model.addAttribute("updateSectionError", updateSectionError);
        updateSectionError = false;

    }


}
