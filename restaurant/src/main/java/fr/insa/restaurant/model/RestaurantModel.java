package fr.insa.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idRestaurant;
    private String name;
    private String description;
    private String hourly;
    private String phone;
    private String city;
    private String adress;
}
