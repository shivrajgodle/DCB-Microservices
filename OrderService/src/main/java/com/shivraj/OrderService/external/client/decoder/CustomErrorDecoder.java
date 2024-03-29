package com.shivraj.OrderService.external.client.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivraj.OrderService.exception.CustomException;
import com.shivraj.OrderService.external.client.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {

        ObjectMapper objectMapper = new ObjectMapper();

        log.info(" ::{}{}",response.request().url());
        log.info(" ::{}{}",response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getErrorMessage(), response.status(),errorResponse.getErrorCode());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error",500,"INTERNAL_SERVER_ERROR");
        }
    }
}
