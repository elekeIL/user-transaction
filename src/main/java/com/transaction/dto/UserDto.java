package com.transaction.dto;

import com.transaction.enums.GenericStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String email;
    private String displayname;
    private String firstName;
    private String lastName;
    private String middleName;
    private String corporateName;
    private String photo;
    private String address;
    private String phoneNumber;
    private String alternative_phoneNumber;
    private String tinNumber;
    private Long userId;
    private String taxgroup;
    private LocalDate dob;
    private GenericStatus status;
    private Boolean nonLocked;
    private Long lgaId;
    private Long areaId;
    private String rcNumber;
    private String lgaName;
    private String areaName;
    private Long zoneId;
    private String priviliages;
    private String gender;
    private Long taxOffice;
    private LocalDateTime timeCreated;
    private String taxOfficeName;
    private String role;

}
