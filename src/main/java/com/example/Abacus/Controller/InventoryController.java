package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.Model.Inventory;
import com.example.Abacus.Repo.InventoryRepo;
import com.example.Abacus.Service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/createInventory")
    public Inventory createInventory(Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }


    @DeleteMapping("/deleteInventory/{id}")
    public void deleteInventory(int id) {
        inventoryService.deleteInventory(id);
    }

    @PutMapping("/updateInventory/{id}")
    public Inventory updateInventory(int id, Inventory updatedInventory) {
return this.inventoryService.updateInventory(id, updatedInventory);
    }

    @GetMapping("/getInventoryById/{id}")
    public Inventory getInventoryById(int id) {
        return this.inventoryService.getInventoryById(id);
    }

    @GetMapping("/getAllInventories")
    public List<Inventory> getAllInventories(){
        return this.inventoryService.getAllInventories();
    }

    
}
