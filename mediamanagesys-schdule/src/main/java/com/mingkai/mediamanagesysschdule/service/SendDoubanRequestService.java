package com.mingkai.mediamanagesysschdule.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dtstack.plat.lang.exception.BizException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mingkai.mappermodule.mapper.*;
import com.mingkai.mappermodule.model.Do.movie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-02 16:03
 */
@Service
public class SendDoubanRequestService {

    @Autowired
    private MovieDetailMapper movieDetailMapper;

    @Autowired
    private MovieCastMapper movieCastMapper;

    @Autowired
    private MovieDirectorMapper movieDirectorMapper;

    @Autowired
    private MovieWriterMapper movieWriterMapper;

    @Autowired
    private MovieBlooperMapper movieBlooperMapper;

    @Autowired
    private MovieTrailerMapper movieTrailerMapper;

    @Autowired
    private MovieRankMapper movieRankMapper;

    /**
     * 豆瓣电影本周口碑榜
     */
    private final  String WeekRankUrl = "https://api.douban.com/v2/movie/weekly?apikey=0b2bdeda43b5688921839c8ecb20399b";

    /**
     * 北美票房榜
     */
    private final  String NorthAmericaRankUrl = "https://api.douban.com/v2/movie/us_box?apikey=0b2bdeda43b5688921839c8ecb20399b";

    /**
     * 新片榜
     */
    private final  String NewRankUrl = "https://api.douban.com/v2/movie/new_movies?apikey=0b2bdeda43b5688921839c8ecb20399b";

    /**
     * 正在热映
     */
    private String nowRankUrl = "https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b";

    /**
     * 即将上映
     */
    private String ComingRankUrl = "https://api.douban.com/v2/movie/coming_soon?apikey=0b2bdeda43b5688921839c8ecb20399b";

    /**
     * TOP250
     */
    private String Top250Url = "https://api.douban.com/v2/movie/top250?apikey=0b2bdeda43b5688921839c8ecb20399b";


    protected Map sentHttpRequest(String url){
        RestTemplate restTemplate = new RestTemplate();
        Map map=restTemplate.getForObject(url,Map.class);
        return map;
    }


    /**
     * 清空表数据
     * @param tableName
     * @return
     */
    public Boolean clearData(String tableName){
       return movieRankMapper.dynamicSqlExcute(tableName);
    }

    /**
     * 保存到榜单
     * @param tableName
     * @param movieId
     * @return
     */
    public Boolean saveRank(String tableName,String movieId){

        //先要 插入到详情表
        MovieDetailDo movie = getModelFromApi(movieId);

        save(movieId,movie);

        // 然后插入到tableName

        // 跑个定时任务 每日将6个榜单的数据清空

        //这个插入的 定时任务在 清空之后跑

        return  movieRankMapper.insertIntoTable(tableName,movie);

    }


    /**
     * 周榜查询ids
     * @return
     */
    public List<String> weekRankQuery(){
        List<String> weekRankMovieIds = Lists.newArrayList();

        Map weekRankMap = this.sentHttpRequest(this.WeekRankUrl);

        List<Map> weekRankList = ((List)weekRankMap.get("subjects"));

        for (Map map : weekRankList) {
            weekRankMovieIds.add(((Map)map.get("subject")).get("id").toString());
        }

        return weekRankMovieIds;
    }


    /**
     * 北美票房榜
     * @return
     */
    public List<String> northAmericaRankQuery(){
        List<String> northAmericaRankMovieIds = Lists.newArrayList();

        Map northAmericaRankMap = this.sentHttpRequest(this.NorthAmericaRankUrl);

        List<Map> northAmericaRankList = (List<Map>) northAmericaRankMap.get("subjects");

        for (Map map : northAmericaRankList) {
            northAmericaRankMovieIds.add(((Map)map.get("subject")).get("id").toString());
        }

        return northAmericaRankMovieIds;
    }


    /**
     * 新片榜
     * @return
     */
    public List<String> newRankQuery(){
        List<String> newRankMovieIds = Lists.newArrayList();

        Map newRankMap = this.sentHttpRequest(this.NewRankUrl);

        List<Map> newRankList = (List<Map>) newRankMap.get("subjects");

        for (Map map : newRankList) {
            newRankMovieIds.add(map.get("id").toString());
        }

        return newRankMovieIds;
    }


