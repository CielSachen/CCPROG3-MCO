package cielsachen.ccprog3.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.ccprog3.model.coffee.Americano;
import cielsachen.ccprog3.model.coffee.Cappuccino;
import cielsachen.ccprog3.model.coffee.Coffee;
import cielsachen.ccprog3.model.coffee.Espresso;
import cielsachen.ccprog3.model.coffee.Latte;
import cielsachen.ccprog3.model.storagebin.AlmondSyrupStorageBin;
import cielsachen.ccprog3.model.storagebin.ChocolateSyrupStorageBin;
import cielsachen.ccprog3.model.storagebin.CoffeeBeanStorageBin;
import cielsachen.ccprog3.model.storagebin.HazelnutSyrupStorageBin;
import cielsachen.ccprog3.model.storagebin.LargeCupStorageBin;
import cielsachen.ccprog3.model.storagebin.MediumCupStorageBin;
import cielsachen.ccprog3.model.storagebin.MilkStorageBin;
import cielsachen.ccprog3.model.storagebin.SmallCupStorageBin;
import cielsachen.ccprog3.model.storagebin.StorageBin;
import cielsachen.ccprog3.model.storagebin.WaterStorageBin;
import cielsachen.ccprog3.model.truck.SpecialTruck;
import cielsachen.ccprog3.model.truck.Truck;
import cielsachen.ccprog3.service.CoffeeService;
import cielsachen.ccprog3.service.StorageBinService;
import cielsachen.ccprog3.service.TruckService;
import cielsachen.ccprog3.util.Input;
import cielsachen.ccprog3.util.PrintColor;

public class TruckController {
    private final CoffeeService coffeeService;
    private final Input input;
    private final Scanner scanner;
    private final TruckService service;
    private final StorageBinService storageBinService;

    public TruckController(TruckService service, StorageBinService storageBinService, CoffeeService coffeeService,
            Scanner scanner, Input input) {
        this.coffeeService = coffeeService;
        this.input = input;
        this.scanner = scanner;
        this.service = service;
        this.storageBinService = storageBinService;
    }

    public void printTruckInfo(Truck truck) {
        System.out.println("---------- + "
                + PrintColor.setColor("Truck #" + truck.id + " Info Summary", PrintColor.BRIGHT_YELLOW)
                + " + ----------");

        System.out.println();

        System.out.println(
                "Special Truck: " + (truck instanceof SpecialTruck ? PrintColor.setColor("Yes", PrintColor.BRIGHT_GREEN)
                        : PrintColor.setColor("No", PrintColor.RED)));
        System.out.println("Location: " + PrintColor.setColor(truck.getLocation(), PrintColor.BRIGHT_CYAN));

        System.out.println();

        System.out.println("Storage Bins:");

        List<StorageBin> storageBins = this.storageBinService.getStorageBinsByTruck(truck);

        for (int index = 0; index < storageBins.size(); index++) {
            StorageBin storageBin = storageBins.get(index);

            System.out.println(
                    "  Bin #" + index + " = " + PrintColor.setColor(storageBin.itemName, PrintColor.BRIGHT_CYAN) + " ("
                            + PrintColor.setColor(storageBin.getCapacity() + " / " + storageBin.maximumCapacity,
                                    storageBin.getCapacity() < storageBin.maximumCapacity / 3 ? PrintColor.RED
                                            : PrintColor.BRIGHT_GREEN)
                            + ")");
        }

        System.out.println();

        System.out.println("Coffee Prices:");

        for (Coffee coffee : this.coffeeService.getCoffeesByTruck(truck)) {
            System.out.println(
                    "  " + coffee.name + " = " + PrintColor.setColor("PHP " + coffee.price, PrintColor.BRIGHT_GREEN));
        }

        if (truck instanceof SpecialTruck specialTruck) {
            System.out.println();

            System.out.println("Extra Espresso Shots = " + PrintColor
                    .setColor("PHP " + specialTruck.getExtraEspressoShotPrice(), PrintColor.BRIGHT_GREEN));
            System.out.println("Syrup Add-ons = "
                    + PrintColor.setColor("PHP " + specialTruck.getSyrupPrice(), PrintColor.BRIGHT_GREEN));
        }
    }

