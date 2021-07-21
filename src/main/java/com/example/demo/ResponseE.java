package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseE<T> {

    private T data;
    private Integer total;
    private Integer page;
    private Integer limit;
    private Integer offset;

}
