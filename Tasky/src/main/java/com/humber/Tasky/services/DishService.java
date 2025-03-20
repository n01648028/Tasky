package com.humber.Tasky.services;

import com.humber.Tasky.models.Dish;
import com.humber.Tasky.repositories.DishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    //Constructor Injection
    private final DishRepository dishRepository;
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }
    //get all dishes from the repo layer
    public List<Dish> getAllDishes(){
        //business logic should have been included here
        return dishRepository.findAll();
    }
    //save a dish to the db(calling the save method on the repo)
    //0=failure, 1=success
    public int saveDish(Dish dish){
        //validation
        if(dish.getPrice() > 20){
            return 0;
        }
        // save the dish by calling the repo
        dishRepository.save(dish);
        return 1;
    }
    //delete a dish from the db
    //0=failure, 1=success
    public int deleteDishById(int id){
        //check if dish exists
        if(dishRepository.existsById(id)){
            //delete the dish
            dishRepository.deleteById(id);
            return 1;
        }
        //dish does not exist
        return 0;
    }
    //update a dish from the db
    public void updateDish(Dish dish){
        dishRepository.save(dish);
    }
    //get a dish by id
    public Optional<Dish> getDishById(int id){
        return dishRepository.findById(id);
    }
    //get dish records by category and price
    public List<Dish> getDishByCategoryAndPrice(String category, double price){
        return dishRepository.findByCategoryAndPrice(category, price);
    }
    //pagination method
    public Page<Dish> getPaginatedDishes(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return dishRepository.findAll(pageable);
    }
}
