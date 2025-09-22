package com.example.demo.model;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "buses")
public class Buses {
    @Id
    private String id;

    @NotBlank(message = "Driver name is required")
    private String DriverName;

    @Indexed(unique = true)
    private String busPlate;

    @Indexed(unique = true)
    @NotBlank(message = "Bus number is required")
    private String busNo;
    public String getbusNo(){
        return busNo;
    }
    public void setbusNo(String s){
        this.busNo=busNo;
    }

    @Min(value = 1, message = "Number of passengers must be at least 1")
    private int passengers;

    private Location location;

}