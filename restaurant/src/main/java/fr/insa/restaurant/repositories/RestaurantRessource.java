package fr.insa.restaurant.repositories;


import fr.insa.restaurant.model.RestaurantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurantRessource")
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
     * Permet de récupérer les informations d'un restaurant à partir de son id.
     *
     * @param idRestaurant du restaurant à récupérer
     * @return toutes les informations du restaurant s'il existe, sinon NULL
     */
    @GetMapping(params = {"idRestaurant"})
    public RestaurantModel getRestaurantById(@RequestParam(name = "idRestaurant") int idRestaurant) {
        return restaurantRepository.getRestaurantModelByIdRestaurant(idRestaurant);
    }

    /**
     * Permet de récupérer tous les restaurants issus d'une ville.
     * Récupère tous les restaurants, puis compare leur ville avec le nom de la ville récupéré dans la variable city.
     * Si la ville d'un restaurant n'est pas compatible à au moins 40% de la variable city, celui ci est retiré de la liste.
     *
     * @param city une ville à récupérer
     * @return Une liste de restaurant
     */
    @GetMapping(params = {"city"})
    public List<RestaurantModel> getRestaurantByCity(@RequestParam(name = "city") String city) {
        List<RestaurantModel> restaurantModelList = restaurantRepository.findAll();
        restaurantModelList.removeIf(restaurantModel -> StringSimilarity.similarity(city, restaurantModel.getCity()) < 0.40);
        return restaurantModelList;
    }

    /**
     * Permet de récupérer tous les restaurants issus en fonction de leur nom.
     * Récupère tous les restaurants, puis compare leur nom avec le nom récupéré dans la variable name.
     * Si le nom d'un restaurant n'est pas compatible à au moins 40% de la variable name, celui ci est retiré de la liste.
     *
     * @param name le nom d'un restaurant à récupérer
     * @return Une liste de restaurant
     */
    @GetMapping(params = {"name"})
    public List<RestaurantModel> getRestaurantByName(@RequestParam(name = "name") String name) {
        List<RestaurantModel> restaurantModelList = restaurantRepository.findAll();
        restaurantModelList.removeIf(restaurantModel -> StringSimilarity.similarity(name, restaurantModel.getName()) < 0.40);
        return restaurantModelList;
    }
}


