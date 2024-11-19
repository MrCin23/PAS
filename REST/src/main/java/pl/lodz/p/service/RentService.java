package pl.lodz.p.service;

import pl.lodz.p.model.Rent;

import java.util.List;

public interface RentService {
    Rent createRent(Rent rent);

    List<Rent> getAllRents();
}
