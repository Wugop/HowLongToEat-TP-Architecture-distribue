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
@RequestMapping("api/v1/noteRessources")
public class NoteRessource extends CommonRessource {

    @Autowired
    public NoteRepository noteRepository;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * Permet de récupérer toutes les notes d'un restaurant en renseignant l'id du restaurant
     *
     * @param idResto est l'id du restaurant à récupérer
     * @return la liste des notes si le restaurant existe, NULL sinon
     */
    @GetMapping(params = {"idRestoN"})
    public List<NoteModel> getNotebyResto(@RequestParam(name = "idRestoN") int idResto) throws ExecutionErrorException {
        try {
            if (restTemplate.getForObject("http://restaurant-client/restaurant/api/v1/restaurantRessources?idRestaurant=" + idResto, Object.class) == null)
                throw new ExecutionErrorException("Error getting notes, this restaurant id is non-existent.", HttpStatus.BAD_REQUEST);
            List<NoteModel> list = noteRepository.getNoteModelByIdRestoN(idResto);
            return getNoteModels(list);
        } catch (Exception e) {
            System.err.println("ERR : " + e.getMessage());
            throw new ExecutionErrorException("No instances available for restaurant-client", HttpStatus.SERVICE_UNAVAILABLE);
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
            if (restTemplate.getForObject("http://user-client/user/api/v1/userRessources?idUser=" + idUser, Object.class) == null)
                throw new ExecutionErrorException("Error getting notes, this user id is non-existent.", HttpStatus.BAD_REQUEST);
            List<NoteModel> list = noteRepository.getNoteModelByIdUserN(idUser);
            return getNoteModels(list);
        } catch (Exception e) {
            System.err.println("ERR : " + e.getMessage());
            throw new ExecutionErrorException("No instances available for user-client", HttpStatus.SERVICE_UNAVAILABLE);
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
     *
     * @param idResto ID du restaurant à donner le temps d'attente
     * @return Map<String, Map < Integer, Integer>> avec la première clé qui est le jour de la semaine et la deuxième map, les tranches d'horaires avec les temps d'attentes moyens
     * @throws ExecutionErrorException Si l'ID du restaurant fourni n'est pas valide
     */
    @GetMapping("/waitingTime")
    public Map<String, Map<Integer, Integer>> getWaitingTimeByResto(@RequestParam(name = "idRestoN") int idResto) throws ExecutionErrorException {
        try {
            if (restTemplate.getForObject("http://restaurant-client/restaurant/api/v1/restaurantRessources?idRestaurant=" + idResto, Object.class) == null)
                throw new ExecutionErrorException("Error getting notes, this restaurant id is non-existent.", HttpStatus.BAD_REQUEST);
            List<NoteModel> listNotes = noteRepository.getNoteModelByIdRestoN(idResto);
            Map<String, Map<Integer, List<NoteModel>>> mapNotesListPerDays = new HashMap<>();
            Map<String, Map<Integer, Integer>> mapWaitingTimePerDays = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                mapNotesListPerDays.put(String.valueOf(i), new HashMap<>());
                mapWaitingTimePerDays.put(String.valueOf(i), new HashMap<>());
                for (int j = 1100; j < 1500; j += 25)
                    mapNotesListPerDays.get(String.valueOf(i)).put(j, new ArrayList<>());
            }

            for (NoteModel note : listNotes) {
                String key = String.valueOf(note.getDatePassage().getDay());
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
            throw new ExecutionErrorException("No instance available for restaurant-client", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("waitingTimeNow")
    public int getWaitingTimeNowByResto(@RequestParam(name = "idRestoN") int idResto) throws ExecutionErrorException {
        Map<String, Map<Integer, Integer>> mapWaitingTime = getWaitingTimeByResto(idResto);
        Calendar c = Calendar.getInstance();
        float temp = (float)c.get(Calendar.MINUTE) / 100;
        int minute = 0;
        if(temp >= 0.25 && temp < 0.5)
            minute = 25;
        else if(temp >= 0.5 && temp < 0.75)
            minute = 50;
        else if(temp >=0.75)
            minute = 75;
        int time = c.get(Calendar.HOUR_OF_DAY) * 100 + minute;
        return c.get(Calendar.HOUR_OF_DAY) <11 || c.get(Calendar.HOUR_OF_DAY) >14 ? -1 : mapWaitingTime.get(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))).get(time);
    }


}

