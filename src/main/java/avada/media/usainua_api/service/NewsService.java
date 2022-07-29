package avada.media.usainua_api.service;

import avada.media.usainua_api.model.News;
import avada.media.usainua_api.model.PageResponse;

public interface NewsService {

    PageResponse<News> getAllNewsByPage(int pageNumber, int pageSize, String sortDirection);

}
