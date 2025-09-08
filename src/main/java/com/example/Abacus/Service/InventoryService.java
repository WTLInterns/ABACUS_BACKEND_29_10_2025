package com.example.Abacus.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Inventory;
import com.example.Abacus.Repo.InventoryRepo;

@Service
public class InventoryService {
    

    @Autowired
    private InventoryRepo inventoryRepo;

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepo.save(inventory);
    }

    public void deleteInventory(int id) {
        inventoryRepo.deleteById(id);
    }


    public Inventory updateInventory(int id, Inventory updatedInventory) {
        Inventory existingInventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found with id: " + id)); 

                 existingInventory.setItemName(updatedInventory.getItemName());
                 existingInventory.setQuantity(updatedInventory.getQuantity());
                 existingInventory.setPricePerItem(updatedInventory.getPricePerItem());

                 return inventoryRepo.save(existingInventory);
        


    }

    public Inventory getInventoryById(int id) {
        return this.inventoryRepo.findById(id) .orElseThrow(() -> new IllegalArgumentException("Inventory not found with id: " + id));
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepo.findAll();
    }




}
