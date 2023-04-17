package com.bank.transfer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Biller {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int bid;
  String full_name;
  String gender;
  String phone;
  String email;
  String password;
  int balance;
}
