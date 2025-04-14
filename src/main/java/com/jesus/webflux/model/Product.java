package com.jesus.webflux.model;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
public record Product(
        Integer codProduct,
        String name,
        String category,
        double unitPrice,
        int stock) {
}

