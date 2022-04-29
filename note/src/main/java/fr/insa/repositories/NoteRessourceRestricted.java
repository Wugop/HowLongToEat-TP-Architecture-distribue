package fr.insa.repositories;

import fr.insa.note.NoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/noteRessourcesRestricted")
public class NoteRessourceRestricted {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Permet la création d'une nouvelle note sur un restaurant
     *
     * @param noteModel les informations de la note à enregistrer
     * @param id        ID de l'utilisateur qui a créé la note
     * @return ResponseEntity avec un status "Accepted" si la note s'est bien créée
     */
    @PostMapping
    public ResponseEntity<String> addNote(@RequestBody NoteModel noteModel, @RequestHeader(name = "x-auth-user-id") String id) {
        this.noteRepository.save(NoteModel.builder()
                .note(noteModel.getNote())
                .temps(noteModel.getTemps())
                .idUserN(Integer.parseInt(id))
                .idRestoN(noteModel.getIdRestoN())
                .datePassage(noteModel.getDatePassage())
                .comment(noteModel.getComment())
                .build());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Note successfully created");
    }
}
