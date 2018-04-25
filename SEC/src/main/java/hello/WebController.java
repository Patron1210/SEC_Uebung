package hello;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@Controller
public class WebController implements WebMvcConfigurer {

    boolean isLoged = false;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/userform")
    public String showForm(UserForm userForm) {
        if(isLoged == true) {
            return "userform";
        }else{

            return "redirect:/";        }
    }

    @GetMapping("/")
    public String showForm(PersonForm personForm) {

        return "loginform";
    }


    @PostMapping("/userform")
    public String getUserData(@Valid UserForm userForm, BindingResult bindingResult) {
        if (checkMail(userForm.getClient())) {
            return "redirect:/results";
        } else {
            return "userform";
        }


    }

    @PostMapping("/")
    public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {
        if (checkLogin(personForm.getName(), personForm.getPass())) {
            return "redirect:/userform";
        } else {
            return "loginform";
        }
    }

    public boolean checkLogin(String email, String password) {
        try {
            // create our mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://192.168.56.3";
            Class.forName(myDriver);
            //TODO: Passwort darf nicht als Klartext da stehen
            Connection conn = DriverManager.getConnection(myUrl, "sec_read", "pai1hdsfa!shjASDFfdpasdhf");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            //TODO: Prepared Statement
            String query = "SELECT Hash, Salt FROM User WHERE Email = '"+ email +"'";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            //TODO: password muss noch gehasht werden und gesaltet

            while (rs.next()) {
                String salt = rs.getString("Salt");
                String hash = sha256(salt + password);
                if (hash.equals(rs.getString("Hash"))) {
                    st.close();
                    return true;
                }
            }
            st.close();
            return false;
        } catch (Exception e) {
            System.err.println("Wrong Login!");
            System.err.println(e.getMessage());
            return false;
        }
    }


    public boolean checkMail(String email) {
        try {
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
            while (rs.next()) {
                if (email.equals(rs.getString("Email"))) {
                    st.close();
                    return true;
                }
            }
            st.close();
            return false;
        } catch (Exception e) {
            System.err.println("Assurance User does not exist -> Exception");
            System.err.println(e.getMessage());
            return false;
        }
    }

    private String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

}