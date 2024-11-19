package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.model.Rent;
import pl.lodz.p.repository.RentRepository;
import pl.lodz.p.service.RentService;

import java.util.List;

@Service
@AllArgsConstructor
public class RentServiceImplementation implements RentService {

    RentRepository repo;

    @Override
    public Rent createRent(Rent rent) {
        repo.add(rent);
        return rent;
    }

    @Override
    public List<Rent> getAllRents() {
        return repo.getRents();
    }
}

