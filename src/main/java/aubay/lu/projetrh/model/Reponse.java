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
public class Reponse {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView({CustomJsonView.ReponseView.class, CustomJsonView.QuestionView.class, CustomJsonView.TestView.class})
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView({CustomJsonView.ReponseView.class, CustomJsonView.QuestionView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private String texte;

    @JsonView({CustomJsonView.ReponseView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private boolean correctAnswer;

    @JsonView({CustomJsonView.ReponseView.class, CustomJsonView.TestView.class})
    @Column(nullable = false)
    private boolean isSelectedAnswer;


    // CONSTRUCTOR //
    public Reponse(){}


    // RELATIONS //
    @JsonView(CustomJsonView.ReponseView.class)
    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;


    // GETTERS AND SETTERS //
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

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isSelectedAnswer() {
        return isSelectedAnswer;
    }

    public void setSelectedAnswer(boolean selectedAnswer) {
        isSelectedAnswer = selectedAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
