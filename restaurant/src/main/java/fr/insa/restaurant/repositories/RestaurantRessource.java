package fr.insa.restaurant.repositories;


import Exceptions.ExecutionErrorException;
import fr.insa.restaurant.model.RestaurantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RestaurantRessource {


    @Autowired
    public RestaurantRepository restaurantRepository;

    /**
     * @return Retourne toutes les informations de tous les restaurants
     */
    @GetMapping
    public List<RestaurantModel> getRestaurant() {
        return restaurantRepository.findAll();
    }

    /**
     * Permet de récupérer les informations d'un restaurant a partir de son id
     *
     * @param idRestaurant du restaurant a recuperer
     * @return tous les informations du restaurant s'il existe, sinon NULL
     */
    @GetMapping(params = {"idRestaurant"})
    public RestaurantModel getRestaurantById(@RequestParam(name = "idRestaurant") int idRestaurant) throws ExecutionErrorException {
        RestaurantModel restaurantModel = this.restaurantRepository.getRestaurantModelByIdRestaurant(idRestaurant);
        if (restaurantModel == null)
            throw new ExecutionErrorException("Error getting restaurant, this id is non-existent.", HttpStatus.BAD_REQUEST);
        return RestaurantModel.builder()
                .description(restaurantModel.getDescription())
                .build();
    }

    /**
     * Permet la création d'un restaurant dans la base de donnée
     *
     * @param restaurantModel le corps du nouveau restaurant
     * @return les informations du nouveau restaurant créé
     */
    @PostMapping()
    public ResponseEntity<String> addRestaurant (@RequestBody RestaurantModel restaurantModel) {
        restaurantRepository.save(restaurantModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Restaurant created.");
    }

}


