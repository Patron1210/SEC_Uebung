package hello;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.sql.*;

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
        if(checkMail(userForm.getClient())){
            return "redirect:/results";
        }
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

    public boolean checkMail(String email){
        try
        {
            // create our mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://192.168.56.3";
            Class.forName(myDriver);
            //TODO: Passwort darf nicht als Klartext da stehen
            Connection conn = DriverManager.getConnection(myUrl, "sec_read", "pai1hdsfa!shjASDFfdpasdhf");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT Email FROM User";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                if(email.equals(rs.getString("Email")));
                st.close();
                return true;
            }
            st.close();
            return false;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

}