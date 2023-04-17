package com.bank.transfer.controller;


import com.bank.transfer.model.Stage;
import com.bank.transfer.model.User;
import com.bank.transfer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
  ResponseEntity response;
  @Autowired
  UserRepo userRepo;
  @PostMapping("/addCustomer")
  public ResponseEntity<?> addCustomer(@RequestBody User user){
    userRepo.save(user);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }
  @GetMapping("/getCustomer")
  public ResponseEntity<?>getCustomer(@RequestBody User user){
    List<User>userList=userRepo.findAll();
    if(userList!=null) {
      response = new ResponseEntity<List<User>>(userList,HttpStatus.OK);
    }
    else {
      response=new ResponseEntity<String>("failure",HttpStatus.BAD_REQUEST);
    }
    return response;
  }
//  @PostMapping("/bank/user/add")
//  public User addCompany(@RequestBody User user)
//  {
//    return userRepo.save(user);
//  }

}
