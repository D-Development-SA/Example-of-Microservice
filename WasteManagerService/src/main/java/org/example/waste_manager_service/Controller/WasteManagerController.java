package org.example.waste_manager_service.Controller;

import jakarta.validation.Valid;
import org.example.waste_manager_service.Controller.Exception.ClassException.EmptyFieldException;
import org.example.waste_manager_service.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import org.example.waste_manager_service.Entity.WasteManagerAddressEntity;
import org.example.waste_manager_service.Entity.WasteManagerEntity;
import org.example.waste_manager_service.Entity.WasteManagerEntityDto;
import org.example.waste_manager_service.Service.Contract.WasteManagerAddressClient;
import org.example.waste_manager_service.Service.Contract.WasteManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/wasteManager")
public class WasteManagerController {
    @Autowired
    private WasteManagerService wasteManagerService;

    @Autowired
    private WasteManagerAddressClient wasteManagerAddressClient;

    @GetMapping()
    public ResponseEntity<List<WasteManagerEntity>> findAll() {
        List<WasteManagerEntity> wasteManagerEntities = wasteManagerService.findAll();
        List<WasteManagerAddressEntity> wasteManagerAddressEntities = wasteManagerAddressClient.findAll();

        if (wasteManagerEntities.isEmpty())
            throw new ListEmptyException("findAll", "wasteManagerEntities");

        wasteManagerEntities = wasteManagerEntities.stream()
                .peek(we -> {
                    long idWMA = we.getIdWasteManagerAddressEntity();
                    we.setWasteManagerAddressEntity(wasteManagerAddressEntities.stream()
                            .filter(wma -> wma.id() == idWMA)
                            .findFirst()
                            .orElseThrow());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(wasteManagerEntities);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<WasteManagerEntity> findById(@PathVariable long id) {
        WasteManagerEntity wasteManagerEntity = wasteManagerService.findById(id);
        long idWMA = wasteManagerEntity.getIdWasteManagerAddressEntity();
        WasteManagerAddressEntity wasteManagerAddress = wasteManagerAddressClient.findById(idWMA);

        wasteManagerEntity.setWasteManagerAddressEntity(wasteManagerAddress);

        return ResponseEntity.ok(wasteManagerEntity);
    }

    @PostMapping("/create")
    public ResponseEntity<WasteManagerEntity> create(@RequestBody @Valid WasteManagerEntity wasteManager) {
        long idWMA = -1;
        WasteManagerAddressEntity managerAddressEntity = wasteManager.getWasteManagerAddressEntity();

        if (managerAddressEntity != null){
            idWMA = wasteManagerAddressClient.create(managerAddressEntity);
        }
        wasteManager.setIdWasteManagerAddressEntity(idWMA);

        WasteManagerEntity wasteManagerEntity = wasteManagerService.save(wasteManager);
        return new ResponseEntity<>(wasteManagerEntity, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WasteManagerEntity> update(@RequestBody @Valid WasteManagerEntityDto wasteManagerDto, @PathVariable long id) {
        if (wasteManagerDto.wasteManagerAddressEntity() == null)
            throw new EmptyFieldException("wasteManagerDto", "null");

        WasteManagerEntity wasteManagerEntity = wasteManagerService.findById(id);

        wasteManagerEntity.setName(wasteManagerDto.name());
        wasteManagerEntity.setEnabled(wasteManagerDto.isEnabled());
        wasteManagerEntity.setNif(wasteManagerDto.nif());
        wasteManagerEntity.setWasteManagerAddressEntity(wasteManagerDto.wasteManagerAddressEntity());
        wasteManagerEntity.getWasteCenterAuthorizationEntities().retainAll(wasteManagerDto.wasteCenterAuthorizationEntities());
        wasteManagerEntity.getWasteCenterAuthorizationEntities().addAll(wasteManagerDto.wasteCenterAuthorizationEntities());
        return ResponseEntity.ok(wasteManagerService.save(wasteManagerEntity));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        WasteManagerEntity wasteManager = wasteManagerService.findById(id);
        wasteManagerAddressClient.delete(wasteManager.getIdWasteManagerAddressEntity());
        wasteManagerService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
