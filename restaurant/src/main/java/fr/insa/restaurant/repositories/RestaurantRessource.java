package fr.insa.restaurant.repositories;


import fr.insa.restaurant.model.RestaurantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RestaurantRessource {

    @Autowired
    public RestaurantRepository restaurantRepository;


    @GetMapping("restaurant")
    public List<RestaurantModel> getRestaurant() {
        return restaurantRepository.findAll();
    }


}


