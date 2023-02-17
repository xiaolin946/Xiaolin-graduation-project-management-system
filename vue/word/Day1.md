import {ref,reactive} from 'vue'

ref 用于   基础类
reactive 用于 对象


###spring boot启动报错，no tcnative-1 in java.library.path, no libtcnative-1 in java.library.path
根据日志中显示的tomcat版本，去官网下载tomcat,解压后将bin目录下tcnative-1.dll 放到 C:\Windows\System32 目录下即可；

###使用java.sql.Timestamp对象承载datetime类型数据，需要注意时区转换使用java.time.LocalDateTime对象承载datetime类型数据，无需注意时区代码如下：
import java.sql.Timestamp;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private Timestamp time;
######或者：
import java.time.LocalDateTime;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private Timestamp time;

