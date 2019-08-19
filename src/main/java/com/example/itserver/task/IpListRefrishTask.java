package com.example.itserver.task;

import com.example.itserver.utils.ConnectUtil;
import com.example.itserver.utils.FileUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class IpListRefrishTask {

    /**
     * 每10分钟定时获取最新IP列表
     */
    @Scheduled(cron="0 */10 * * * ?")
    private void process(){
        //获取license
        String license = FileUtil.getLicense();
        try {
            ConnectUtil.getPostHttpConn("http://" + "xxxxx"
                    + ":8888/gunsApi/listdevice?license=" + license);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
