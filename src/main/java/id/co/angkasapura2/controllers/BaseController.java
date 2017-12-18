package id.co.angkasapura2.controllers;

import id.co.angkasapura2.enums.RoleEnum;
import id.co.angkasapura2.utils.Response;
import id.co.angkasapura2.utils.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected final ResponseEntity FORBIDDEN =
            new ResponseEntity(HttpStatus.FORBIDDEN);

    public ResponseEntity getHttpResponse(Response response){
        if (response.getData() == null){
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(response, HttpStatus.OK);
        }
    }


    public boolean authorize(RoleEnum roleEnum, String token){
        try {
            Integer[] roles = TokenUtil.getRoles(token);
            for(Integer roleId : roles){
                if (roleId == roleEnum.getValue())
                    return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean authorize(RoleEnum[] roleEnums, String token){
        try {
            Integer[] roles = TokenUtil.getRoles(token);
            for (RoleEnum roleEnum : roleEnums){
                for(Integer roleId : roles){
                    if (roleId == roleEnum.getValue())
                        return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
