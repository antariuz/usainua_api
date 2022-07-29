package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.model.News;
import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.repository.NewsRepo;
import avada.media.usainua_api.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepo newsRepo;

    @Override
    public PageResponse<News> getAllNewsByPage(int pageNumber, int pageSize, String sortDirection) {
        Page<News> newsList = newsRepo.findAll(PageRequest.of(pageNumber, pageSize,
                sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by("id").descending()
                        : Sort.by("id").ascending()));
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
