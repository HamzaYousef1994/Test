package app.controller.dashboard;

import app.model.category.Category;
import app.model.category.CategoryRepo;
import app.model.organization.Organization;
import app.model.organization.OrganizationRepo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@Secured("ROLE_ADMIN")
public class OrganizationsController {


    private final OrganizationRepo organizationRepo;

    private final CategoryRepo categoryRepo;


    public OrganizationsController(OrganizationRepo organizationRepo, CategoryRepo categoryRepo) {

        this.organizationRepo = organizationRepo;
        this.categoryRepo = categoryRepo;
    }


    @GetMapping("organizations")
    public String organizationsPage(Model model) {

        model.addAttribute("organizations", organizationRepo.findAll());

        model.addAttribute("categoryNames", categoryRepo.categoryNames());

        return "dashboard/organizations";
    }


    @GetMapping("add-organization")
    public String addOrganizationPage(Model model) {

        model.addAttribute("organization", new Organization());

        model.addAttribute("categories", categoryRepo.findAll());

        return "dashboard/organization";
    }


    @GetMapping("edit-organization/{orgID}")
    public String editOrganizationPage(@PathVariable int orgID, Model model) {

        Optional<Organization> organizationOptional = organizationRepo.findById(orgID);

        if (organizationOptional.isPresent()) {

            Organization organization = organizationOptional.get();

            model.addAttribute("organization", organization);

            model.addAttribute("categories", categoryRepo.findAll());

        }

        return "dashboard/organization";
    }


    @PostMapping("remove-organization")
    public String removeOrganization(int orgID) {

        if (organizationRepo.existsById(orgID))
            organizationRepo.deleteById(orgID);

        return "redirect:/organizations";
    }


    @PostMapping("organization-control")
    public String organizationControl(@Valid Organization organization) {

        organizationRepo.save(organization);

        return "redirect:/organizations";
    }



    /*================================================ Category ============================================*/


    @GetMapping("categories")
    public String CategoriesPage(Model model) {

        model.addAttribute("categories", categoryRepo.findAll());

        model.addAttribute("newCategory", new Category());

        model.addAttribute("category", new Category());

        model.addAttribute("editCategoryModal", false);

        return "dashboard/category";
    }


    @GetMapping("edit-category/{categoryID}")
    public String editCategory(@PathVariable int categoryID, Model model) {

        Optional<Category> categoryOptional = categoryRepo.findById(categoryID);

        if (categoryOptional.isPresent()) {

            Category category = categoryOptional.get();

            model.addAttribute("category", category);

            model.addAttribute("newCategory", new Category());

            model.addAttribute("categories", categoryRepo.findAll());

            model.addAttribute("editCategoryModal", true);

        }

        return "dashboard/category";
    }


    @PostMapping("remove-category")
    public String removeCategory(int categoryID) {

        if (categoryRepo.existsById(categoryID))
            categoryRepo.deleteById(categoryID);

        return "redirect:/categories";
    }


    @PostMapping("category-control")
    public String categoryControl(Category category) {

        categoryRepo.save(category);

        return "redirect:/categories";
    }


    @GetMapping("organizations/{categoryName}")
    public String getByCategoryName(@PathVariable String categoryName, Model model) {

        model.addAttribute("organizations", organizationRepo.findAllByCategory_NameEn(categoryName));

        model.addAttribute("categoryNames", categoryRepo.categoryNames());

        return "dashboard/organizations";
    }


}
