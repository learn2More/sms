package com.ppdai.ac.shardingJDBC.Utils;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xiejin on 2017/5/4.
 */
public class DateForMatUtil {

    /**
     * 理论上 一个月最多6周
     */
    private static final int MAX_WEEK_OF_MONTH=6;

    private static final int MONTH_PER_YEAR=12;


    /**
     * 获取传入时间的当前年份 格式{yyyy}
     * @param date
     * @return 返回传入时间当前年份的字符串
     */
    public static String getYear(Date date){
        return date==null?null:(new SimpleDateFormat("yyyy")).format(date);
    }

    public static List<String> getYearList(Integer startYear,Integer endYear){
        List<String> yearList=null;
        if(startYear!=null&&endYear!=null){
            yearList=new ArrayList<String>((endYear-startYear)>0?(endYear-startYear):1);
            yearList.add(startYear.toString());
            for(int i=0;i<(endYear-startYear);i++){
                yearList.add(String.valueOf(startYear+(i+1)));
            }
        }
        return yearList;
    }

/*    public static List<String> getYearList(Date startDate,Date endTime){
        List<String> yearList=null;
        if(startDate!=null&&endTime!=null){
            Integer startYear=Integer.parseInt(yearForMat.format(startDate));
            Integer endYear=Integer.parseInt(yearForMat.format(endTime));
            yearList=getYearList(startYear,endYear);
        }
        return yearList;
    }*/

/*    public static List<String> getYearListByStarTime(Date date){
        List<String> yearList=null;
        if(date!=null){
            Integer startYear=Integer.parseInt(yearForMat.format(date));
            Integer nowYear=new Date().getYear();
            yearList=new ArrayList<String>((nowYear-startYear)>0?(nowYear-startYear):1);
            yearList.add(startYear.toString());
            for(int i=0;i<(nowYear-startYear);i++){
                yearList.add(String.valueOf(startYear+(i+1)));
            }
        }
        return yearList;
    }*/


    /**
     * 获取传入时间的当前年份加月份 格式{yyyyMM}
     * @param date
     * @return
     */
    /*public static String getMonth(Date date){
        return date==null?null:monthForMat.format(date);
    }*/


    /**
     *
     * @param date
     * @return 月份+星期
     */
    public static String getMonthAndWeekStr(Date date){
        if(date==null)
            return  null;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int weekOfMonth=calendar.get(Calendar.WEEK_OF_MONTH);
        return String.format("%02d",calendar.get(Calendar.MONTH)+1)+String.format("%02d",weekOfMonth);
    }

/*    public static List<String> getMonthBetween(Date minDate, Date maxDate)  {
        ArrayList<String> result = new ArrayList<String>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(monthForMat.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }*/

   /* public static List<String> getWeekOfMonthBetween(Date minDate, Date maxDate)  {
        ArrayList<String> result = new ArrayList<String>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        ArrayList<String> monthResult = new ArrayList<String>();
        while (curr.before(max)) {
            monthResult.add(monthForMat.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        for(String month:monthResult){
            for(int week=1;week<=MAX_WEEK_OF_MONTH;week++){
                String weekTable=month+String.format("%02d",week);
                if(!result.contains(weekTable))
                    result.add(month+String.format("%02d",week));
            }
        }
        return result;
    }*/


    public static List<String> getBetweenMonthAndWeekStr(Date start, Date end)  {
        List<String> result= Lists.newArrayList();
        Calendar before = Calendar.getInstance();
        before.setTime(start);

        Calendar after = Calendar.getInstance();
        after.setTime(end);

        int yearGap=after.get(Calendar.YEAR)-before.get(Calendar.YEAR);
        if(yearGap>0){//跨年
            int beforeMonth=before.get(Calendar.MONTH)+1;
            int afterMonth=after.get(Calendar.MONTH)+1;
            int monthGap=MONTH_PER_YEAR*yearGap+(afterMonth-beforeMonth)+1;
            if(monthGap>=MONTH_PER_YEAR){//跨度超过12个月,取一年中的所有月的所有星期
                for(int i=1;i<=MONTH_PER_YEAR;i++){
                    for(int j=1;j<=MAX_WEEK_OF_MONTH;j++){
                        result.add(String.format("%02d",i)+String.format("%02d",j));
                    }
                }
            }else{//跨度少于12个月
                int literalMonthGap=afterMonth+12-beforeMonth;
                for(int i=0;i<=literalMonthGap;i++){
                    int month=(beforeMonth+i>12)?(beforeMonth+i-12):(beforeMonth+i);
                    for(int j=1;j<=MAX_WEEK_OF_MONTH;j++){
                        result.add(String.format("%02d",month)+String.format("%02d",j));
                    }

                }
            }
        }else{//同一年份
            int currYear=before.get(Calendar.YEAR);
            int beforeMonth=before.get(Calendar.MONTH);
            int beforeWeek=before.get(Calendar.WEEK_OF_MONTH);
            int afterMonth=after.get(Calendar.MONTH);
            int afterWeek=after.get(Calendar.WEEK_OF_MONTH);
            int monthGap=afterMonth-beforeMonth;
            if(monthGap>=1){//跨月
                Calendar temp=Calendar.getInstance();
                temp.set(Calendar.YEAR,currYear);
                temp.set(Calendar.MONTH,beforeMonth);
                //起始月
                for(int i=beforeWeek;i<=temp.getActualMaximum(Calendar.WEEK_OF_MONTH);i++){
                    result.add(String.format("%02d",beforeMonth+1)+String.format("%02d",i));
                }
                //中间间隔月
                for(int i=beforeMonth+1;i<afterMonth;i++){
                    temp.set(Calendar.YEAR,currYear);
                    temp.set(Calendar.MONTH,i);
                    for(int j=1;j<=temp.getActualMaximum(Calendar.WEEK_OF_MONTH);j++){
                        result.add(String.format("%02d",i+1)+String.format("%02d",j));
                    }
                }
                //终止月
                for(int i=1;i<=afterWeek;i++){
                    result.add(String.format("%02d",afterMonth+1)+String.format("%02d",i));
                }
            }else{
                for(int i=beforeWeek;i<=afterWeek;i++){
                    result.add(String.format("%02d",beforeMonth+1)+String.format("%02d",i));
                }
            }
        }



        return result;
    }
}
