package com.humber.Tasky.controllers;

import com.humber.Tasky.models.Dish;
import com.humber.Tasky.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Tasky/admin")
public class AdminController {
    //dependency injection
    @Autowired
    private DishService dishService;
    @Value("${restaurant.name}")
    private String restaurantName;
    @Value("${page.size}")
    private int pageSize;
    @GetMapping("/dishes")
    //method to get all dishes from the service
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }
    //endpoint to open the add dish form
    @GetMapping("/add")
    public String addDish(Model model) {
        //add information to display in add like the dishes
        model.addAttribute("dish", new Dish());
        return "admin/add-dish";
    }
    //endpoint to save the dish
    @PostMapping("/save")
    public String saveDish(@ModelAttribute Dish dish, Model model) {
        //0=failure, 1=sucess
        int statusCode = dishService.saveDish(dish);
        //error condition
        if (statusCode == 0) {
            return "admin/add-dish";
        }
        //success condition
        //save the data
        model.addAttribute("dishes", dish);
        model.addAttribute("message", dish.getName() + " added successfully");
        //open the menu page with the updated data
        return "menu";
    }
    //endpoint to delete a dish
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable int id) {
        int deleteStatusCode = dishService.deleteDishById(id);
        if (deleteStatusCode == 1) {
            return "redirect:/restaurant/menu/1?message=Dish deleted successfully.";
        }
        //does delete fail (dish does not exist)
        return "redirect:/restaurant/menu/1?message=Dish does not exist!";
    }
    //endpoint to open the update dish from
    @GetMapping("/update/{id}")
    public String updateDish(@PathVariable int id, Model model) {
        //get the dish to be updated by its id from the database
        Optional<Dish> optionalDishToUpdate = dishService.getDishById(id);
        if (optionalDishToUpdate.isPresent()) {
            dishService.updateDish(optionalDishToUpdate.get());
            model.addAttribute("dish", optionalDishToUpdate.get());
            return "admin/add-dish";
        }
        return "redirect:/restaurant/menu/1?message=Dish to be updated does not exist!";
    }
    //endpoint to update the dish
    @PostMapping("/update")
    public String updateDish(@ModelAttribute Dish dish, Model model) {
        //call the update method in the service layer
        dishService.updateDish(dish);
        return "redirect:/restaurant/menu/1?message=Dish updated successfully!";
    }
}
