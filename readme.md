# SAAS 学习总结

1. 资源地址：

   视频来源：哔哩哔哩：苍海之南 【关注】

   >  地址：https://www.bilibili.com/video/BV1QJ411S7c4?p=12

   > 本地资源地址：E:\项目练习笔记\SAAS-HRM

2.  SAAS  是什么？

   ​       多租户技术又称多重租赁技术：是一种软件架构技术，是实现如何在多用户环境下（此处的多用户一般是面向企业用户）共用相同的系统或程序组件，并且可确保各用户间数据的隔离性。  

    简单讲：
           在一台服务器上运行单个应用实例，它为多个租户（客户）提供服务。从定义中我们可以理解：多租户是一种架构，目的是为了让多用户环境下使用同一套程序，且保证用户间数据隔离  

   ​       在隔离的同时，数据可能有共享的数据，并且也可能有不一样的，有共享的内容，也有独立隔离的内容；

3.  架构搭建

   1. 构建父工程

   2. 公共代码类：

       * 返回值类封装

       * 雪花算法生成ID

         借助二进制生成64Bit的数据 （1bit不用占位符、 41bit的时间戳、10bit-工作机器ID、12bit的序列号）同一机器同一毫秒支持2的12次方 4096个

   3.   构建实体类模块，配置lombok 插件；

   4.   构建主模块，启动配置，包扫描，数据库配置；

   5.    数据库设计：1NF,2NF,3NF

       *    第一范式：1NF  确保每一列的原子性（做到每列不可拆分）  
       *   第二范式（2NF）：在第一范式的基础上，非主字段必须依赖于主字段（一个表只做一件事）比如：用户表  只有用户数据，用户有夫妻，房产等，只能新建表存储  
       *   第三范式（3NF）：在第二范式的基础上，消除传递依赖  

       ​     Schema：Oracle的Schema 指就是表空间，mysql 的Schmea 指的是一个一个的数据库

       6.  VUE  脚后手架工程

          

       4. VUE  模块
       
          1. 模块分析
       
              ![image-20200413060142984](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060142984.png)
       
          2.  VUE 发送请求，执行数据的逻辑分析
       
             1.  VUE 发送请求，执行数据的逻辑
       
             2.  进入页面，在页面元素加载之前，首先会执行钩子函数；
       
             3.  然后由钩子函数调用我们的methods中的method() 方法，--来加载页面所需要的值
       
             4.  method()方法来调用API接口
       
             5.  API接口把请求发送给后端服务，
       
             6.  后端服务把请求数据返回给API接口
       
             7.  data()来把API返回的数据来绑定到我们的数据模型上面
       
             8. 通过数据模型对页面进行渲染
       
                ![image-20200413060406555](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060406555.png)
       
          3.  环境搭建
       
             1. 新增模块
                在src目录下创建文件夹，命名规则：module-模块名称（）创建好之后，如何把新增的模块注册呢？src/main.js 
       
             2. 构造模拟模拟数据
       
                ![image-20200413060604628](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060604628.png)
       
             3. 注册模块
       
                ![image-20200413060633509](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060633509.png)
       
             4. 配置路由菜单
       
                ![image-20200413060703538](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060703538.png)
       
             5. 编写页面
       
             6.  配置API 方法
       
                 ![image-20200413060738987](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060738987.png)
       
             7. 构造方法调用过程
       
                ![image-20200413060823920](C:\Users\maoxb\AppData\Roaming\Typora\typora-user-images\image-20200413060823920.png)
       
             8. 
       
                
       
                
       
          4.  
       
          5. 
       
       

​              

 