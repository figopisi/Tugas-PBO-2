package service.CustomerService;

import DAO.Customer.CustomerDAO;
import model.Customer;
import util.Request;
import util.Response.JsonHelper;
import util.Response.ResponseHelper;
import util.validator.CustomerValidator;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class CustomerService {

    private static final CustomerDAO customerDAO = new CustomerDAO();

    public static void index(ResponseHelper res) {
        System.out.println(">> Masuk ke CustomerService.index()");
        List<Customer> customers = customerDAO.findAll();
        res.setBody(JsonHelper.success(customers));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void show(int id, ResponseHelper res) {
        Customer customer = customerDAO.findById(id);
        res.setBody(JsonHelper.success(customer));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void create(Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        CustomerValidator.validateInput(body);

        Customer customer = new Customer(
                (String) body.get("name"),
                (String) body.get("email"),
                (String) body.get("phone")
        );

        int newId = customerDAO.create(customer);

        res.setBody(JsonHelper.created(newId, "Customer berhasil dibuat."));
        res.send(HttpURLConnection.HTTP_CREATED);
    }

    public static void update(int id, Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        CustomerValidator.validateInput(body);

        Customer customer = new Customer(
                id,
                (String) body.get("name"),
                (String) body.get("email"),
                (String) body.get("phone")
        );

        customerDAO.update(id, customer);

        res.setBody(JsonHelper.success("Customer berhasil diperbarui."));
        res.send(HttpURLConnection.HTTP_OK);
    }
}