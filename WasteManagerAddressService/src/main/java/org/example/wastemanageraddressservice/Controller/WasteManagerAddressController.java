package org.example.wastemanageraddressservice.Controller;

import jakarta.validation.Valid;
import org.example.wastemanageraddressservice.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntity;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntityDto;
import org.example.wastemanageraddressservice.Service.Contract.WasteManagerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wasteManagerAddress")
public class WasteManagerAddressController {
    private final WasteManagerAddressService wasteManagerService;

    @Autowired
    public WasteManagerAddressController(WasteManagerAddressService wasteManagerService) {
        this.wasteManagerService = wasteManagerService;
    }

    @GetMapping()
    public ResponseEntity<List<WasteManagerAddressEntity>> findAll() {
        List<WasteManagerAddressEntity> wasteManagerEntities = wasteManagerService.findAll();

        if (wasteManagerEntities.isEmpty())
            throw new ListEmptyException("findAll", "wasteManagerEntities");

        return ResponseEntity.ok(wasteManagerEntities);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<WasteManagerAddressEntity> findById(@PathVariable long id) {
        WasteManagerAddressEntity wasteManagerAddressEntity = wasteManagerService.findById(id);
        return ResponseEntity.ok(wasteManagerAddressEntity);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody @Valid WasteManagerAddressEntity wasteManager) {
        WasteManagerAddressEntity wasteManagerAddressEntity = wasteManagerService.save(wasteManager);
        return new ResponseEntity<>(wasteManagerAddressEntity.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WasteManagerAddressEntity> update(@RequestBody @Valid WasteManagerAddressEntityDto managerAddressEntityDto, @PathVariable long id) {
        WasteManagerAddressEntity wasteManagerAddressEntity = wasteManagerService.findById(id);

        wasteManagerAddressEntity.setAddress(managerAddressEntityDto.address());
        wasteManagerAddressEntity.setEnabled(managerAddressEntityDto.isEnabled());
        wasteManagerAddressEntity.setVersion(managerAddressEntityDto.version());

        return ResponseEntity.ok(wasteManagerService.save(wasteManagerAddressEntity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        wasteManagerService.findById(id);
        wasteManagerService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
