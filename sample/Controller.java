package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;

@SuppressWarnings("unchecked")
public class Controller {
    private static final String userName = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost/billcalc";
    private static Connection conn = null;
    Statement stmt;

    {
        try {
            stmt = createDBConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection createDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(Controller.url, Controller.userName, Controller.password);
        } catch (SQLException e) {
            System.err.println("Some problems with database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Some problems with driver");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Other problems");
            e.printStackTrace();
        }
        return conn;
    }

    public static void fillComboBox(String tableName, ComboBox cbname) {
        try {
            Statement stmt = createDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery("Select name from " + tableName + " order by Name");
            String temp;
            ObservableList<String> cbFillNames = FXCollections.observableArrayList();
            while (rs.next()) {
                temp = rs.getString("Name");
                cbFillNames.add(temp);
            }
            cbname.getItems().add("");
            cbname.getItems().addAll(cbFillNames);
            conn.close();
        } catch (SQLException e) {
            System.err.println("Some problems with SQL-query");
            e.printStackTrace();
        }
    }

    public float calcResult() {
        float beveragePrice = 0;
        float appetizerPrice = 0;
        float mainCoursePrice = 0;
        float dessertPrice = 0;
        try {
            String chosenBeverage = (String) View.beverageCB.getValue();
            String chosenAppetizer = (String) View.appetizerCB.getValue();
            String chosenMainCourse = (String) View.mainCourseCB.getValue();
            String chosenDessert = (String) View.dessertCB.getValue();

            ResultSet rs = stmt.executeQuery("Select price from beverage where name = \"" + chosenBeverage + "\"");
            while (rs.next()) {
                beveragePrice = rs.getFloat("Price") * 100;
            }
            rs = stmt.executeQuery("Select price from appetizer where name = \"" + chosenAppetizer + "\"");
            while (rs.next()) {
                appetizerPrice = rs.getFloat("Price") * 100;
            }
            rs = stmt.executeQuery("Select price from main_course where name = \"" + chosenMainCourse + "\"");
            while (rs.next()) {
                mainCoursePrice = rs.getFloat("Price") * 100;
            }
            rs = stmt.executeQuery("Select price from dessert where name = \"" + chosenDessert + "\"");
            while (rs.next()) {
                dessertPrice = rs.getFloat("Price") * 100;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        float result = beveragePrice + appetizerPrice + mainCoursePrice + dessertPrice;
        return result / 100;
    }
}
