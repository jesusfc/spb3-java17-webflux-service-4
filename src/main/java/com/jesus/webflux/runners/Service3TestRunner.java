package com.jesus.webflux.runners;

import com.jesus.webflux.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A Spring Boot CommandLineRunner implementation that tests the interaction
 * with a reactive service (Service 3) using WebClient.
 * <p>
 * This runner fetches a Product by its ID from the service and prints it to the console.
 * <p>
 * Author: Jesús Fdez. Caraballo
 * Email: jesus.fdez.caraballo@gmail.com
 * Created on: April 2025
 */
@Component
public class Service3TestRunner implements CommandLineRunner {

    // Base URL of the Service 3 API
    private static final String SERVICE_3_URL = "http://localhost:8083";

    /**
     * Executes the runner logic when the application starts.
     *
     * @param args Command-line arguments passed to the application.
     * @throws Exception if an error occurs during execution.
     */
    @Override
    public void run(String... args) throws Exception {

        //service3TestGet();
        //service3TestPost();
        //service3TestGetById();
        service3TestDeleteById();
    }

    private void service3TestDeleteById() {
        System.out.println("Service3TestRunner - service3TestDeleteById()");

        // Create a WebClient instance to interact with the Service 3 API
        WebClient client = WebClient.create(SERVICE_3_URL);

        // Fetch a Product by its ID from the Service 3 API
        Mono<Product> product = client.delete()
                .uri("/reactive/product/delete/101") // Endpoint to fetch a product by its ID
                .accept(MediaType.APPLICATION_JSON) // Specify that the response should be in JSON format
                .retrieve() // Retrieve the response
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    System.out.println("Error: " + clientResponse.statusCode());
                    return Mono.empty();
                }).bodyToMono(Product.class); // Convert the response body to a Mono of Product

        // Subscribe to the Mono and print the Product details to the console
        product.subscribe(p -> System.out.println("Product Eliminado!!!! "));
    }

    private void service3TestGetById() {

        System.out.println("Service3TestRunner - service3TestGetById()");

        // Create a WebClient instance to interact with the Service 3 API
        WebClient client = WebClient.create(SERVICE_3_URL);

        // Fetch a Product by its ID from the Service 3 API
        Mono<Product> product = client.get()
                .uri("/reactive/product/1000") // Endpoint to fetch a product by ID
                .accept(MediaType.APPLICATION_JSON) // Specify that the response should be in JSON format
                .retrieve() // Retrieve the response
                .bodyToMono(Product.class); // Convert the response body to a Mono of Product

        // Subscribe to the Mono and print the Product details to the console
        product.subscribe(p -> System.out.println("Product: " + p));
        // Show message if product is null or empty
        product.switchIfEmpty(Mono.just(new Product(0, "NOT FOUND", "", 0, 0)).map(p -> {
            System.out.println("Product not found");
            return p;
        })).block(); // Block the Mono to wait for the result

    }


    private void service3TestGet() {
        System.out.println("Service3TestRunner - service3TestGet()");

        // Create a WebClient instance to interact with the Service 3 API
        WebClient client = WebClient.create(SERVICE_3_URL);

        // Fetch a Product by its ID from the Service 3 API
        Flux<Product> product = client.get()
                .uri("/reactive/product/all") // Endpoint to fetch a product by ID
                .accept(MediaType.APPLICATION_JSON) // Specify that the response should be in JSON format
                .retrieve() // Retrieve the response
                .bodyToFlux(Product.class); // Convert the response body to a Flux of Product

        // Subscribe to the Flux and print the Product details to the console
        product.subscribe(p -> System.out.println("Product: " + p));
    }

    private void service3TestPost() {
        System.out.println("Service3TestRunner - service3TestPost()");

        // Create a WebClient instance to interact with the Service 3 API
        WebClient client = WebClient.create(SERVICE_3_URL);

        // Create a new Product object to be sent in the POST request
        Product product = new Product(110, "Chocolate", "Alimentación", 10.0, 100);

        // Send a POST request to create a new Product in the Service 3 API
        Mono<Product> productMono = client.post()
                .uri("/reactive/product/add") // Endpoint to create a new product
                .contentType(MediaType.APPLICATION_JSON) // Specify that the request body is in JSON format
                .bodyValue(product) // Set the request body to the Product object
                .retrieve() // Retrieve the response
                .bodyToMono(Product.class);

        // Subscribe to the Mono and print the created Product details to the console
        productMono.subscribe(p -> System.out.println("Product created: " + p));
    }
}