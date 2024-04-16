package org.example.waste_manager_service.Service.Contract;

import jakarta.validation.Valid;
import org.example.waste_manager_service.Entity.WasteManagerAddressEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("waste-manager-address-service")
public interface WasteManagerAddressClient {
    @GetMapping("api/wasteManagerAddress")
    List<WasteManagerAddressEntity> findAll();

    @GetMapping("api/wasteManagerAddress/findById/{id}")
    WasteManagerAddressEntity findById(@PathVariable long id);

    @PostMapping("api/wasteManagerAddress/create")
    long create(@RequestBody @Valid WasteManagerAddressEntity wasteManager);

    @PutMapping("api/wasteManagerAddress/update/{id}")
    WasteManagerAddressEntity update(@RequestBody @Valid WasteManagerAddressEntity managerAddressEntity, @PathVariable long id);

    @DeleteMapping("api/wasteManagerAddress/delete/{id}")
    ResponseEntity<Void> delete(@PathVariable long id);
}