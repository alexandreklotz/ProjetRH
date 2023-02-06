package aubay.lu.projetrh.model;

import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CustomJsonView.RolesView.class, CustomJsonView.UtilisateurView.class})
    @Column
    private Long id;


    @JsonView({CustomJsonView.RolesView.class, CustomJsonView.UtilisateurView.class})
    @Column(nullable = false)
    private String denomination;


    // CONSTRUCTOR //
    public Roles(){}


    // RELATIONS //
    @JsonView(CustomJsonView.RolesView.class)
    @OneToMany(mappedBy = "role")
    private Set<Utilisateur> utilisateurs;


    // GETTERS AND SETTERS //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
