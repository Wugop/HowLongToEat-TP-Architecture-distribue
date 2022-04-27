package fr.insa.restaurant.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idRestaurant;
    private String description;
}
