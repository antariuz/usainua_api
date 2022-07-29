package avada.media.usainua_api.service;

import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.model.Product;

public interface ProductService {

    PageResponse<Product> getProductsByPage(int pageNumber, int pageSize, String sortBy, String sortDirection);

}
