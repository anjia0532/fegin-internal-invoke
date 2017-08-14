package com.anjia0532.bar.web.rest.client;
/**
 * <b>描　　述</b>: TODO<br>
 * <b>文件名称</b>: FooFeignClientFallback.java<br>
 * <b>包　　名</b>: com.anjia0532.uaa.web.rest.client<br>
 * <b>创建时间</b>: 2017年8月14日 下午1:12:38<br> 
 * <b>修改时间</b>: <br> 
 *
 * @author SN_AnJia(anjia0532@qq.com)
 * @version 1.0
 * @since jdk 1.8
 */
public class FooFeignClientFallback implements FooFeignClient{

    @Override
    public String getCurrentLogin() {
        return "Fallback";
    }

}
