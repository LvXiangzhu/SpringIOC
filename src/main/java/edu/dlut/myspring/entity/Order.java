package edu.dlut.myspring.entity;

import edu.dlut.myspring.annotation.Component;
import edu.dlut.myspring.annotation.Value;
import lombok.Data;

@Data
@Component
public class Order {
    @Value("1")
    int id;

    @Value("1")
    int count;

    @Value("zhangsan")
    String name;

    @Value("35.5")
    double price;
}
