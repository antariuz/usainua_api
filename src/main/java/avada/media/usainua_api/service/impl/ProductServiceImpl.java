package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.model.Product;
import avada.media.usainua_api.repository.ProductRepo;
import avada.media.usainua_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public PageResponse<Product> getProductsByPage(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Page<Product> newsList = productRepo.findAll(PageRequest.of(pageNumber, pageSize,
                sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()));
        return new PageResponse<>(
                newsList.getContent(),
                newsList.getNumber(),
                newsList.getSize(),
                newsList.getTotalElements(),
                newsList.getTotalPages(),
                newsList.isLast()
        );
    }

}
