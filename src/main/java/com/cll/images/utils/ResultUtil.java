package com.cll.images.utils;



/**
 * @author chenliangliang
 * @date 2017/11/27
 */
public class ResultUtil {


    public static Result success(String msg, Object data){
        return new Result(true,msg,data);
    }

    public static Result success(Object data){
        return new Result(true,null,data);
    }


    public static Result fail(String msg){
        return new Result(false,msg,null);
    }

    /**
     * 查询数据异常
     * @return Result
     */
    public static Result selectError(){
        return fail("查询数据异常");
    }

    /**
     * 跟新数据异常
     * @return Result
     */
    public static Result updateError(){
        return fail("跟新数据异常");
    }

    /**
     * 删除数据异常
     * @return Result
     */
    public static Result deleteError(){
        return fail("删除数据异常");
    }

    /**
     * 插入数据异常
     * @return Result
     */
    public static Result insertError(){
        return fail("插入数据异常");
    }

    /**
     * 数据格式错误
     * @return Result
     */
    public static Result dataError(){
        return fail("数据格式错误");
    }

    /**
     * 未知错误
     * @return Result
     */
    public static Result unknownError(){
        return fail("未知错误");
    }

    /**
     * OK
     * @param data
     * @return Result
     */
    public static Result OK(Object data){
        return success("OK",data);
    }

    /**
     * OK
     * @return Result
     */
    public static Result OK(){
        return success("OK",null);
    }
}
