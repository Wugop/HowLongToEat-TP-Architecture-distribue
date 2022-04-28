package fr.insa.repositories;

import fr.insa.exception.ExecutionErrorException;
import fr.insa.note.NoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/v1")
public class NoteRessource extends CommonRessource {

    @Autowired
    public NoteRepository noteRepository;

    @Autowired
    private RestTemplate restTemplate;

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


    /**
     * Permet de récupérer toutes les notes d'un restaurant en renseignant l'id du restaurant
     *
     * @param idResto est l'id du restaurant à récupérer
     * @return la liste des notes si le restaurant existe, NULL sinon
     */
    @GetMapping(params = {"idRestoN"})
    public List<NoteModel> getNotebyResto(@RequestParam(name = "idRestoN") int idResto) throws ExecutionErrorException {
        try {
            restTemplate.getForObject("http://restaurant-client/restaurant/api/v1?idRestaurant=" + idResto, Object.class);
            List<NoteModel> list = noteRepository.getNoteModelByIdRestoN(idResto);
            return getNoteModels(list);
        } catch (Exception e) {
            System.err.println("ERR : " + e.getMessage());
            throw new ExecutionErrorException("Error getting notes, this restaurant id is non-existent.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Permet de récupérer toutes les notes d'un utilisateur en renseignant l'id de l'utilisateur
     *
     * @param idUser est l'id de l'utilisateur à récupérer
     * @return la liste des notes que l'utilisateur à poster s'il existe, NULL sinon
     */
    @GetMapping(params = {"idUserN"})
    public List<NoteModel> getNotebyUser(@RequestParam(name = "idUserN") int idUser) throws ExecutionErrorException {
        try {
            restTemplate.getForObject("http://user-client/user/api/v1/userRessources?idUser=" + idUser, Object.class);
            List<NoteModel> list = noteRepository.getNoteModelByIdUserN(idUser);
            return getNoteModels(list);
        } catch (Exception e) {
            System.err.println("ERR : " + e.getMessage());
            throw new ExecutionErrorException("Error getting notes, this user id is non-existent.", HttpStatus.BAD_REQUEST);
        }
    }

    private List<NoteModel> getNoteModels(List<NoteModel> list) {
        List<NoteModel> returnList = new ArrayList<>();
        for (NoteModel note : list)
            returnList.add(NoteModel.builder().note(note.getNote())
                    .idRestoN(note.getIdRestoN())
                    .datePassage(note.getDatePassage())
                    .temps(note.getTemps())
                    .comment(note.getComment())
                    .idUserN(note.getIdUserN())
                    .build());
        return returnList;
    }


    /**
     * Fonctionnant permettant d'obtenir le temps d'attente moyen par jour pour un restaurant donné par tranche de 15 minutes
     * @param idResto ID du restaurant à donner le temps d'attente
     * @return Map<String, Map<Integer, Integer>> avec la première clé qui est le jour de la semaine et la deuxième map, les tranches d'horaires avec les temps d'attentes moyens
     * @throws ExecutionErrorException Si l'ID du restaurant fourni n'est pas valide
     */
    @GetMapping("/waitingTime")
    public Map<String, Map<Integer, Integer>> getWaitingTimeByResto(@RequestParam(name = "idRestoN") int idResto) throws ExecutionErrorException {
        try {
            restTemplate.getForObject("http://restaurant-client/restaurant/api/v1?idRestaurant=" + idResto, Object.class);
            List<NoteModel> listNotes = noteRepository.getNoteModelByIdRestoN(idResto);
            Map<String, Map<Integer, List<NoteModel>>> mapNotesListPerDays = new HashMap<>();
            Map<String, Map<Integer, Integer>> mapWaitingTimePerDays = new HashMap<>();
            String[] days = new DateFormatSymbols().getShortWeekdays();
            List<String> daysList = new ArrayList<>(Arrays.asList(days));
            daysList.remove(0);
            for (String day : daysList) {
                mapNotesListPerDays.put(day, new HashMap<>());
                mapWaitingTimePerDays.put(day, new HashMap<>());
                for (int i = 1100; i < 1500; i += 25)
                    mapNotesListPerDays.get(day).put(i, new ArrayList<>());
            }

            for (NoteModel note : listNotes) {
                String key = new SimpleDateFormat("EE").format(note.getDatePassage());
                switch (note.getDatePassage().getHours()) {
                    case 11:
                        if (note.getDatePassage().getMinutes() < 15)
                            mapNotesListPerDays.get(key).get(1100).add(note);
                        else if (note.getDatePassage().getMinutes() < 30)
                            mapNotesListPerDays.get(key).get(1125).add(note);
                        else if (note.getDatePassage().getMinutes() < 45)
                            mapNotesListPerDays.get(key).get(1150).add(note);
                        else
                            mapNotesListPerDays.get(key).get(1175).add(note);
                        break;
                    case 12:
                        if (note.getDatePassage().getMinutes() < 15)
                            mapNotesListPerDays.get(key).get(1200).add(note);
                        else if (note.getDatePassage().getMinutes() < 30)
                            mapNotesListPerDays.get(key).get(1225).add(note);
                        else if (note.getDatePassage().getMinutes() < 45)
                            mapNotesListPerDays.get(key).get(1250).add(note);
                        else
                            mapNotesListPerDays.get(key).get(1275).add(note);
                        break;
                    case 13:
                        if (note.getDatePassage().getMinutes() < 15)
                            mapNotesListPerDays.get(key).get(1300).add(note);
                        else if (note.getDatePassage().getMinutes() < 30)
                            mapNotesListPerDays.get(key).get(1325).add(note);
                        else if (note.getDatePassage().getMinutes() < 45)
                            mapNotesListPerDays.get(key).get(1350).add(note);
                        else
                            mapNotesListPerDays.get(key).get(1375).add(note);
                        break;
                    case 14:
                        if (note.getDatePassage().getMinutes() < 15)
                            mapNotesListPerDays.get(key).get(1400).add(note);
                        else if (note.getDatePassage().getMinutes() < 30)
                            mapNotesListPerDays.get(key).get(1425).add(note);
                        else if (note.getDatePassage().getMinutes() < 45)
                            mapNotesListPerDays.get(key).get(1450).add(note);
                        else
                            mapNotesListPerDays.get(key).get(1475).add(note);
                        break;
                    default:
                        break;
                }
            }
            for (Map.Entry<String, Map<Integer, List<NoteModel>>> entry : mapNotesListPerDays.entrySet()) {
                for (Map.Entry<Integer, List<NoteModel>> entry1 : entry.getValue().entrySet()) {
                    int mean = 0;
                    for (NoteModel note : entry1.getValue())
                        mean += note.getTemps();
                    mean = (entry1.getValue().size() == 0) ? 0 : mean / entry1.getValue().size();
                    mapWaitingTimePerDays.get(entry.getKey()).put(entry1.getKey(), mean);
                }
            }
            return mapWaitingTimePerDays;
        } catch (Exception e) {
            System.err.println("ERR : " + e.getMessage());
            throw new ExecutionErrorException("Error getting notes, this restaurant id is non-existent.", HttpStatus.BAD_REQUEST);
        }

    }
}

