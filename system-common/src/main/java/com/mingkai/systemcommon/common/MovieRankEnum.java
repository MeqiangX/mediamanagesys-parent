package com.mingkai.systemcommon.common;

/**
 * @description: 榜单类型enum
 * @author: Created by 云风 on 2019-04-11 20:35
 */
public enum MovieRankEnum {

    HOT_RANK("0","正在热映","mesys_movie_hot_rank"),

    COMMING_RANK("1","即将上映","mesys_movie_comming_rank"),

    NEW_RANK("2","新片榜","mesys_movie_new_rank"),

    NORTH_RANK("3","北美票房榜","mesys_movie_north_rank"),

    TOP_250_RANK("4","top250榜单","mesys_movie_top250_rank"),

    WEEK_RANK("5","本周口碑榜","mesys_movie_week_rank");


    private String code;

    private String rankName;

    private String tableName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    MovieRankEnum(String code, String rankName, String tableName) {
        this.code = code;
        this.rankName = rankName;
        this.tableName = tableName;
    }

    public static MovieRankEnum getMovieRankEnum(String code){
        for (MovieRankEnum movieRankEnum : MovieRankEnum.values()) {
            if (movieRankEnum.code.equals(code)){
                return movieRankEnum;
            }
        }
            return null;
    }
}
