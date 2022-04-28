package fr.insa.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idNote;
    private float note;
    private int temps;
    private int idUserN;
    private int idRestoN;
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datePassage;
    private String comment;
}
