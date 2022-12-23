package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.service.IGeneralService;

import java.util.Optional;


public interface ICartService extends IGeneralService<Cart> {

    Optional<Cart> findByUsername(String username);

    void addNewCart(Product product, String username);

    void updateCartNotExistProduct(Cart cart, Product product, String username);

    void updateCartExistProduct(Cart cart, CartDetail cartDetail, Product product, String username);
}