    /**
     * 不带城市的 查询
     *  * 4 豆瓣电影本周口碑榜
     *          * https://api.douban.com/v2/movie/weekly?apikey=0b2bdeda43b5688921839c8ecb20399b
     *          *
     *          * 5 北美票房榜
     *          * https://api.douban.com/v2/movie/us_box?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=
     *          *
     *          * 6 新片榜
     *          * https://api.douban.com/v2/movie/new_movies?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=
     * @return
     */
    public List<String> noCityQuery(){

        List<String> weekRankMovieIds = Lists.newArrayList();

        List<String> northAmericaRankMovieIds = Lists.newArrayList();

        List<String> newRankMovieIds = Lists.newArrayList();

        Map weekRankMap = this.sentHttpRequest(this.WeekRankUrl);

        Map northAmericaRankMap = this.sentHttpRequest(this.NorthAmericaRankUrl);

        Map newRankMap = this.sentHttpRequest(this.NewRankUrl);


        List<Map> weekRankList = ((List)weekRankMap.get("subjects"));

        for (Map map : weekRankList) {
            weekRankMovieIds.add(((Map)map.get("subject")).get("id").toString());
        }

        List<Map> northAmericaRankList = (List<Map>) northAmericaRankMap.get("subjects");

        for (Map map : northAmericaRankList) {
            northAmericaRankMovieIds.add(((Map)map.get("subject")).get("id").toString());
        }

        List<Map> newRankList = (List<Map>) newRankMap.get("subjects");

        for (Map map : newRankList) {
            newRankMovieIds.add(map.get("id").toString());
        }


        List<String> allMovieIds = Stream.concat(
                Stream.concat(weekRankMovieIds.stream(), northAmericaRankMovieIds.stream()),
                newRankMovieIds.stream()).collect(Collectors.toList());

        Set<String> noRepeatIds = new HashSet<>(allMovieIds);

        //查询movie_detail 再次进行去重

        List<String> RepeatList = movieDetailMapper.selectIdsIn(new ArrayList<>(noRepeatIds));

        noRepeatIds.removeAll(RepeatList);

        return Lists.newArrayList(noRepeatIds);

    }


    /**
     * 正在热映
     * @param city
     * @param start
     * @param count
     * @return
     */
    public List<String> nowRankQuery(String city,int start,int count){
        List<String> nowRankMovieIds = Lists.newArrayList();

        Map nowRankMap = this.sentHttpRequest(nowRankUrl + "&city=" + city + "&start=" + start + "&count=" + count);

        List<Map> nowRankList = ((List)nowRankMap.get("subjects"));

        for (Map map : nowRankList) {
            nowRankMovieIds.add((map.get("id")).toString());
        }

        return nowRankMovieIds;
    }

    /**
     * 即将上映
     * @param city
     * @param start
     * @param count
     * @return
     */
    public List<String> comingRankQuery(String city,int start,int count){
        List<String> comingRankMovieIds = Lists.newArrayList();

        Map comingRankMap = this.sentHttpRequest(ComingRankUrl + "&city=" + city + "&start=" + start + "&count=" + count);

        List<Map> comingRankList = (List<Map>) comingRankMap.get("subjects");

        for (Map map : comingRankList) {
            comingRankMovieIds.add((map.get("id")).toString());
        }

        return comingRankMovieIds;
    }

    /**
     * TOP250
     * @param city
     * @param start
     * @param count
     * @return
     */
    public List<String> top250RankQuery(String city,int start,int count){
        List<String> top250MovieIds = Lists.newArrayList();

        Map top250Map = this.sentHttpRequest(Top250Url + "&city=" + city + "&start=" + start + "&count=" + count);

        List<Map> top250List = (List<Map>) top250Map.get("subjects");

        for (Map map : top250List) {
            top250MovieIds.add(map.get("id").toString());
        }

        return top250MovieIds;
    }

    /**
     * 带城市和分页的查询
     * 1 正在热映
     * https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=
     *
     * 2 即将上映
     * https://api.douban.com/v2/movie/coming_soon?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=
     *
     * 3 TOP250
     * https://api.douban.com/v2/movie/top250?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=
     *
     * @param city
     * @param start
     * @param count
     * @return
     */
    public List<String> cityAndPageQuery(String city,int start,int count){

        List<String> nowRankMovieIds = Lists.newArrayList();

        List<String> comingRankMovieIds = Lists.newArrayList();

        List<String> top250MovieIds = Lists.newArrayList();

        Map nowRankMap = this.sentHttpRequest(nowRankUrl + "&city=" + city + "&start=" + start + "&count=" + count);

        Map comingRankMap = this.sentHttpRequest(ComingRankUrl + "&city=" + city + "&start=" + start + "&count=" + count);

        Map top250Map = this.sentHttpRequest(Top250Url + "&city=" + city + "&start=" + start + "&count=" + count);

        List<Map> nowRankList = ((List)nowRankMap.get("subjects"));

        for (Map map : nowRankList) {
            nowRankMovieIds.add((map.get("id")).toString());
        }

        List<Map> comingRankList = (List<Map>) comingRankMap.get("subjects");

        for (Map map : comingRankList) {
            comingRankMovieIds.add((map.get("id")).toString());
        }

        List<Map> top250List = (List<Map>) top250Map.get("subjects");

        for (Map map : top250List) {
            top250MovieIds.add(map.get("id").toString());
        }


        List<String> allMovieIds = Stream.concat(
                Stream.concat(nowRankMovieIds.stream(), comingRankMovieIds.stream()),
                top250MovieIds.stream()).collect(Collectors.toList());

        Set<String> noRepeatIds = new HashSet<>(allMovieIds);

        //查询movie_detail 再次进行去重

        List<String> repeatList = movieDetailMapper.selectIdsIn(new ArrayList<>(noRepeatIds));

        noRepeatIds.removeAll(repeatList);

        return Lists.newArrayList(noRepeatIds);

    }

