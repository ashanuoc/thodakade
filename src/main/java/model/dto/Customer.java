package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer {
    private String custID;
    private String title;
    private String name;
    private LocalDate dob;
    private double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;

}
