package aubay.lu.projetrh.model;

import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column(nullable = false)
    private String firstName;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column(nullable = false)
    private String lastName;

    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private String userLogin;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column(nullable = false)
    private String userPassword;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column(nullable = false)
    private String mailAddress;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column
    private double globalScore;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @Column
    private String userDescription;

    //TODO: Implémenter date de création/inscription


    // CONSTRUCTOR //
    public Utilisateur(){}


    // RELATIONS //
    @JsonView(CustomJsonView.UtilisateurView.class)
    @OneToMany(mappedBy = "utilisateur")
    private Set<Test> tests;

    @JsonView(CustomJsonView.UtilisateurView.class)
    @ManyToOne
    @JoinColumn(name = "roles_id", nullable = false)
    private Roles role;


    // GETTERS AND SETTERS//
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public double getGlobalScore() {
        return globalScore;
    }

    public void setGlobalScore(double globalScore) {
        this.globalScore = globalScore;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
