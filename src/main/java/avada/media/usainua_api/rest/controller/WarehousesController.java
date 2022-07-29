package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.Warehouse;
import avada.media.usainua_api.repository.WarehouseRepo;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "warehouse")
@RequiredArgsConstructor
public class WarehousesController {
    private final WarehouseRepo warehouseRepo;

    @ApiOperation(value = "Get Warehouses List", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllWarehouses", produces = "application/json")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return ResponseEntity.ok().body(warehouseRepo.findAll());
    }

}
