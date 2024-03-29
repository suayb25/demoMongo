package com.company.suayb.controller;

import com.company.suayb.model.ReCaptchaResponse;
import com.company.suayb.model.User;
import com.company.suayb.service.UserService;
import com.company.suayb.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.validation.Valid;

@Controller
public class UserController {
   static final  String admin="admin";
    static final  String adminPassword="admin1234";

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    User loginUser=new User();
    boolean login=false;
    User loginAdmin=new User();

    @RequestMapping("/")
    public String findAllUsers(Model model) {
        logger.info("Getting All Users.");
        model.addAttribute("userList", userService.findAll());
        UserDto user = new UserDto();
        user.setName("");
        user.setSurname("");
        user.setPhoneNumber("");
        user.setUserName("");
        model.addAttribute("user", user);
        model.addAttribute("loginAdmin",loginAdmin);
        return "user";
    }

    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") String id, Model model) {
        userService.delete(id);
        User user2 = new User();
        user2.setName("");
        user2.setSurname("");
        user2.setPhoneNumber("");
        user2.setUserName("");
        model.addAttribute("user", user2);
        model.addAttribute("userList", userService.findAll());
        return "redirect:/";
    }

    @PostMapping(value = "/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto,Model m) {
            boolean y=true;
            List<User> userList=userService.findAll();
            for (int i=0;i<userList.size();i++){
                if(userDto.getUserName().equals(userList.get(i).getUserName())){
                    y=false;
                    break;
                }
            }
            if(y){
                userService.save(userDto);
            }else{
                m.addAttribute("usernameWrong","This username is already exist");
            }
        return "redirect:/";

    }

    @PostMapping(value = "/saveUser")
    public String saveGuestUser(@Valid @ModelAttribute("user") UserDto userDto,Model m) {

        userService.save(userDto);
        m.addAttribute("loginUser",loginUser);
        m.addAttribute("userList", userService.findAll());
    return "index";
    }

    @PostMapping(value = "/edit")
    public String editUser(@Valid @ModelAttribute("user") UserDto userDto) {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user= userService.findByUsername(userDto.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setAge(user.getAge());
        userDto.setMomsSurname(user.getMomsSurname());

        userService.save(userDto);

        return "redirect:/";
    }

    @RequestMapping("/Delete/{id}")
    public void deleteStudent1(@PathVariable(name = "id") String id) {

        userService.delete(id);
    }

    @RequestMapping("/reservation")
    public String showReservation() {

       return "reservation";
    }

    @RequestMapping("/homepage")
    public String showHomePage(Model m) {
        m.addAttribute("wrong","");

        return "homepage";
    }


    @PostMapping("/login")
    public String homepage(@RequestParam("username") String username,@RequestParam("password") String password,Model m) {
        if(userService.findByUsername(username)!=null){
            User user =userService.findByUsername(username);

            if(user.getPassword()!=null){
                if(user.getPassword().equals(getSHA(password))){
                    if(user.getRole()==0){
                        login=true;
                        loginUser=user;
                        ModelMapper modelMapper=new ModelMapper();
                        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                        UserDto userDto= modelMapper.map(user,UserDto.class);
                        m.addAttribute("loginUser",user);
                        m.addAttribute("userList", userService.findAll());
                        userService.save(userDto);//login status true
                        return "index";
                    }else{
                        login=true;
                        loginAdmin=user;
                        return "redirect:/";
                    }
                }else{
                    m.addAttribute("wrong","Your password is wrong");
                    return "homepage" ;
                }
            }else{
                m.addAttribute("wrong","You have to register!");
                return "homepage" ;
            }
        }else{
            boolean k=true;
            List<User> users = userService.findAll();
            for(int i=0;i<users.size();i++){
                if(users.get(i).getRole()==1){
                    k=false;
                }
            }
            if(username.equals(admin)&& password.equals(adminPassword) && k){
                return "redirect:/";
            }else{
                m.addAttribute("wrong","Your login information is wrong");
                return "homepage" ;
            }
        }
    }

    @RequestMapping("/registerPage")
    public String showRegister() {

        return "register";
    }
    @PostMapping("/register")
    public String register(String username,String password,String confirm,String oldsurname,Model m) {

        if(password.equals(confirm)){
            if(userService.findByUsername(username)!=null){
                User userDetail = userService.findByUsername(username);
                if(userDetail.getPassword()==null){
                    if(userDetail.getMomsSurname().equals(oldsurname)){
                        userDetail.setPassword(getSHA(password));
                        System.out.println("\n" + password + " : " + userDetail.getPassword());
                        ModelMapper modelMapper=new ModelMapper();
                        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                        UserDto userDto= modelMapper.map(userDetail,UserDto.class);
                        userService.save(userDto);
                        return "homepage";
                    }else {
                        m.addAttribute("wrong", "Your informations is wrong!");
                        return "register";
                    }

                }else{
                    m.addAttribute("wrong","You have to an account");
                    return "register";
                }
            }else{
                m.addAttribute("wrong","There is no username like you entered");
                return "register";
            }
        }else{
            m.addAttribute("wrong","Your passwords are not matching!");
            return "register";
        }

    }


    public static String getSHA(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);
            return null;
        }
    }

    @PostMapping("/profile")
    public String profile(Model m) {
          if(login){
              m.addAttribute("userprofile",loginUser);
              return "profile";
          }else{
              return "homepage";
          }
    }

    @PostMapping("/logout")
    public String logout(Model m) {
        if (login) {
            login = false;
            loginUser=new User();
        }
        return "homepage";
    }
}