    public Boolean saveBatch(String city,int start,int count){

        /**
         *
         * 根据id 查找
         * https://api.douban.com/v2/movie/subject/30164448?apikey=0b2bdeda43b5688921839c8ecb20399b
         *
         * 1 正在热映
         * https://api.douban.cm/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=
         *
         * 2 即将上
         * https://api.douban.com/v2/movie/coming_soon?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=
         *
         * 3 TOP250
         * https://api.douban.com/v2/movie/top250?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=
         *
         * 4 豆瓣电影本周口碑榜
         * https://api.douban.com/v2/movie/weekly?apikey=0b2bdeda43b5688921839c8ecb20399b
         *
         * 5 北美票房榜
         * https://api.douban.com/v2/movie/us_box?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=
         *
         * 6 新片榜
         * https://api.douban.com/v2/movie/new_movies?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=
         *
         */

        try{
            List<String> ids = this.noCityQuery();
            ids.addAll(this.cityAndPageQuery(city,start,count));

            Set<String> noRepeatSet = Sets.newHashSet(ids);

            ArrayList<String> noRepeatList = Lists.newArrayList(noRepeatSet);

            for (String id : noRepeatList) {
                save(id,getModelFromApi(id));
            }
        }catch (Exception e){
            throw new BizException("未知错误",e);
        }


        return true;
    }


    /**
     * 保存到数据库
     * @param movieId
     * @return
     */
    public Boolean save(String movieId,MovieDetailDo movieDetailDo){

        // id 首先去查找 电影详情表中是否已经存在 当前记录
        MovieDetailDo movieDetail = movieDetailMapper.selectOne(new QueryWrapper<MovieDetailDo>()
        .eq("movie_id",movieId));

        if (null != movieDetail){
            ///throw new BizException(String.format("库中已经存在-id: {}  ==> 记录",movieId));
            return false;
        }

        return 1 == movieDetailMapper.insert(movieDetailDo);
    }


