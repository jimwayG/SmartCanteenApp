# 智慧食堂前端部分

相配合的[后端部分](https://github.com/APeng215/SmartCanteenBackend)，[智慧识别部分](https://github.com/AlearXS/SmartCanteenDetection)。

现在食堂数据全部从后端获取，想要食堂界面显示内容，请确保后端已启动。后端具体部署方法参见后端 README。

## 配置项

配置后端 API 地址，这个地址被前端用于请求后端服务。

```java
public interface CanteenService {

    // http://10.0.2.2:8081/ 用于开发环境。10.0.2.2 为模拟器运行环境地址（比如运行模拟器的电脑）
    // http://aaapeng.tpddns.cn:8081/ 用于生产环境。aaapeng.tpddns.cn:8081 为服务器 API 地址
    String BASE_URL = "http://aaapeng.tpddns.cn:8081/";
...
```