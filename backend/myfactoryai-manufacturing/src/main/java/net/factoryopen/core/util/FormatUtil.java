package net.factoryopen.core.util;

import net.factoryopen.core.common.exception.FormatterConvertException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
    public static Date string2Date(String dateString) throws Exception{
        Date date = null;

        try
        {
            if (dateString!=null && dateString.trim()!=""){
                //若格式为yyyy/mm/dd
                dateString = dateString.replace("/","-");

                //若格式为yyyy年mm月dd日HH时mm分ss秒SSS毫秒
                //替换年月日
                dateString = dateString.replace("年","-");
                dateString = dateString.replace("月","-");
                dateString = dateString.replace("日","");

                //强制转换格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.parse(dateString);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new FormatterConvertException(ex);
        }

        return date;
    }

    public static Date string2DateTime(String dateString) throws Exception{
        Date date = null;

        try
        {
            if (dateString!=null && dateString.trim()!=""){
                //若格式为yyyy/mm/dd
                dateString = dateString.replace("/","-");

                //若格式为yyyy年mm月dd日HH时mm分ss秒SSS毫秒
                //替换年月
                dateString = dateString.replace("年","-");
                dateString = dateString.replace("月","-");
                dateString = dateString.replace("日"," ");
                dateString = dateString.replace("时",":");
                dateString = dateString.replace("分",":");
                dateString = dateString.replace("秒","");

                //强制转换格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = dateFormat.parse(dateString);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new FormatterConvertException(ex);
        }

        return date;
    }

    public static Date string2FullDateTime(String dateString) throws Exception{
        Date date = null;

        try
        {
            if (dateString!=null && dateString.trim()!=""){
                //若格式为yyyy/mm/dd
                dateString = dateString.replace("/","-");

                //若格式为yyyy年mm月dd日HH时mm分ss秒SSS毫秒
                //替换年月
                dateString = dateString.replace("年","-");
                dateString = dateString.replace("月","-");
                //替换日
                if (dateString.indexOf("时")>=0) dateString = dateString.replace("日"," ");
                else dateString = dateString.replace("日","");
                //替换时
                dateString = dateString.replace("时",":");
                //替换分
                if (dateString.indexOf("秒")>=0) dateString = dateString.replace("分",":");
                else dateString.replace("分","");
                //替换秒
                if (dateString.indexOf("毫秒")>=0) dateString = dateString.replace("秒",".");
                else dateString = dateString.replace("秒","");
                //替换毫秒
                dateString = dateString.replace("毫秒","");

                //强制转换格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                date = dateFormat.parse(dateString);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new FormatterConvertException(ex);
        }

        return date;
    }
}
