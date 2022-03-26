package fr.insa.note.Note.repositories;

import fr.insa.note.Note.NoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class NoteRessource {
    @Autowired
    public NoteRepository noteRepository;

    /**
     * Permet la création d'une note à la base de donnée
     * @param noteModel le corps de la note
     * @return les infos de la nouvelle note créée
     */
    @PostMapping
    public NoteModel addNote(@RequestBody NoteModel noteModel) {
        return noteRepository.save(noteModel);
    }


    /**
     * Permet de récupérer toutes les notes d'un restaurant en renseignant l'id du restaurant
     * @param idResto est l'id du restaurant à récupérer
     * @return la liste des notes si le restaurant existe, NULL sinon
     */
    @GetMapping(params = {"idRestoN"})
    public  List<NoteModel>  getNotebyResto (@RequestParam(name = "idRestoN") int idResto) {
        return noteRepository.getNoteModelByIdRestoN(idResto);
    }

    /**
     * Permet de récupérer toutes les notes d'un utilisateur en renseignant l'id de l'utilisateur
     * @param idUser est l'id de l'utilisateur à récupérer
     * @return la liste des notes que l'utilisateur à poster s'il existe, NULL sinon
     */
    @GetMapping(params = {"idUserN"})
    public List<NoteModel> getNotebyUser (@RequestParam(name = "idUserN") int idUser) {
        return noteRepository.getNoteModelByIdUserN(idUser);
    }
}
