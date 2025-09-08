package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Abacus.Model.Inventory;
import com.example.Abacus.Service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/createInventory")
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }


    @DeleteMapping("/deleteInventory/{id}")
    public void deleteInventory(@PathVariable int id) {
        inventoryService.deleteInventory(id);
    }

    @PutMapping("/updateInventory/{id}")
    public Inventory updateInventory(@PathVariable int id,@RequestBody Inventory updatedInventory) {
        return this.inventoryService.updateInventory(id, updatedInventory);
    }

    @GetMapping("/getInventoryById/{id}")
    public Inventory getInventoryById(@PathVariable int id) {
        return this.inventoryService.getInventoryById(id);
    }

    @GetMapping("/getAllInventories")
    public List<Inventory> getAllInventories(){
        return this.inventoryService.getAllInventories();
    }

    
}
