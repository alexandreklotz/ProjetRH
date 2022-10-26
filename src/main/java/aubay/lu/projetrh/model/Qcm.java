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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Qcm {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(CustomJsonView.QcmView.class)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView({CustomJsonView.QcmView.class, CustomJsonView.QuestionView.class})
    @Column(nullable = false)
    private String titre;


    // CONSTRUCTOR //
    public Qcm(){}


    // RELATIONS //
    @JsonView(CustomJsonView.QcmView.class)
    @OneToMany(mappedBy = "qcm")
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

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
