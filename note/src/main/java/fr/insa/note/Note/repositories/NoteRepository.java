package fr.insa.note.Note.repositories;

import fr.insa.note.Note.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteModel,String> {
    public List<NoteModel> getNoteModelByIdRestoN(int idRestoN);
    public  List<NoteModel>  getNoteModelByIdUserN(int idUser);
}
