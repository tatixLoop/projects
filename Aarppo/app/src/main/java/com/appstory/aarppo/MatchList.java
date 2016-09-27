package com.appstory.aarppo;

import android.util.Log;

/**
 * Created by jithin suresh on 27-09-2016.
 */
public class MatchList {





    String name,id;
    String date, team1, team2;


    String description;

    int img;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public  void setName(String name){
        this.name=name;


    }


    public  void setDescription(String description)
    {
        this.description=description;
    }
    public String getName(){
        return name;

    }

    public String getTeam1()
    {

        return team1;
    }

    public String getTeam2()
    {

        return team2;
    }
    public void setTeam1(String teamName)
    {
        this.team1 = teamName;

    }

    public void setTeam2(String teamName)
    {
        this.team2 = teamName;

    }

    public String getDescription() {
        return description;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }



}
