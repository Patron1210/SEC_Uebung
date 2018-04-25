package hello;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Controller
public class WebController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/userform")
    public String showForm(UserForm userForm) {

        return "userform";
    }

    @GetMapping("/")
    public String showForm(PersonForm personForm) {

        return "loginform";
    }


    @PostMapping("/userform")
    public String getUserData(@Valid UserForm userForm, BindingResult bindingResult) {
        if(userForm.getClient().equals("Hans")){ // Query DB
            return "redirect:/results";
        }else{
            return "userform";
        }


    }

    @PostMapping("/")
    public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "loginform";
        }

        return "redirect:/userform";
    }


}