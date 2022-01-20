package com.globallogic.vehicle.registry.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleSO {
    private Integer id;
    private String vin;
    private Integer productionYear;
    private String brand;
    private String model;
}
