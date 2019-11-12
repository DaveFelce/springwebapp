package com.davidfelce.controller;

import com.davidfelce.dao.CarDAO;
import com.davidfelce.domain.Car;
import com.davidfelce.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

//    This will replace carservice, to use the DB
    @Autowired
    private CarDAO carDAO;

    @RequestMapping("/list")
    public void carList(Model model) {
//        List<Car> carList = carService.findAll();
        List<Car> carList = carDAO.findAll();
        model.addAttribute("carList", carList);
    }

    @RequestMapping("/first")
    public void daoCarList() {
        Car car = carDAO.findById(1);
        System.out.println(car.getName());
        System.out.println(car.getId());
        System.out.println(car.getPrice());
    }

    @RequestMapping("/{field}")
    public String showCarField(@PathVariable("field") String field, Model model) {
        List<Car> carList = carService.findAll();
        Car car = carList.get(0);
        model.addAttribute("greeting", carDAO.getCarGreeting());
        model.addAttribute("price", car.getPrice());
        model.addAttribute("field", field);

        return "/car/field";
    }

    @RequestMapping("/add")
    public void carAdd() {
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String carAddSubmit(@ModelAttribute("car") @Valid Car car, BindingResult result) {
        if(result.hasErrors()) {
            // show the form again, with the errors
            return "car/add";
        }

        // validation was successful
//        carService.add(car);
        carDAO.add(car);
        return "redirect:/car/list";
    }
}