    public Truck createTruck() {
        System.out.println("---------- + ---------- + "
                + PrintColor.setColor("Truck Creation Menu", PrintColor.BRIGHT_YELLOW) + " + ---------- + ----------");

        System.out.println();

        boolean isSpecial = this.input.getBoolean("Is this truck a special coffee truck (JavaJeep+)?");

        System.out.print("Where will this truck be located? ");

        String location = this.scanner.nextLine();
        int id = this.service.getTrucks().size();
        Truck truck = isSpecial ? new SpecialTruck(id, location) : new Truck(id, location);

        this.service.addTruck(truck);

        System.out.println();

        System.out.println("---------- + "
                + PrintColor.setColor("Truck Provisioning", PrintColor.BRIGHT_YELLOW) + " + ----------");

        String integerInputErrorMessage = "Only input from one of the integer IDs!";

        for (int storageId = 0; storageId < (isSpecial ? StorageBin.SPECIAL_TRUCK_AMOUNT
                : StorageBin.STANDARD_TRUCK_AMOUNT); storageId++) {
            while (true) {
                try {
                    System.out.println();

                    List<StorageBin> storageBins = this.storageBinService.getStorageBinsByTruck(truck);

                    for (int index = 0; index < storageBins.size(); index++) {
                        System.out.println(
                                PrintColor.setColor("Bin #" + index + " = " + storageBins.get(index).itemName,
                                        PrintColor.BRIGHT_GREEN));
                    }

                    System.out.println("Bin #" + storageId + " =");

                    System.out.println();

                    System.out.println("What item should this storage bin contain?");

                    if (storageId < StorageBin.STANDARD_TRUCK_AMOUNT) {
                        System.out.println("  [1] Small Cups");
                        System.out.println("  [2] Medium Cups");
                        System.out.println("  [3] Large Cups");
                        System.out.println("  [4] Coffee Beans");
                        System.out.println("  [5] Milk");
                        System.out.println("  [6] Water");
                    } else {
                        System.out.println("  [7] Hazelnut Syrup");
                        System.out.println("  [8] Chocolate Syrup");
                        System.out.println("  [9] Almond Syrup");
                    }

                    System.out.println();

                    System.out.print("  > ");

                    int chosenItemId = this.scanner.nextInt();

                    this.scanner.nextLine();

                    StorageBin storage;

                    if (storageId < StorageBin.STANDARD_TRUCK_AMOUNT) {
                        switch (chosenItemId) {
                            case 1:
                                storage = new SmallCupStorageBin(storageId, id);

                                break;
                            case 2:
                                storage = new MediumCupStorageBin(storageId, id);

                                break;
                            case 3:
                                storage = new LargeCupStorageBin(storageId, id);

                                break;
                            case 4:
                                storage = new CoffeeBeanStorageBin(storageId, id);

                                break;
                            case 5:
                                storage = new MilkStorageBin(storageId, id);

                                break;
                            case 6:
                                storage = new WaterStorageBin(storageId, id);

                                break;
                            default:
                                System.out.println();

                                System.out.println(PrintColor.setColor(integerInputErrorMessage, PrintColor.RED));

                                continue;
                        }
                    } else {
                        switch (chosenItemId) {
                            case 7:
                                storage = new HazelnutSyrupStorageBin(storageId, id);

                                break;
                            case 8:
                                storage = new ChocolateSyrupStorageBin(storageId, id);

                                break;
                            case 9:
                                storage = new AlmondSyrupStorageBin(storageId, id);

                                break;
                            default:
                                System.out.println();

                                System.out.println(PrintColor.setColor(integerInputErrorMessage, PrintColor.RED));

                                continue;
                        }
                    }

                    this.storageBinService.addStorage(storage);

                    break;
                } catch (InputMismatchException exception) {
                    this.scanner.nextLine();

                    System.out.println();

                    System.out.println(PrintColor.setColor(integerInputErrorMessage, PrintColor.RED));
                }
            }
        }

        for (StorageBin storageBin : this.storageBinService.getStorageBinsByTruck(truck)) {
            storageBin.increaseCapacity(storageBin.maximumCapacity);
        }

        System.out.println();

        this.coffeeService.addCoffee(new Espresso(id, this.input.getFloat(
                "What price should " + PrintColor.setColor("Espressos", PrintColor.YELLOW) + " be sold for?")));
        this.coffeeService.addCoffee(new Americano(id, this.input.getFloat(
                "What price should " + PrintColor.setColor("Café Americanos", PrintColor.YELLOW) + " be sold for?")));
        this.coffeeService.addCoffee(new Latte(id, this.input
                .getFloat("What price should " + PrintColor.setColor("Lattes", PrintColor.YELLOW) + " be sold for?")));
        this.coffeeService.addCoffee(new Cappuccino(id, this.input.getFloat(
                "What price should " + PrintColor.setColor("Cappuccinos", PrintColor.YELLOW) + " be sold for?")));

        if (truck instanceof SpecialTruck specialTruck) {
            System.out.println();

            specialTruck.setExtraEspressoShotPrice(this.input.getFloat("What price should "
                    + PrintColor.setColor("extra Espresso shots", PrintColor.YELLOW) + " be sold for?"));
            specialTruck.setSyrupPrice(this.input.getFloat(
                    "What price should " + PrintColor.setColor("syrup add-ons", PrintColor.YELLOW) + " be sold for?"));
        }

        System.out.println();

        System.out.println(PrintColor.setColor("Created a new coffee truck!", PrintColor.BRIGHT_GREEN));

        System.out.println();

        this.printTruckInfo(truck);

        return truck;
    }
}
