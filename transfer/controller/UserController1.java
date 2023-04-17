package com.bank.transfer.controller;

import com.bank.transfer.model.Biller;
import com.bank.transfer.model.Stage;
import com.bank.transfer.model.Transactions;
import com.bank.transfer.model.User;
import com.bank.transfer.repository.BillerRepo;
import com.bank.transfer.repository.StageRepo;
import com.bank.transfer.repository.TransactionsRepo;
import com.bank.transfer.repository.UserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class UserController1 {

  @Autowired
  UserRepo userRepo;

  @Autowired
  BillerRepo billerRepo;

  @Autowired
  StageRepo stageRepo;

  @Autowired
  TransactionsRepo transactionsRepo;



  @GetMapping("/get/inbox/{bid}")
  public ResponseEntity<List<Stage>> getBillerInbox(@PathVariable Integer bid) {
    List<Stage> stage = stageRepo.findAll();
    List<Stage> res = new ArrayList<>();
    for (Stage s: stage){
      if(s.getBid()==bid){
        res.add(s);
      }
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PostMapping("/approve/{sid}")
  public ResponseEntity<Boolean> approveTranscation(@PathVariable Integer sid){
    Optional<Stage> s = stageRepo.findById(sid);
    if(!s.isPresent()){
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
    stageRepo.deleteById(sid);
    Optional<Biller> biller = billerRepo.findById(s.get().getBid());
    Optional<User> user = userRepo.findById(s.get().getUid());
    if(!biller.isPresent() || !user.isPresent()){
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
    User tempUser = user.get();
    Biller tempBiller = biller.get();
    tempUser.setBalance(tempUser.getBalance()-s.get().getAmount());
    tempBiller.setBalance(tempBiller.getBalance()+s.get().getAmount());
    userRepo.save(tempUser);
    billerRepo.save(tempBiller);
    Transactions transaction = new Transactions();
    transaction.setBid(s.get().getBid());
    transaction.setUid(s.get().getUid());
    transaction.setAmount(s.get().getAmount());
    transaction.setType(s.get().getType());
    transactionsRepo.save(transaction);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @PostMapping("/do/transaction")
  public ResponseEntity<Boolean> doTransaction(@RequestBody Stage stage) {
    if(stageRepo.findById(stage.getSid()).isPresent()){
      return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
     stageRepo.save(stage);
     return new ResponseEntity<>(true, HttpStatus.OK);
  }

}
