package com.theironyard.controllers;

import com.theironyard.entities.Product;
import com.theironyard.entities.User;
import com.theironyard.services.ProductRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by dlocke on 1/30/17.
 */

@Controller
public class RvThereYetController {

    @Autowired
    UserRepository users;

    @Autowired
    ProductRepository items;

    //path for HOME
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home (HttpSession session, Model model, String item) throws Exception {

        String userName = (String) session.getAttribute("userName");

        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("userName", user);
        }

        List<Product> productList;

        if (item != null) {
            productList = (List<Product>) items.findByUser(userName);
        } else{
            productList = (List<Product>) items.findAll();
        }
        model.addAttribute("items", productList);
        return "index";
    }//end home()

    //path for login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login (HttpSession session, String userName, String userPassword) throws Exception {
        User user = users.findFirstByName(userName);
        if(user == null){
            user = new User(userName, PasswordStorage.createHash(userPassword));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(userPassword, user.password)){
            throw new Exception("Incorrect Password");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }//end login()

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @PostConstruct
    public void init() throws PasswordStorage.CannotPerformOperationException {
        if(users.count() == 0){
            User user =  new User();
            user.name = "Danny";
            user.password = PasswordStorage.createHash("userPassword");
            users.save(user);
        }
    }//end init()

}
