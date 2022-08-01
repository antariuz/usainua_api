package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.model.Product;
import avada.media.usainua_api.model.ShoppingMall;
import avada.media.usainua_api.repository.ShoppingMallRepo;
import avada.media.usainua_api.service.ProductService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@Api(tags = "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ShoppingMallRepo shoppingMallRepo;

    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "5";
    private final String DEFAULT_SORT_BY = "id";
    private final String DEFAULT_SORT_DIRECTION = "DESC";

    @ApiOperation(value = "Get All Products", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getProductsByPage", produces = "application/json")
    public ResponseEntity<PageResponse<Product>> getAllProductsByPage(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        return ResponseEntity.ok().body(productService.getProductsByPage(pageNumber, pageSize, sortBy, sortDirection));
    }

    @ApiOperation(value = "Get All Categories", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllCategories", produces = "application/json")
    public ResponseEntity<Product.Category[]> getAllCategories() {
        return ResponseEntity.ok().body(Product.Category.values());
    }

    @ApiOperation(value = "Get All Shopping Malls", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllShoppingMalls", produces = "application/json")
    public ResponseEntity<List<ShoppingMall>> getAllShoppingMalls() {
        return ResponseEntity.ok().body(shoppingMallRepo.findAll());
    }

}
