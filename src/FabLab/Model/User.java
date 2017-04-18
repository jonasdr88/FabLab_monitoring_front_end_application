package FabLab.Model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.*;

/**
 * Created by jonas on 11/04/2017.
 */
public class User {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty rolNumber;
    private final StringProperty userID;

    public User()
    {
        this(null,null, null, null);
    }

    public User(String firstName, String lastName, String rolNumber, String userID)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.rolNumber = new SimpleStringProperty(rolNumber);
        this.userID = new SimpleStringProperty(userID);
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public void setFirstName(String firstName)
    {
        this.firstName.set(firstName);
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    public String getRolNumber()
    {
        return rolNumber.get();
    }

    public void setRolNumber(String rolNumber)
    {
        this.rolNumber.set(rolNumber);
    }

    public String getUserID()
    {
        return userID.get();
    }

    public void setUserID(String userID)
    {
        this.userID.set(userID);
    }
}
