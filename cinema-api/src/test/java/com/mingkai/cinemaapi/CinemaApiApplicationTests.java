package com.mingkai.cinemaapi;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.cinemaapi.service.CinemaService;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mediamanagesysmapper.model.Vo.cinema.CinemaVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CinemaApiApplicationTests {

    @Autowired
    CinemaService cinemaService;

    @Test
    public void contextLoads() {
        CinemaSearchPo cinemaSearchPo = new CinemaSearchPo();
        cinemaSearchPo.setAreaId("110108");
        cinemaSearchPo.setMovieId("30272143");
        Page<CinemaVo> cinemaVoPage = cinemaService.searchCinemas(cinemaSearchPo);
        return ;
    }

}
