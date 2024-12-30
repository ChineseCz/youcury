Youcry-模拟支付系统
=========
## 内容列表

- [背景](##背景)
- [安装](##安装)
- [使用说明](##使用说明)
- [成果](##成果)

## 背景
基于河畔校园论坛，为工作室模拟扩展实现一个支付系统，为同学们购买校园周边以及二手交易提供一个便利的平台。


## 安装
1. 克隆该仓库：
   ```bash
   git clone https://github.com/Chinesecz/my-java-project.git
   ```
2. 安装依赖项（使用 Maven）：
   ```bash
   mvn clean install
   docker-compose up -d
   ```


## 使用说明
主要源码目录简介：     
```
youcury  
├─web -- 内网接口  
│  ├─Config  -- 配置类初始化  
│  ├─Controller -- 微信登录、支付宝访问入口  
|  ├─job  -- 定时任务  
|  ├─listener  -- 监听  
|  ├─dto  -- 传输对象  
├─service -- 业务服务代码  
|  ├─weixin  -- 微信接口对接  
|  ├─impl   -- 订单业务实现   
|  ├─redis   -- redis服务  
├─domain  -- 一些实体、请求、响应对象  
├─common -- 一些公共的方法  
```

![image](https://github.com/user-attachments/assets/9b3fee45-9e01-4f46-b08e-29accae3e4d0)



## 效果展示
![image](https://github.com/user-attachments/assets/040eb1d8-fc20-4e10-b22b-f6cf9db9fb8c)


![8cdab9a18a6588966195b73d2e298a8](https://github.com/user-attachments/assets/4c24510d-b35c-4ab3-8edb-086e18d9b016)

![image](https://github.com/user-attachments/assets/ba1c6a69-48d5-49d2-b638-84328fc33508)
![ba32615fa8969e3c410a7ce84cc0e45](https://github.com/user-attachments/assets/956ad3ee-7da4-4e07-90fd-e150467f75a8)

