package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

/**
 * Created by dlocke on 1/30/17.
 */

@Controller
public class RvThereYetController {

    @Autowired
    UserRepository users;

    //path for HOME
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home (HttpSession session, Model model, String author) throws Exception {

        String loginName = (String) session.getAttribute("loginName");

        if (loginName != null) {
            User user = users.findFirstByName(loginName);
            model.addAttribute("user", user);
        }

//        List<Twitter> twitterList;
//
//        if (author != null) {
//            twitterList = (List<Twitter>) tweets.findByAuthor(author);
//        } else{
//            twitterList = (List<Twitter>) tweets.findAll();
//        }
//        model.addAttribute("tweets", twitterList);
        return "home";
    }//end home()

    //path for login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login (HttpSession session, String loginName, String loginPassword) throws Exception {
        User user = users.findFirstByName(loginName);
        if(user == null){
            user = new User(loginName, PasswordStorage.createHash(loginPassword));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(loginPassword, user.password)){
            throw new Exception("Incorrect Password");
        }
        session.setAttribute("loginName", loginName);
        return "redirect:/";
    }//end login()

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
