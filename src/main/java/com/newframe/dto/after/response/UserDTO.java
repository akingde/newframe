package com.newframe.dto.after.response;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class UserDTO {

    private Long uid;
    private String userName;

    public UserDTO() {
        this.uid = -1L;
        this.userName = "admin";
    }

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        System.out.println(userDTO.toString());
    }
}
