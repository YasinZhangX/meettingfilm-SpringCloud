package com.yasin.meetingfilm.backend.film.services.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeActorsRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmInfoRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmsRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.FilmSaveReqVO;
import com.yasin.meetingfilm.backend.film.dao.entity.MoocFilmActorT;
import com.yasin.meetingfilm.backend.film.dao.entity.MoocFilmInfoT;
import com.yasin.meetingfilm.backend.film.dao.entity.MoocFilmT;
import com.yasin.meetingfilm.backend.film.dao.mapper.MoocActorTMapper;
import com.yasin.meetingfilm.backend.film.dao.mapper.MoocFilmActorTMapper;
import com.yasin.meetingfilm.backend.film.dao.mapper.MoocFilmInfoTMapper;
import com.yasin.meetingfilm.backend.film.dao.mapper.MoocFilmTMapper;
import com.yasin.meetingfilm.backend.film.services.IFilmService;
import com.yasin.meetingfilm.backend.utils.ToolUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Yasin Zhang
 */
@Service(value = "filmService")
public class FilmServiceImpl implements IFilmService {

    @Resource
    private MoocActorTMapper actorTMapper;

    @Resource
    private MoocFilmTMapper filmTMapper;

    @Resource
    private MoocFilmInfoTMapper filmInfoTMapper;

    @Resource
    private MoocFilmActorTMapper filmActorTMapper;

    @Override
    public IPage<DescribeActorsRespVO> describeActors(int nowPages, int pageSize) {
        // 查询演员列表
        return actorTMapper.describeActors(new Page<>(nowPages, pageSize));
    }

    @Override
    public IPage<DescribeFilmsRespVO> describeFilms(int nowPages, int pageSize) {
        return filmTMapper.describeFilms(new Page<>(nowPages, pageSize));
    }

    @Override
    public DescribeFilmInfoRespVO describeFilmInfoById(int filmId) throws CommonServiceException {
        try {
            return filmTMapper.describeFilmById(filmId);
        } catch (Exception e) {
            throw new CommonServiceException(500, e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFilm(FilmSaveReqVO reqVO) throws CommonServiceException {
        try {
            // 保存电影主表
            MoocFilmT film = new MoocFilmT();
            film.setFilmName(reqVO.getFilmName());
            film.setFilmType(ToolUtils.str2Int(reqVO.getFilmTypeId()));
            film.setImgAddress(reqVO.getMainImgAddress());
            film.setFilmScore(reqVO.getFilmScore());
            film.setFilmPresalenum(ToolUtils.str2Int(reqVO.getPreSaleNum()));
            film.setFilmBoxOffice(ToolUtils.str2Int(reqVO.getBoxOffice()));
            film.setFilmSource(ToolUtils.str2Int(reqVO.getFilmSourceId()));
            film.setFilmCats(reqVO.getFilmCatIds());
            film.setFilmArea(ToolUtils.str2Int(reqVO.getAreaId()));
            film.setFilmDate(ToolUtils.str2Int(reqVO.getDateId()));
            film.setFilmTime(ToolUtils.str2LocalDateTime(reqVO.getFilmTime()+" 00:00:00"));
            film.setFilmStatus(ToolUtils.str2Int(reqVO.getFilmStatus()));

            filmTMapper.insert(film);

            // 保存电影子表
            MoocFilmInfoT filmInfo = new MoocFilmInfoT();

            filmInfo.setFilmId(film.getUuid()+"");
            filmInfo.setFilmEnName(reqVO.getFilmEnName());
            filmInfo.setFilmScore(reqVO.getFilmScore());
            filmInfo.setFilmScoreNum(ToolUtils.str2Int(reqVO.getFilmScorers()));
            filmInfo.setFilmLength(ToolUtils.str2Int(reqVO.getFilmLength()));
            filmInfo.setBiography(reqVO.getBiography());
            filmInfo.setDirectorId(ToolUtils.str2Int(reqVO.getDirectorId()));
            filmInfo.setFilmImgs(reqVO.getFilmImgs());


            filmInfoTMapper.insert(filmInfo);

            String[] actorId = reqVO.getActIds().split("#");
            String[] roleNames = reqVO.getRoleNames().split("#");
            if(actorId.length != roleNames.length){
                throw new CommonServiceException(500, "演员和角色名数量不匹配");
            }

            for(int i=0;i<actorId.length;i++){
                // 保存演员映射表
                MoocFilmActorT filmActor = new MoocFilmActorT();

                filmActor.setFilmId(film.getUuid());
                filmActor.setActorId(ToolUtils.str2Int(actorId[i]));
                filmActor.setRoleName(roleNames[i]);


                filmActorTMapper.insert(filmActor);
            }
        } catch (Exception e) {
            throw new CommonServiceException(500, e.getMessage());
        }
    }
}
