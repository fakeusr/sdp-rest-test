package com.globallogic.vehicle.registry.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	@Version
	protected Integer version;
	@CreationTimestamp
	protected LocalDateTime creationDate;

	private String vin;
	private String brand;
	private String model;
	private Integer productionYear;
}
