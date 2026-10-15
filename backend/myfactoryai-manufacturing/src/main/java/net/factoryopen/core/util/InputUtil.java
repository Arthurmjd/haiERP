package net.factoryopen.core.util;

public class InputUtil {
    public static Integer extractInteger(Object obj){
        return (Integer) obj;
    }

    public static double extractDouble(Object obj){
        double result = 0;

        if (obj.getClass().equals(String.class)){
            result = Double.parseDouble((String)obj);
        }else if (obj.getClass().equals(Double.class)){
            result = ((Double)obj).doubleValue();
        }else if (obj.getClass().equals(Integer.class)){
            result = ((Integer)obj).doubleValue();
        }

        return result;
    }

    public static long extractLong(Object obj){
        long result = 0;

        if (obj.getClass().equals(String.class)){
            result = Long.parseLong((String)obj);
        }else if (obj.getClass().equals(Long.class)){
            result = ((Long)obj).longValue();
        }else if (obj.getClass().equals(Integer.class)){
            result = ((Integer)obj).longValue();
        }

        return result;
    }
}
