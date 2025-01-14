package pl.lodz.p.model;

import pl.lodz.p.manager.UserManager;
import pl.lodz.p.manager.RentManager;
import pl.lodz.p.manager.VMachineManager;

public class Main {

    private static final VMachineManager vMachineManager = VMachineManager.getInstance();
    private static final UserManager clientManager = UserManager.getInstance();
    private static final RentManager rentManager = RentManager.getInstance();

    public static void main(String[] args) {
    }
}
