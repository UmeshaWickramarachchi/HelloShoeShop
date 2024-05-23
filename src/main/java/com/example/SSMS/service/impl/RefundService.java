package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.RefundRequestDTO;
import com.example.SSMS.model.Inventory;
import com.example.SSMS.model.OrderItems;
import com.example.SSMS.model.Refund;
import com.example.SSMS.model.Sale;
import com.example.SSMS.repository.InventoryDAO;
import com.example.SSMS.repository.OrderItemsDAO;
import com.example.SSMS.repository.RefundDAO;
import com.example.SSMS.repository.SalesDAO;
import com.example.SSMS.service.RefundServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefundService implements RefundServiceI {

    @Autowired
    SalesDAO salesDAO;
    @Autowired
    OrderItemsDAO orderItemsDAO;
    @Autowired
    RefundDAO refundDAO;
    @Autowired
    InventoryDAO inventoryDAO;

    @Override
    public Refund sentRefundRequest(RefundRequestDTO req) {
        Refund refund = new Refund();
        Sale refundableSale = salesDAO.findByOrderNo(req.getOrderNo());
        if(refundableSale != null){
            refund.setSale(refundableSale);
            ArrayList<OrderItems> items = new ArrayList<>();
            double refundAmount = 0;
            String orderid = refundableSale.getOrderNo();
            for (String id:req.getItemCodes()) {
                List<OrderItems> orderedItems = orderItemsDAO.findByItemCode(id);
                for(OrderItems orderedItem : orderedItems){
                    if(orderedItem.getSale().getOrderNo().equals(orderid)){
                        items.add(orderedItem);
                        refundAmount += orderedItem.getTotalPriceOfEachItem();
                    }
                }
            }
            refund.setOrderItems(items);
            refund.setRefundAmount(refundAmount);
            refundDAO.save(refund);
            return refund;
        }
        else{
            throw new RuntimeException("Record is exist");
        }
    }

    @Override
    public List<Refund> getRefundRequests() {
        return refundDAO.findAll();
    }

    @Override
    public Refund ApproveOrRejectRefundRequest(Long id) {
        Refund refund = refundDAO.findById(id);
        if (refund != null) {
            refund.getSale().setRefundStatus(true);
            for (OrderItems item : refund.getOrderItems()) {
                Inventory orderedItem = inventoryDAO.findByItemCode(item.getItemCode());
                if (orderedItem != null) {
                    int qty = item.getQty();
                    orderedItem.setQty(orderedItem.getQty() + qty);
                }
            }
            double newTotalPrice = refund.getSale().getTotalPrice() - refund.getRefundAmount();
            refund.getSale().setTotalPrice(newTotalPrice);
            refundDAO.save(refund);
        }
        return refund;
    }

}
