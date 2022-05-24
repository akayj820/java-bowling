package qna.domain.qna.answer;

import qna.NotFoundException;
import qna.UnAuthorizedException;
import qna.domain.AbstractEntity;
import qna.domain.ContentType;
import qna.domain.deleteHistory.DeleteHistory;
import qna.domain.qna.question.Question;
import qna.domain.user.User;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Embedded
    private AnswerBody answerBody;

    private boolean deleted = false;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        super(id);

        if (writer == null) {
            throw new UnAuthorizedException();
        }

        if (question == null) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.answerBody = new AnswerBody(question, contents);
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public User getWriter() {
        return writer;
    }

    public void toQuestion(Question question) {
        answerBody.toQuestion(question);
    }

    public DeleteHistory delete() {
        setDelete();

        return createDeleteHistory(ContentType.ANSWER, this.writer);
    }

    public void setDelete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "writer=" + writer +
                ", answerBody=" + answerBody +
                ", deleted=" + deleted +
                '}';
    }
}
