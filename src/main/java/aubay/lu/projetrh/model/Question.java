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
public class Question {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView({CustomJsonView.QuestionView.class, CustomJsonView.ReponseView.class, CustomJsonView.TestView.class, CustomJsonView.QcmView.class})
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView({CustomJsonView.QuestionView.class, CustomJsonView.QcmView.class, CustomJsonView.ReponseView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private String texte;

    @JsonView({CustomJsonView.QuestionView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private int tempsReponse;

    @JsonView({CustomJsonView.QuestionView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private Double points;


    // CONSTRUCTOR //
    public Question(){}


    // RELATIONS //
    @JsonView(CustomJsonView.QuestionView.class)
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    private Qcm qcm;

    @JsonView({CustomJsonView.QuestionView.class, CustomJsonView.TestView.class})
    @OneToMany(mappedBy = "question")
    private Set<Reponse> reponses;

    @JsonView(CustomJsonView.QuestionView.class)
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;


    //GETTERS & SETTERS
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public int getTempsReponse() {
        return tempsReponse;
    }

    public void setTempsReponse(int tempsReponse) {
        this.tempsReponse = tempsReponse;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Qcm getQcm() {
        return qcm;
    }

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    public Set<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(Set<Reponse> reponses) {
        this.reponses = reponses;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test tests) {
        this.test = tests;
    }
}
