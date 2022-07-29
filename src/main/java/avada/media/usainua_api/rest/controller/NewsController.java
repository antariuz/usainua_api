package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.News;
import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.service.NewsService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = "news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "5";
    private final String DEFAULT_SORT_DIRECTION = "DESC";

    @ApiOperation(value = "Get All News", authorizations = @Authorization("accessToken"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Access token is missing"),
    })
    @GetMapping(value = "getAllNewsByPage", produces = "application/json")
    public ResponseEntity<PageResponse<News>> getAllNewsByPage(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        return ResponseEntity.ok().body(newsService.getAllNewsByPage(pageNumber, pageSize, sortDirection));
    }

}
