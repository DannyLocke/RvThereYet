package com.theironyard.controllers;

import com.theironyard.entities.*;
import jodd.json.JsonParser;
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

    @Value("${EbayKey}")
    private String EbayKey;

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

    //path to Ebay API
    @RequestMapping(path = "/ebay", method = RequestMethod.GET)
    public String ebayList() throws IOException {

        URL ebayAPI = new URL("https://svcs.ebay.com/services/search/FindingService/v1?SECURITY-APPNAME=" + EbayKey + "&OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&RESPONSE-DATA-FORMAT=JSON&callback=_cb_findItemsByKeywords&REST-PAYLOAD&keywords=iPhone&paginationInput.entriesPerPage=6&GLOBAL-ID=EBAY-US&siteid=0");
        URLConnection uc = ebayAPI.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));


        StringBuilder sb = new StringBuilder(); //new String derived from JSON data

        while (in.ready()){

            //System.out.println(in.readLine());
            sb.append(in.readLine());
        }

        System.out.println(sb);

        //parse sb into a NEW array list of objects
        //send the list to the template
        //use mustache to display

//        JsonParser parser = new JsonParser();
//        EbayList listing = parser.parse(sb.toString(), EbayList.class);
//
//        System.out.println(listing.getFindItemsByKeywords());

        return "index";
    }


    //path for HOME (returning user)
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String name) throws Exception {

        name = (String) session.getAttribute("name");

        if (name != null) {
            User user = users.findFirstByName(name);
            model.addAttribute("name", user);
        }
        return "index";
    }//end home()

    //path for registration page
    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public String registration(HttpSession session, String name, String password) throws Exception {
        User user = new User(name, PasswordStorage.createHash(password));
        users.save(user);
        session.setAttribute("name", name);
        //registration page should look very similar to "/" page except no link for new user

        return "redirect:/search";
    }

    //path for login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            return "redirect:/"; //NEEDS: username error message
        } else if (!PasswordStorage.verifyPassword(password, user.password)) {
            return "redirect:/"; //NEEDS: password error message
        }
        session.setAttribute("name", name);
        return "redirect:/search"; //returning and new user both go to search page
    }//end login()



    //path for search page
    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public String search(HttpSession session, String name, String item) {
        //search page has field for user entry
        //search button
        //user types keyword/product and clicks "search"
        //search button takes user to product page
        //links near bottom to go to history page

        return "redirect:/product";
    }

    //path for product page
    @RequestMapping(path = "/product", method = RequestMethod.GET)
    public String product(HttpSession session, String name, String item) {
        //product page has a list of items returned from the APIs (based on user's keyword)
        //user can click on an item to go to Walmart.com or Ebay.com
        //links near bottom to return to search page and/or go to history page

        return "redirect:/";
    }

    //path for history page
    @RequestMapping(path = "/history", method = RequestMethod.GET)
    public String history(HttpSession session, String name, String item) {
        //history page has a list of items returned from user's previous searches
        //user can click on an item to go to Walmart.com or Ebay.com
        //links near bottom to return to search page and/or go to product page
        //history page will "LOOK" very similar to product page
        //difference is that history is based on user and product is based on keyword

        return "redirect:/";
    }


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