    public MovieDetailDo getModelFromApi(String movieId){

        Map map = sentHttpRequest("https://api.douban.com/v2/movie/subject/"+movieId+"?apikey=0b2bdeda43b5688921839c8ecb20399b");

        // 否则  插入
        MovieDetailDo movieDetail = new MovieDetailDo();
        movieDetail.setMovieId(movieId);
        // 根据key 来取值 eg : rating -> 下面也是一个LinkedHashMap  取 average 为评分

        String average = ((Map)map.get("rating")).get("average").toString();
        movieDetail.setRating(average);

        // genres  --> List类别
        List<String> genresList = ((List)map.get("genres"));

        movieDetail.setGenres(String.join(",",genresList));

        // title
        String title = map.get("title").toString();

        movieDetail.setMovieName(title);

        // casts --> List --> LinkedHashMap  name_en name  avatars--> map --> small/large / medium
        List<Map> castList = (List)map.get("casts");

        List<String> castIds = Lists.newArrayList();

        for (Map cast:castList){

            if (null == cast.get("id")){
                continue;
            }
            String id = cast.get("id").toString();

            castIds.add(id);

            //寻找是否有这个主演记录
            MovieCastDo actor = movieCastMapper.selectOne(new QueryWrapper<MovieCastDo>()
                    .eq("actor_id", id));

            if (null != actor){
                continue;
            }

            actor = new MovieCastDo();

            actor.setActorId(id);

            String nameEn = cast.get("name_en").toString();
            actor.setActorNameEn(nameEn);

            String name = cast.get("name").toString();
            actor.setActorName(name);

            String smallImage = ((Map) cast.get("avatars")).get("small").toString();
            actor.setActorImage(smallImage);

            movieCastMapper.insert(actor);

        }

        //cast
        movieDetail.setCasts(String.join(",",castIds));

        //original_title
        String originalTitle = map.get("original_title").toString();
        movieDetail.setOriginalName(originalTitle);

        // directors --> Map --> name_en name avatars --> map --> small ...
        List<Map> directorList = (List)map.get("directors");

        List<String> directorIds = Lists.newArrayList();

        for (Map director:directorList){
            if (null == director.get("id")){
                continue;
            }
            String id = director.get("id").toString();

            directorIds.add(id);

            MovieDirectorDo movieDirector = movieDirectorMapper.selectOne(new QueryWrapper<MovieDirectorDo>()
                    .eq("director_id", id));

            //已经存在导演记录
            if (null != movieDirector){
                continue;
            }

            movieDirector = new MovieDirectorDo();

            movieDirector.setDirectorId(id);

            String nameEn = director.get("name_en").toString();
            movieDirector.setDirectorNameEn(nameEn);

            String name = director.get("name").toString();
            movieDirector.setDirectorName(name);

            String smallImage = ((Map) director.get("avatars")).get("small").toString();
            movieDirector.setDirectorImage(smallImage);

            movieDirectorMapper.insert(movieDirector);

        }

        movieDetail.setDirectors(String.join(",",directorIds));

        // pubdates --> list --> 多个

        List<String> pubDateList = (List)map.get("pubdates");
        movieDetail.setPubdates(String.join(",",pubDateList));

        // year
        String year = map.get("year").toString();
        movieDetail.setYear(year);

        // images  --> map --> small/large/medium
        String smallImage = ((Map) map.get("images")).get("small").toString();
        movieDetail.setImage(smallImage);

        // languages --> list 取全部语言
        List<String> languages = (List<String>) map.get("languages");
        movieDetail.setLanguages(String.join(",",languages));


        // writers --> list -->  name_en name avatars --> map -->  small/large/medium
        List<Map> writers = (List)map.get("writers");

        List<String> writerIds = Lists.newArrayList();

        for (Map writer:writers){
            if (null == writer.get("id")){
                continue;
            }
            String id = writer.get("id").toString();

            writerIds.add(id);

            MovieWriterDo movieWriter = movieWriterMapper.selectOne(new QueryWrapper<MovieWriterDo>()
                    .eq("writer_id", id));

            if (null != movieWriter){
                continue;
            }

            movieWriter = new MovieWriterDo();

            movieWriter.setWriterId(id);

            String nameEn = writer.get("name_en").toString();
            movieWriter.setWriterNameEn(nameEn);

            String name = writer.get("name").toString();
            movieWriter.setWriterName(name);

            String image = ((Map) writer.get("avatars")).get("small") == null ? "" : ((Map) writer.get("avatars")).get("small").toString();
            movieWriter.setWriterImage(image);

            movieWriterMapper.insert(movieWriter);
        }

        movieDetail.setWriters(String.join(",",writerIds));


        // tags --> 标签 List  取全部

        List<String> tags = (List)map.get("tags");
        movieDetail.setTags(String.join(",",tags));

        // durations 时长 list get(0)
        String duration = ((List) map.get("durations")).size() == 0 ? "" : ((List) map.get("durations")).get(0).toString();
        movieDetail.setDuration(duration);


        // trailers 预告片数组 List --> map -->  title small/medium resource_url
        List<Map> trailers = (List)map.get("trailers");
        for (Map trailer : trailers) {

            MovieTrailerDo movieTrailerDo = new MovieTrailerDo();
            movieTrailerDo.setMovieId(movieId);

            String title1 = trailer.get("title").toString();
            movieTrailerDo.setTrailerTitle(title1);

            String small = trailer.get("small").toString();
            movieTrailerDo.setTrailerImage(small);

            String resourceUrl = trailer.get("resource_url").toString();
            movieTrailerDo.setTrailerResourceUrl(resourceUrl);

            movieTrailerMapper.insert(movieTrailerDo);
        }


        // bloopers 花絮数组 List --> Map --> title small/medium resource_url
        List<Map> bloopers = (List)map.get("bloopers");

        for (Map blooper : bloopers) {

            MovieBlooperDo movieBlooperDo = new MovieBlooperDo();
            movieBlooperDo.setMovieId(movieId);

            String title1 = blooper.get("title").toString();
            movieBlooperDo.setBlooperTitle(title1);

            String small = blooper.get("small").toString();
            movieBlooperDo.setBlooperImage(small);

            String resource_url = blooper.get("resource_url").toString();
            movieBlooperDo.setBlooperResourceUrl(resource_url);

            movieBlooperMapper.insert(movieBlooperDo);
        }


        // countries 国家/地区数组 List
        List<String> countries = (List)map.get("countries");
        movieDetail.setCountries(String.join(",",countries));

        //  summary
        String summary = map.get("summary").toString();
        movieDetail.setSummary(summary);

        // ratings_count 评分人数
        String ratings_count = map.get("ratings_count").toString();
        movieDetail.setRatingsCount(new Integer(ratings_count));

        // aka 别名
        List<String> akaList = (List)map.get("aka");
        movieDetail.setAnotherName(String.join(",",akaList));

        return movieDetail;
    }

}
