package fr.insa.restaurant.repositories;

import fr.insa.restaurant.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RestaurantRepository extends JpaRepository<RestaurantModel, String> {

}