package FabLab.Model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.*;

/**
 * Created by jonas on 11/04/2017.
 */
public class User {

    private int id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty rolNumber;
    private final StringProperty emailAdress;
    private final StringProperty study;
    private final StringProperty nfc_id;

    public User()
    {
        this(-1,null, null, null, null, null, null);
    }

    public User(int id, String firstName, String lastName, String rolNumber, String email, String opleiding, String nfc_id)
    {
        this.id = id;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.rolNumber = new SimpleStringProperty(rolNumber);
        this.emailAdress = new SimpleStringProperty(email);
        this.study = new SimpleStringProperty(opleiding);
        this.nfc_id = new SimpleStringProperty(nfc_id);
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

    public int getId() {
        return this.id;
    }

    public String getEmailAdress()
    {
        return this.emailAdress.get();
    }

    public String getNFCId()
    {
        return this.nfc_id.get();
    }

    public void setEmailAdress(String emailAdress)
    {
        this.emailAdress.set(emailAdress);
    }

    public String getStudy()
    {
        return this.study.get();
    }

    public void setStudy(String study)
    {
        this.study.set(study);
    }

    public void setId(int id) {
        this.id = id;
    }

}
