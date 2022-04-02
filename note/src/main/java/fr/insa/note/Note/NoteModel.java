package fr.insa.note.Note;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idNote;
    private int temps;
    private int idUserN;
    private int idRestoN;
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private String datePassage;
    private String comment;
}
