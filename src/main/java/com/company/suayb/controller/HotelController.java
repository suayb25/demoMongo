package com.company.suayb.controller;

import com.company.suayb.model.Hotel;
import com.company.suayb.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class HotelController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @RequestMapping("/makeReservation")
    public String makeReservation(@RequestParam("bday") String bday,Model model) {
         model.addAttribute("hotel",hotelService.findAll());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
      /*  List<Hotel> myList = new ArrayList<>();
        String b=formatter.format(bday);*/
        try {
            Date date = formatter.parse(bday);
          //  myList= Lists.newArrayList(hotelService.findAvailable(date));
            /*for (int i=0;i<myList.size();i++){
                String a =formatter.format(myList.get(i).getDate());

                int mm_list=0;
                int mm_request=Integer.parseInt(b.substring(5,6));
                int dd_list=0;
                int dd_request=Integer.parseInt(b.substring(8,9));

                mm_list=Integer.parseInt(a.substring(5,6));

            }*/
            System.out.println(date);
            System.out.println(formatter.format(date));
            model.addAttribute("available",hotelService.findAvailable(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }



        return "reservation";
    }
}
