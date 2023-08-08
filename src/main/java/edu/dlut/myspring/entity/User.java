package edu.dlut.myspring.entity;

import edu.dlut.myspring.annotation.Autowired;
import edu.dlut.myspring.annotation.Component;
import lombok.Data;

@Data
@Component
public class User {
    String name;
    int age;
    @Autowired
    Order order;
}
