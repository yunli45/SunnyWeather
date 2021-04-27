# 记录安卓第一行代码第三版中开发实战-天气预报app
##  1、项目架构 MVVM(Model-View-ViewMode)
    主要分成三部分：Model 是数据模型部分；View 是界面展示部分；ViewModel可以理解为一个链接数据模型和界面展示的桥梁，从而实现让业务逻辑和界面展示部分
    分离的程序结构设计。
    还应该包含：仓库、数据源等
    
    示意图： ui控制层 -> ViewModel 层 -> 仓库层
        仓库层：本地数据源（model） 和 网络数据源（model)
    ui 控制层包含了：Activity 、Fragment 、布局文件等界面相关的东西 
    viewModel: 用于持有和 UI 元素相关的数据，以保证这些数据在屏幕旋转的时候数据不会丢失；并且还要 提供接口 给 UI控制层 
    调用和仓库层进行通信 由于ViewModel通常和Activity或 Fragment是一一对应的，因此我们还是习惯将它们放在一起
    
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
    
## 3、功能需求
具备以下功能
    可以搜索全球大多数国家的各个城市数据；
    可以查看全球绝大多数城市的天气信息；
    可以自由地切换城市，查看其他城市的天气；
    可以手动刷新实时的天气。
      
## 4、项目依赖
   原有的依赖：
        implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
        implementation 'androidx.core:core-ktx:1.3.2'
        implementation 'androidx.appcompat:appcompat:1.2.0'
        implementation 'com.google.android.material:material:1.2.1'
        implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
        testImplementation 'junit:junit:4.+'
        androidTestImplementation 'androidx.test.ext:junit:1.1.2'
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
        /**
        *  以下是项目的依赖
        */
       // RecyclerView是Android 5.0推出的，是support-v7包中的新组件,它被用来代替ListView和GridView，
       // 并且能够实现瀑布流的布局，更加高级并且更加灵活，提供更为高效的回收复用机制，同时实现管理与视图的解耦合。
       // 在第四章 4.6 更强大的滚动控件：RecyclerView
       implementation 'androidx.recyclerview:recyclerview:1.0.0'
       // 生命周期组件 （包含在 ViewModel）
       // 13.2.1 ViewModel的基本用法
       implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
       // jetpack 提供的一种响应式编程组件，可以包含任何类型的数据，并且在数据发生变化的时候通知给观察者。特别合适与 ViewModel 结合一起使用
       // 13.4 LiveData
       implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
       // material 12.3 滑动菜单
       implementation 'com.google.android.material:material:1.1.0'
       // swiperefreshlayout 12.6 下拉刷新
       implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
       implementation 'com.squareup.retrofit2:retrofit:2.6.1' // retrofit2
       implementation 'com.squareup.retrofit2:converter-gson:2.6.1' // retrofit2 gson 转换器
       // 11.7 Kotlin课堂：使用协程编写高效的并发程序
       // 携程依赖，第二个依赖库是在Android项目中才会用到的
       implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0"
       implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1"
## 5、记录书中错误代码部分
    添加依赖的错误：
        错误：implementation?"androidx.swiperefreshlayout:swiperefreshlayout:1.0.0" 这句会报错 ':' expected, got '}'
        正确：implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
 
## 6、部分记录
    token: Kd0V6U58a7aXLTZB
    PlaceResponse.kt文件中定义的类与属性，完全就是按照15.1节中搜索城市数据接
    口返回的JSON格式来定义的。不过，由于JSON中一些字段的命名可能与Kotlin的命名规范不太
    一致，因此这里使用了@SerializedName注解的方式，来让JSON字段和Kotlin字段之间建立
    映射关系
    仓库层有点像是一个数据获取与缓存的中间层，在
    本地没有缓存数据的情况下就去网络层获取，如果本地已经有缓存了，就直接将缓存数据返
    回。
    由于搜索城市数据的功能我们在后面还会复用，因
    此就不建议写在Activity里面了，而是应该写在Fragment里面，这样当需要复用的时候直接在
    布局里面引入该Fragment即可
    @SuppressLint("StaticFieldLeak"):
        SuppressLint : 忽略特定的编译器警告
    
    流程：搜索全部城市数据 -> 带上经纬度 -> 具体的城市的天气情况 
        采用 mvvm 的架构，然后界面使用 fragment 来进行复用， 使用 refit2 来进行网络请求 加上携程
            1、application -》 获取全局的context 、 设置参数 token
            2、新增 config 包主要放置一些配置文件的
            3、https://www.toutiao.com/i6954745113687917069/?tt_from=mobile_qq&utm_campaign=client_share&timestamp=1619481195&app=news_article&utm_source=mobile_qq&utm_medium=toutiao_ios&use_new_style=1&req_id=20210427075315010151152031001F3095&share_token=ABCDC0A9-2FEE-449E-854B-9A37F2E2F60E&group_id=6954745113687917069&wid=1619481398670
                架构篇-一分钟掌握可伸缩架构 - 待看