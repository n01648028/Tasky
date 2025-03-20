package com.humber.Tasky.repositories;

import com.humber.Tasky.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    //custom method to find dish records by category and price
    public List<Dish> findByCategoryAndPrice(String category, double price);
}
