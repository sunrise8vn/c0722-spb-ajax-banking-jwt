package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.CartAddDTO;
import com.cg.service.bill.IBillService;
import com.cg.service.cart.ICartService;
import com.cg.service.cartDetail.ICartDetailService;
import com.cg.service.product.IProductService;
import com.cg.service.user.IUserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/carts")
public class CartAPI {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBillService billService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private IProductService productService;


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CartAddDTO cartAddDTO) {

        String username = appUtil.getPrincipalEmail();

        Optional<Cart> cartOptional = cartService.findByUsername(username);

        Optional<Product> productOptional = productService.findById(cartAddDTO.getProductId());

        if (!productOptional.isPresent()) {
            throw new DataInputException("Product not valid");
        }

        Product product = productOptional.get();

        if (!cartOptional.isPresent()) {
            cartService.addNewCart(product, username);
        }
        else {
            Cart cart = cartOptional.get();

            Optional<CartDetail> cartDetailOptional = cartDetailService.findByCartAndProduct(cart, product);

            if (!cartDetailOptional.isPresent()) {
                cartService.updateCartNotExistProduct(cart, product, username);
            }
            else {
                CartDetail cartDetail = cartDetailOptional.get();
                cartService.updateCartExistProduct(cart, cartDetail, product, username);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder() {
        String username = appUtil.getPrincipalEmail();

        Optional<User> userOptional = userService.findByUsername(username);

        if (!userOptional.isPresent()) {
            throw new DataInputException("You should sign in");
        }

        User user = userOptional.get();

        Optional<Cart> cartOptional = cartService.findByUsername(username);

        if (!cartOptional.isPresent()) {
            throw new DataInputException("Cart empty");
        }

        Cart cart = cartOptional.get();

        List<CartDetail> cartDetails = cartDetailService.findAllByCart(cart);

        if (cartDetails.isEmpty()) {
            throw new DataInputException("Cart empty");
        }

        billService.createBill(cart, user, cartDetails);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
