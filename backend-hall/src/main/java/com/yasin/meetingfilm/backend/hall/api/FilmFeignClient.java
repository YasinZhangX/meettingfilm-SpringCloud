package com.yasin.meetingfilm.backend.hall.api;

import com.yasin.meetingfilm.backend.apis.film.FilmFeignService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Yasin Zhang
 */
@FeignClient(name = "film-service", path = "/films")
public interface FilmFeignClient extends FilmFeignService {
}
