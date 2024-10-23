package com.base.entity;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Getter(AccessLevel.NONE)
    private int id;
    private String name;
    private int price;
    private int code;
}
