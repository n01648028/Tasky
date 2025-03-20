package com.humber.Tasky.controllers;

import com.humber.Tasky.models.Dish;
import com.humber.Tasky.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Tasky")
public class DishController {
    //dependency injection
    @Autowired
    private DishService dishService;
    @Value("${restaurant.name}")
    private String restaurantName;
    @Value("${page.size}")
    private int pageSize;
    //endpoint for home page
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("rName", restaurantName);
        return "home";
    }
    //endpoint for menu page (list of dishes)
    @GetMapping("/menu/{pageNo}")
    public String menu(Model model,
                       @RequestParam(required = false) String message,
                       @RequestParam(required = false) String searchedCategory,
                       @RequestParam(required = false) Double searchedPrice,
                       @PathVariable int pageNo,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDirection
                       ) {
        //filter condition
        if(searchedCategory != null && searchedPrice != null) {
            List<Dish> filteredDishes = dishService.getDishByCategoryAndPrice(searchedCategory, searchedPrice);
            model.addAttribute("dishes", filteredDishes);
            model.addAttribute("message", filteredDishes.isEmpty() ? "Filter failed!" : "Filter successful!");
            return "menu";
        }
        //general condition = return paginated records
        Page<Dish> page = dishService.getPaginatedDishes(pageNo, pageSize, sortField, sortDirection);
        model.addAttribute("dishes", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        //sorting info
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        //add information to display in menu like the dishes and the message if there is one
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("message", message);
        return "menu";
    }
}
