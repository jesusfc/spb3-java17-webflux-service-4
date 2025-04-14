package com.jesus.webflux.runners;

import com.jesus.webflux.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * A Spring Boot CommandLineRunner implementation that tests the interaction
 * with a reactive service (Service 3) using WebClient.
 *
 * This runner fetches a Product by its ID from the service and prints it to the console.
 *
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

        System.out.println("Service3TestRunner");

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
}