package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.User;
import com.cg.service.IGeneralService;

import java.util.List;


public interface IBillService extends IGeneralService<Bill> {

    void createBill(Cart cart, User user, List<CartDetail> cartDetails);
}
