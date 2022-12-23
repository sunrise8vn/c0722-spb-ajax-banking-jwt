package com.cg.service.bill;


import com.cg.model.*;
import com.cg.repository.BillDetailRepository;
import com.cg.repository.BillRepository;
import com.cg.repository.CartDetailRepository;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillServiceImpl implements IBillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<Bill> findAll() {
        return null;
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Bill getById(Long id) {
        return null;
    }

    @Override
    public void createBill(Cart cart, User user, List<CartDetail> cartDetails) {
        Bill bill = new Bill();
        bill.setTotalAmount(cart.getTotalAmount());
        bill.setUser(user);
        billRepository.save(bill);

        for (CartDetail cartDetail: cartDetails) {
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setProductTitle(cartDetail.getProductTitle());
            billDetail.setProductPrice(cartDetail.getProductPrice());
            billDetail.setProductQuantity(cartDetail.getProductQuantity());
            billDetail.setProductAmount(cartDetail.getProductAmount());
            billDetailRepository.save(billDetail);

            cartDetailRepository.delete(cartDetail);
        }

        cartRepository.delete(cart);
    }

    @Override
    public Bill save(Bill bill) {
        return null;
    }

    @Override
    public void delete(Bill bill) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
