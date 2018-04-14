import java.sql.*;

/**
 * This class connects to the database and contains all functions used for the
 * database communication
*/
public class database {
    public database(String dbUrl, String user, String password) {
        try {
            // create our mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(dbUrl, user, password);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    /**
     * This method checks if the given username exists in the database
     * if yes -> true will be returned, if no -> false
     * @param username
     * @return true/ false
     */
    public boolean checkIfUserExists(String username){
        //TODO: Statement ist noch falsch -> hier kommt das richtige sobald die DB steht
        String query = "SELECT * FROM users WHERE username='username'";
        //fire the statement
        Statement st = conn.createStatement();
        //fetch the result
        ResultSet rs = st.executeQuery(query);

        //TODO: einfach mal kopiert -> hier sollte ja eigentlich nur überprüft werden
        //ob etwas zurückkommt -> wenn ja -> return true
        while (rs.next()) {
            int id = rs.getInt("id");
        }
        st.close();
        return false;
    }
}