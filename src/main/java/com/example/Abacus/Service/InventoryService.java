package com.example.Abacus.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.response.InventoryResponse;
import com.example.Abacus.Model.Inventory;
import com.example.Abacus.Model.MasterAdmin;
import com.example.Abacus.Repo.InventoryRepo;
import com.example.Abacus.Repo.MasterAdminRepo;

@Service
public class InventoryService {
    

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private MasterAdminRepo masterAdminRepo;

    public Inventory createInventory(Inventory inventory, int masterAdminId) {
MasterAdmin masterAdmin = masterAdminRepo.findById(masterAdminId)
            .orElseThrow(() -> new IllegalArgumentException("Master admin not found with id: " + masterAdminId));
            inventory.setMasterAdmin(masterAdmin);
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

   public List<InventoryResponse> getAllInventories(int masterAdminId) {
    masterAdminRepo.findById(masterAdminId)
        .orElseThrow(() -> new IllegalArgumentException("No MasterAdmin found with ID: " + masterAdminId));

    return inventoryRepo.findAll().stream()
        .filter(inventory -> inventory.getMasterAdmin().getId()==masterAdminId)
        .map(inventory -> new InventoryResponse(
                inventory.getId(),
                inventory.getItemName(),
                inventory.getQuantity(),
                inventory.getPricePerItem()
        )).collect(Collectors.toList());
}

public List<InventoryResponse> getAllInventory(){
    return inventoryRepo.findAll().stream()
        .map(inventory -> new InventoryResponse(
                inventory.getId(),
                inventory.getItemName(),
                inventory.getQuantity(),
                inventory.getPricePerItem()
        )).collect(Collectors.toList());
}











}
