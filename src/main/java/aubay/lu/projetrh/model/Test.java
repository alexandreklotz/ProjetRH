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
public class Test {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView({CustomJsonView.TestView.class, CustomJsonView.ReponseView.class})
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView({CustomJsonView.TestView.class, CustomJsonView.UtilisateurView.class})
    @Column(nullable = false)
    private String titre;

    @JsonView(CustomJsonView.TestView.class)
    @Column
    private double score;

    @JsonView(CustomJsonView.TestView.class)
    @Column
    private boolean alreadySubmitted;

    @JsonView(CustomJsonView.TestView.class)
    @Column(nullable = false)
    private UUID qcmId;

    //TODO : Implémenter date de création et date de passage. Peut-être même le recruteur qui a créé le test

    // CONSTRUCTOR //
    public Test(){}


    // RELATIONS //
    @JsonView(CustomJsonView.TestView.class)
    @ManyToOne
    @JoinColumn(name="utilisateur_id")
    private Utilisateur utilisateur;


    @JsonView(CustomJsonView.TestView.class)
    @OneToMany(mappedBy = "test")
    private Set<Question> questions;


    // GETTERS AND SETTERS //
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isAlreadySubmitted() {
        return alreadySubmitted;
    }

    public void setAlreadySubmitted(boolean alreadySubmitted) {
        this.alreadySubmitted = alreadySubmitted;
    }

    public UUID getQcmId() {
        return qcmId;
    }

    public void setQcmId(UUID qcmId) {
        this.qcmId = qcmId;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

}
