# 记录安卓第一行代码第三版中开发实战-天气预报app
##  1、项目架构 MVVM(Model-View-ViewMode)
    主要分成三部分：Model 是数据模型部分；View 是界面展示部分；ViewModel可以理解为一个链接数据模型和界面展示的桥梁，从而实现让业务逻辑和界面展示部分
    分离的程序结构设计。
    还应该包含：仓库、数据源等
    
    示意图： ui控制层 -> ViewModel 层 -> 仓库层
        仓库层：本地数据源（model） 和 网络数据源（model)
    ui 控制层包含了：Activity 、Fragment 、布局文件等界面相关的东西
    viewModel: 用于持有和 UI 元素相关的数据，以保证这些数据在屏幕旋转的时候数据不会丢失；并且还要 提供接口 给 UI控制层 调用和仓库层进行通信
    仓库层：主要工作是判断调用方请求的数据是从本地数据源中获取还是从网络数据源获取，并且将获取到的数据返回给调用方。 本地数据源可以使用数据库、 sharedPreferences 
    等持久化技术来实现，而网络数据源则通常使用 retrofit 访问服务器提供的 webservice 接口来实现

## 2、程序结构
    - logic包:用于存放业务逻辑相关的代码
        - dao: 存放数据访问对象 
        - model: 对象模型 
        - network:网络相关的代码
    - ui 包:存放界面相关的代码
        - place: 
        - weather:
    - MainActivity
## 3、项目依赖
   原有的依赖：
        implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
        implementation 'androidx.core:core-ktx:1.3.2'
        implementation 'androidx.appcompat:appcompat:1.2.0'
        implementation 'com.google.android.material:material:1.2.1'
        implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
        testImplementation 'junit:junit:4.+'
        androidTestImplementation 'androidx.test.ext:junit:1.1.2'
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'