package CarShop;

import datamanager.DataManager;
import models.Car;
import models.Client;
import models.Order;

import java.util.*;

/**
 * Created by sa on 08.02.17.
 */
public class Store {
    private HashMap<Order, Client> contractList = new HashMap<>(256);
    private HashSet<Car> cars = new HashSet<>(32);
    private HashSet<Client> clients = new HashSet<>(256);
    private static final String FILE_CONTRACTS = "fileContracts.txt";
    private static final String FILE_CARS = "fileCars.txt";
    private static final String FILE_CLIENTS = "fileClients.txt";


    public void createCar(int price, String model,
                          String regNumber){
        Car car = new Car(price, model, regNumber);
        cars.add(car);
    }

    public void save (){
        DataManager.serialize(cars, FILE_CARS);
        //DataManager.serialize(clients, FILE_CLIENTS);
        //DataManager.serialize(contractList, FILE_CONTRACTS);
    }

    public void recover (){
        ArrayList <Car> list = new ArrayList<>();
        DataManager.deserialize(FILE_CARS, list);
        for (Car car:
             list) {
            cars.add(car);
        }
return;
//        ArrayList <Client> listClient = new ArrayList<>();
//        DataManager.deserialize(FILE_CLIENTS, listClient);
//        for (Client client:
//                listClient) {
//            clients.add(client);
//        }
//
//        ArrayList <Order> contractListOne = new ArrayList<>();
//        ArrayList <Client> contractListTwo = new ArrayList<>();
//        DataManager.deserialize(FILE_CONTRACTS,  contractList);
    }

    public Order getFirstOrder(){
        for (Order order:
                contractList.keySet()) {
            return order;
        }
        return null;

    }

    public void sellCar(String model,
                        String firstName,
                        String lastName,
                        String phoneNumber) throws CarNotFoundException {
        Client client = new Client(firstName,
                lastName, phoneNumber);
        clients.add(client);

        Car tmpCar = null;
        for (Car car:
                cars) {
            if (car.getModel().equals(model)){
                tmpCar = car;
                break;
            }
        }
        if (tmpCar != null){
            Random random = new Random();
            Order order = new Order(tmpCar,
                    tmpCar.getPrice() * 2,
                    random.nextLong(), (short) 80
            );
            contractList.put(order, client);
            cars.remove(tmpCar);
        }
        else{
            System.out.println("Car not found");
            throw new CarNotFoundException();
        }
    }

    public List<Order> getOrders(){
        List <Order> list = new ArrayList<>();
        for (Order order :
                contractList.keySet()) {
//            System.out.println(order.toString());
            list.add(order);
        }
        return list;
    }

    public ArrayList<Car> getFreeCars(){
        ArrayList <Car> arrCars = new ArrayList<>();
        for (Car car:
                cars){
            arrCars.add(car);
        }
        return arrCars;
    }

    public Map<Order, Client> getContractList(){
        return contractList;
    }
}
