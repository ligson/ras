## ras

ra系统

###1.模块说明

####1.1.ra_api：api接口层

####1.2.ra_service：业务服务层

####1.3.ra_web：web处理层

###2.开发配置说明

###2.1.下载gradle2.10

    \\192.168.1.212\share\gradle-2.10-bin.zip

###2.2.配置项目

###2.2.1.ra_service：配置

    1. 配置文件在ra_service/src/main/resources/config.properties

    2. dubbo配置文件在ra_service/src/main/resources/dubbo.properties

###2.2.1.ra_web：配置

    2. dubbo配置文件在ra_web/src/main/resources/X-dubbo.properties

###2.3.启动项目

    1.ra_service:MainClass:org.ca.ras.common.main.Startup

    2.ra_web:配置到tomcat里即可

    3.此项目依赖kms,需要先启动kms

    4.RA管理中心:http://localhost:8090/ra/admin/login.html(超级管理员ra1,密码password)

    5.用户中心：http://localhost:8090/ra/


