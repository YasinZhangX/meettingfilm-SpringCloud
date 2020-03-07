package com.yasin.meetingfilm.backend.testNG.film;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import com.yasin.meetingfilm.backend.testNG.common.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Yasin Zhang
 */
@Slf4j
public class FilmTest {

    @Test
    public void addFilm() {
        String url = "http://localhost:7002/films/film:add";
        RestTemplate restTemplate = RestUtils.restTemplate();

        FilmSaveReqVO filmSaveReqVO = new FilmSaveReqVO();
        filmSaveReqVO.setFilmStatus("1");
        filmSaveReqVO.setFilmName("123");
        filmSaveReqVO.setFilmEnName("123");
        filmSaveReqVO.setMainImgAddress("films/123.jpg");
        filmSaveReqVO.setFilmScore("10.0");
        filmSaveReqVO.setFilmScorers("123456");
        filmSaveReqVO.setPreSaleNum("123456");
        filmSaveReqVO.setBoxOffice("123456");
        filmSaveReqVO.setFilmTypeId("1");
        filmSaveReqVO.setFilmSourceId("1");
        filmSaveReqVO.setFilmCatIds("#1#2#3#");
        filmSaveReqVO.setAreaId("1");
        filmSaveReqVO.setDateId("13");
        filmSaveReqVO.setFilmTime("2020-07-05");
        filmSaveReqVO.setDirectorId("1");
        filmSaveReqVO.setActIds("7#8#9");
        filmSaveReqVO.setRoleNames("1#2#3");
        filmSaveReqVO.setFilmLength("132");
        filmSaveReqVO.setBiography("...");
        filmSaveReqVO.setFilmImgs("films/1.jpg");

        ResponseEntity<BaseResponseVO> response = restTemplate.postForEntity(url, filmSaveReqVO, BaseResponseVO.class);
        log.info("response:{}", response);
        Assert.assertEquals(response.getStatusCodeValue(), 200);
    }

    @Test(dataProvider = "filmDataProvider")
    public void films(String filmName, Integer expectCount) {
        String url = "http://localhost:7002/films";
        RestTemplate restTemplate = RestUtils.restTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = JSONObject.parseObject(response);

        Integer num = jsonObject.getJSONObject("data").getInteger("totalSize");
        if (num < 1) {
            throw new RuntimeException("error");
        }

        Integer count = 0;
        JSONArray films = jsonObject.getJSONObject("data").getJSONArray("films");
        List<DescribeFilmsRespVO> list =  films.toJavaList(DescribeFilmsRespVO.class);
        for (DescribeFilmsRespVO vo : list) {
            if (vo.getFilmEnName().equals(filmName)) {
                count++;
            }
        }
        log.info("count:{}", count);

        Assert.assertEquals(count, expectCount);
    }

    @DataProvider(name = "filmDataProvider")
    public Object[][] filmDataProvider() {
        Object[][] objects = new Object[][] {
            {"123", 1},
            {"123456", 0}
        };

        return objects;
    }

}
