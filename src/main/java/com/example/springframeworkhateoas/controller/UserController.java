package com.example.springframeworkhateoas.controller;

import com.example.springframeworkhateoas.model.User;
import com.example.springframeworkhateoas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/all", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity <PageImpl <User>> users(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                  @RequestParam(value = "size", defaultValue = "0", required = false) int size) {
        Pageable pageable = null;
        if (size > 0) {
            pageable = PageRequest.of(page, size);
        }
        Page <User> all = userRepository.findAll(pageable);
        List <User> content = all.getContent();
        List <User> users = new ArrayList <>();
        for (User user : content) {
            Link link = ControllerLinkBuilder.linkTo(UserController.class).slash("http://192.168.1.56/users/" + user.getUserId()).withRel("user");
            user.add(link);
            users.add(user);
        }
        return new ResponseEntity <>(new PageImpl <>(users, pageable, users.size()), HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity <User> getUser(@NotNull @PathVariable("userId") Long userId) {
        Optional <User> byId = userRepository.findById(userId);
        return byId.map(user -> new ResponseEntity <>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity <>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity <Void> creation(@RequestBody User user) {
        if (user.getName() == null || user.getName().equals("")) {
            return new ResponseEntity <>(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity <>(HttpStatus.OK);
    }
}
