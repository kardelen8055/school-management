package com.project.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)//
public class ResponseMessage <E>{

    //<E>  BU JENERİK ANLAMLI

    private  E object;
    private  String message;

    private HttpStatus httpStatus;
    //responseEnttiyle alakalı burası
}
