package com.consultoraestrategia.ss_portalalumno.util;

import com.google.gson.JsonPrimitive;

public class UtilsFirebase {

    public static <T extends Comparable<T>>T convert(Object o,T convert){
        try {
            if(o==null){
                return convert;
            }else {
                if(convert instanceof Integer){
                    if(o.getClass().equals(String.class)){
                        return (T)Integer.valueOf((String) o);
                    }else if(o.getClass().equals(Long.class)){
                        return (T)Integer.valueOf(((Long) o).intValue());
                    }else if(o.getClass().equals(Float.class)){
                        return (T)Integer.valueOf(((Float) o).intValue());
                    }else if(o.getClass().equals(Double.class)){
                        return (T)Integer.valueOf(((Double) o).intValue());
                    }else if(o.getClass().equals(Boolean.class)){
                        if(o.equals(true)){
                            return (T)Integer.valueOf(1);
                        }else {
                            return (T)Integer.valueOf(0);
                        }
                    }else if(o.getClass().equals(JsonPrimitive.class)){
                        return (T)Integer.valueOf(((JsonPrimitive)o).getAsInt());
                    }else {
                        return (T)o;
                    }


                }else if(convert.getClass().equals(Long.class)){
                    if(o.getClass().equals(String.class)){
                        return (T)Long.valueOf((String) o);
                    }else if(o.getClass().equals(Integer.class)){
                        return (T)Long.valueOf(((Integer)o));
                    }else if(o.getClass().equals(Float.class)){
                        return (T)Long.valueOf(((Float) o).longValue());
                    }else if(o.getClass().equals(Double.class)){
                        return (T)Long.valueOf(((Double) o).longValue());
                    }else if(o.getClass().equals(Boolean.class)){
                        if(o.equals(true)){
                            return (T)Long.valueOf(1);
                        }else {
                            return (T)Long.valueOf(0);
                        }
                    }else if(o.getClass().equals(JsonPrimitive.class)){
                        return (T)Long.valueOf(((JsonPrimitive)o).getAsLong());
                    }else {
                        return (T)o;
                    }
                }else if(convert.getClass().equals(Float.class)){
                    if(o.getClass().equals(String.class)){
                        return (T)Float.valueOf((String) o);
                    }else if(o.getClass().equals(Integer.class)){
                        return (T)Float.valueOf(((Integer)o).floatValue());
                    }else if(o.getClass().equals(Double.class)){
                        return (T)Float.valueOf(((Double) o).floatValue());
                    }else if(o.getClass().equals(Long.class)){
                        return (T)Float.valueOf(((Long)o).floatValue());
                    }else if(o.getClass().equals(JsonPrimitive.class)){
                        return (T)Float.valueOf(((JsonPrimitive)o).getAsFloat());
                    }else {
                        return (T)o;
                    }
                }else if(convert.getClass().equals(Double.class)){
                    if(o.getClass().equals(String.class)){
                        return (T)Double.valueOf((String) o);
                    }else if(o.getClass().equals(Integer.class)){
                        return (T)Double.valueOf(((Integer)o).doubleValue());
                    }else if(o.getClass().equals(Long.class)){
                        return (T)Double.valueOf(((Long)o).doubleValue());
                    }else if(o.getClass().equals(Float.class)){
                        return (T)Double.valueOf(((Float) o).doubleValue());
                    }else if(o.getClass().equals(JsonPrimitive.class)){
                        return (T)Double.valueOf(((JsonPrimitive)o).getAsDouble());
                    }else {
                        return (T)o;
                    }
                }else if(convert.getClass().equals(String.class)){
                    if(o.getClass().equals(JsonPrimitive.class)){
                        return (T)String.valueOf(((JsonPrimitive)o).getAsString());
                    }else {
                        return (T)(o.toString());
                    }
                }else if(convert.getClass().equals(Boolean.class)){
                    if(o.getClass().equals(String.class)){
                        if(o.equals("true")||o.equals("1")){
                            return (T)new Boolean(true);
                        }else {
                            return (T)new Boolean(false);
                        }
                    }else if(o.getClass().equals(Number.class)){
                        if(o.equals(1)||o.equals(1L)||o.equals(1F)){
                            return (T)new Boolean(true);
                        }else {
                            return (T)new Boolean(false);
                        }
                    }else if(o.getClass().equals(JsonPrimitive.class)){
                        return (T)new Boolean(((JsonPrimitive)o).getAsBoolean());
                    }else {
                        return (T)o;
                    }
                }else{
                    return null;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return convert;
        }
    }

}
