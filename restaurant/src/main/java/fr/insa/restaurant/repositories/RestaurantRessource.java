package fr.insa.restaurant.repositories;


import fr.insa.restaurant.model.RestaurantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RestaurantRessource {


    @Autowired
    public RestaurantRepository restaurantRepository;


    @GetMapping
    public List<RestaurantModel> getRestaurant() {
        return restaurantRepository.findAll();
    }


    @GetMapping(params = {"idRestaurant"})
    public RestaurantModel getRestaurantById(@RequestParam(name = "idRestaurant") int idRestaurant) {
        return restaurantRepository.getRestaurantModelByIdRestaurant(idRestaurant);
    }

    @PostMapping()
    public RestaurantModel addRestaurantModel(@RequestBody RestaurantModel restaurantModel) {
        return restaurantRepository.save(restaurantModel);
    }




}


