package com.cg.service.cart;


import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.repository.CartDetailRepository;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Optional<Cart> findByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    @Override
    public void addNewCart(Product product, String username) {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setTotalAmount(product.getPrice());
        cart.setUsername(username);
        cartRepository.save(cart);

        CartDetail cartDetail = new CartDetail();
        cartDetail.setId(null);
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setProductTitle(product.getTitle());
        cartDetail.setProductPrice(product.getPrice());
        cartDetail.setProductQuantity(1L);
        cartDetail.setProductAmount(product.getPrice());
        cartDetailRepository.save(cartDetail);
    }

    @Override
    public void updateCartNotExistProduct(Cart cart, Product product, String username) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setId(null);
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setProductTitle(product.getTitle());
        cartDetail.setProductPrice(product.getPrice());
        cartDetail.setProductQuantity(1L);
        cartDetail.setProductAmount(product.getPrice());
        cartDetailRepository.save(cartDetail);

        BigDecimal totalAmount = cartDetailRepository.sumTotalAmountByCartId(cart.getId());
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartExistProduct(Cart cart, CartDetail cartDetail, Product product, String username) {
        Long currentQuantity = cartDetail.getProductQuantity();
        long newQuantity = currentQuantity + 1;
        BigDecimal price = product.getPrice();
        BigDecimal newAmount = price.multiply(BigDecimal.valueOf(newQuantity));

        cartDetail.setProductQuantity(newQuantity);
        cartDetail.setProductAmount(newAmount);
        cartDetailRepository.save(cartDetail);

        BigDecimal totalAmount = cartDetailRepository.sumTotalAmountByCartId(cart.getId());
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    public BigDecimal sumTotalAmountCartDetail(Cart cart) {
        BigDecimal totalAmount = BigDecimal.valueOf(0L);

//        List<CartDetail> cartDetails = cartDetailRepository.findAllByCart(cart);

        return totalAmount;
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public void delete(Cart cart) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
