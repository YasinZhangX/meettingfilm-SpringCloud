package com.yasin.meetingfilm.backend.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yasin Zhang
 */
@Controller
@RequestMapping("/doc")
public class DocController {

    @GetMapping("/api")
    public String getApi(){
        return "apiDoc";
    }

    @GetMapping("/nozuul")
    public String getNoZuul(){
        return "MeetingFilm_backend_nozuul";
    }

}
