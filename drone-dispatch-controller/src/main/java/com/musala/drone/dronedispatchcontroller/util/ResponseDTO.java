package com.musala.drone.dronedispatchcontroller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.musala.drone.dronedispatchcontroller.util.constants.ConstantUtil.SUCCESS;

@AllArgsConstructor
@Data
public class ResponseDTO {
    private String status;
    private long timestamps;
    private Object response;
    public ResponseDTO(){
        this.status=SUCCESS;
        this.timestamps =System.currentTimeMillis();
    }


}
