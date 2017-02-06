package com.theironyard.controllers;

import jodd.json.JsonParser;

import com.theironyard.entities.Product;
import com.theironyard.entities.User;
import com.theironyard.entities.WalmartItem;
import com.theironyard.entities.WalmartList;
import com.theironyard.services.ProductRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dlocke on 1/30/17.
 */

@Controller
public class RvThereYetController {

    @Value("${WalmartKey}")
    private String WalmartKey;

    @Autowired
    UserRepository users;

    @Autowired
    ProductRepository items;

    //path to Walmart API
    @RequestMapping(path = "/walmart", method = RequestMethod.GET)
    public String walmartList() throws IOException {

        URL walmartAPI = new URL("http://api.walmartlabs.com/v1/search?query=paper+towel&format=json&apiKey=" + WalmartKey);
        URLConnection uc = walmartAPI.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));


        StringBuilder sb = new StringBuilder(); //new String derived from JSON data

        while (in.ready()){
            sb.append(in.readLine());
        }

        System.out.println(sb);

        //parse sb into a NEW array list of objects
        //send the list to the template
        //use mustache to display

        JsonParser parser = new JsonParser();
        WalmartList listing = parser.parse(sb.toString(), WalmartList.class);

        System.out.println(listing.getQuery());

        return "index";
    }


    //path for HOME
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String item) throws Exception {

        String userName = (String) session.getAttribute("userName");

        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("userName", user);
        }
        return "index";
    }//end home()

    //path for login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String userPassword) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(userPassword));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(userPassword, user.password)) {
            throw new Exception("Incorrect Password");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }//end login()

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostConstruct
    public void init() throws PasswordStorage.CannotPerformOperationException {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Danny";
            user.password = PasswordStorage.createHash("userPassword");
            users.save(user);
        }
    }//end init()

}
