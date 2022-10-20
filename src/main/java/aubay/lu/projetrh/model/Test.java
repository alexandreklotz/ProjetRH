package aubay.lu.projetrh.model;

import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Test extends Qcm {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(CustomJsonView.TestView.class)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView(CustomJsonView.TestView.class)
    @Column(nullable = false)
    private double score;


    // CONSTRUCTOR //
    public Test(){}


    // RELATIONS //
    @JsonView(CustomJsonView.TestView.class)
    @ManyToOne
    @JoinColumn(name="utilisateur_id")
    private Utilisateur utilisateur;


    // GETTERS AND SETTERS //
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
