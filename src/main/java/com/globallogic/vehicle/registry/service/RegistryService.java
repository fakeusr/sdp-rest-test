package com.globallogic.vehicle.registry.service;

import com.globallogic.vehicle.registry.controller.VehicleSO;
import com.globallogic.vehicle.registry.entities.Vehicle;
import com.globallogic.vehicle.registry.exceptions.RegistryResourceNotFound;
import com.globallogic.vehicle.registry.repository.RegistryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistryService {

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    protected ModelMapper modelMapper;

    public VehicleSO get(String vin) {
        Vehicle found = registryRepository.findByVin(vin);

        if (found == null) {
            throw new RegistryResourceNotFound("Vehicle with given VIN does not exist.");
        }

        return modelMapper.map(found, VehicleSO.class);
    }

    public List<VehicleSO> getAll() {
        List<Vehicle> vehicles = registryRepository.findAll();

        if (vehicles.isEmpty()) throw new RegistryResourceNotFound("No vehicles found");

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleSO.class))
                .collect(Collectors.toList());
    }

    public VehicleSO create(VehicleSO so) {
        Vehicle vehicle = modelMapper.map(so, Vehicle.class);

        return modelMapper.map(registryRepository.save(vehicle), VehicleSO.class);
    }

    public void delete(String vin) {
        Vehicle toDelete = registryRepository.findByVin(vin);
        if (toDelete == null) return;

        registryRepository.delete(toDelete);
    }

    public VehicleSO update(String vin, VehicleSO so) {
        Vehicle toUpdate = registryRepository.findByVin(vin);
        if (toUpdate == null) throw new RegistryResourceNotFound("Vehicle with given VIN does not exist.");

        if (so.getVin() != null) toUpdate.setVin(so.getVin());
        if (so.getModel() != null) toUpdate.setModel(so.getModel());
        if (so.getBrand() != null) toUpdate.setBrand(so.getBrand());
        if (so.getProductionYear() != null) toUpdate.setProductionYear(so.getProductionYear());

        return modelMapper.map(registryRepository.save(toUpdate), VehicleSO.class);
    }
}