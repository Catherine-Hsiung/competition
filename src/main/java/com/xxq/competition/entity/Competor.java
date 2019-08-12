package com.xxq.competition.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class Competor {
    private Integer id;
    @NotNull(message = "昵称不能为空")
    private String  name;
    @NotNull(message = "性别不能为空")
    private Integer sex;
    private Integer age;
    private Integer role;
    @Pattern(regexp = "^1(3|9|5|7|8)\\d{9}$")
    @NotNull(message = "电话号码不能为空")
    private String  phone;
    @Email
    private String  email;
}
