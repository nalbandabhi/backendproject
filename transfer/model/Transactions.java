package com.bank.transfer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transactions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int tid;
  int uid;
  int bid;
  int amount;
  String type;
}
