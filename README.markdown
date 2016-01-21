
Java Keywords for Robot Frameworks, Demo
=======================================

这是一个完整的Java + Robot Frameworks的例子。


必需的软件
-----------

1. JDK 6/7/8，运行和开发环境中都需要。
2. [Robot Framework][rf]。有两种选择：
  - 使用RF的[Standalone JAR（绿色版）][rf-standalone] （推荐）
  - 从[Jython][rf-jython]中用`pip`安装
3. [Gradle][gradle]，仅开发（打包）环境需要，用来把Java程序打包成jar包


安装Gradle
----------

Gradle是Java的构建工具，这里用它来将我们的Java Keyword程序和所使用的各种第三方Java库打包成一个独立的jar文件，以便在Robot脚本中调用。

首先确认`javac`命令已经在你的`PATH`环境变量里，然后从[Gradle网站][gradle]下载它的zip包。
解压之后可以看到其中有一个`bin`目录，将该目录也添加到你的`PATH`中即可。
之后可以执行`gradle --version`命令，看到类似如下输出即说明安装正确：

```
$ gradle --version

------------------------------------------------------------
Gradle 2.10
------------------------------------------------------------

Build time:   2015-12-21 21:15:04 UTC
Build number: none
Revision:     276bdcded730f53aa8c11b479986aafa58e124a6

Groovy:       2.4.4
Ant:          Apache Ant(TM) version 1.9.3 compiled on December 23 2013
JVM:          1.7.0_91 (Oracle Corporation 24.91-b01)
OS:           Linux 3.13.0-32-generic amd64
```


运行这个例子
------------

#### 1
首先将这里的代码clone下来

```
git clone https://github.com/yangyd/rf-javakw.git
```

#### 2
进入目录，执行以下命令，生成jar包。

```
cd rf-javakw
gradle jar
```

第一次执行时会比较花时间，因为Gradle会根据我们在`build.gradle`中声明的依赖去自动下载所有的第三方Java库。

#### 3
顺利完成之后，可以看到当前目录下多了一个`build`目录，在其中可以找到打好的jar包，具体位置在 `build/libs/rf-javakw-1.0.jar`

```
build
├── libs
    └── rf-javakw-1.0.jar
```

执行测试时只需使用这个jar文件（以及相应的robot脚本）即可。

#### 4
示例的Robot脚本放在`robot`目录下。这里用RF的[Standalone JAR][rf-standalone]来执行测试。将它下载下来放在当前目录，然后执行以下命令

```shell
java -jar robotframework-2.9.2.jar --pythonpath build/libs/rf-javakw-1.0.jar robot/suite1.txt
```

应该可以看到如下的输出

```
==============================================================================
Suite1
==============================================================================
Test Robot Framework Logging                                          | PASS |
------------------------------------------------------------------------------
Test My Logging                                                       | PASS |
------------------------------------------------------------------------------
Test Use Java Keywords                                                | PASS |
------------------------------------------------------------------------------
Suite1                                                                | PASS |
3 critical tests, 3 passed, 0 failed
3 tests total, 3 passed, 0 failed
==============================================================================
Output:  /home/yd/javakw/output.xml
Log:     /home/yd/javakw/log.html
Report:  /home/yd/javakw/report.html
```


说明
------

- `java -jar robotframework-2.9.2.jar`是启动RF的命令，基本上就相当于`pybot`或`jybot`。
- 参数`--pythonpath xxx.jar`用来指定包含关键字的library。可以是jar文件，也可以是包含Python关键字模块的目录。如果用到多个library，要使用多个`--pythonpath`参数。
- Java关键字的Library就是一个普通的Java类，其中的每个`public`方法都可以作为关键字使用，只要调用的名字能匹配上。
- 在Robot脚本中引入Java library时，要使用那个Java类的全名，如这个例子中的`yd.robot.MyJavaHttpKeywords`

使用第三方Java库
-------------

Java关键字程序通常需要依赖第三方的Java库才能工作，例如访问RabbitMQ，调用AWS API等等。这里作为例子使用了[Apache HttpClient][apache-hc]，来发起简单的http请求

为了便于在RF中使用，我们将自己的代码(java keyword)和第三方库打成了一个单独的jar包，这是通过Gradle实现的。

注意到这里有一个`build.gradle`文件，这是控制Gradle的构建脚本，其中有一段如下：

```groovy
dependencies {
  compile 'org.apache.httpcomponents:httpasyncclient:4.1.1'
}
```

这就是声明对于[Apache HttpClient] 4.1.1的依赖。这里的 `'org.apache.httpcomponents:httpasyncclient:4.1.1'` 称为Maven坐标，用来定位一个Java库的特定版本。

要使用其他的Java库，只需在`dependencies`块中增加声明即可，例如，要在例子中加入[RabbitMQ Java客户端3.6版][amqp]的库，可以写成

```groovy
dependencies {
  compile 'org.apache.httpcomponents:httpasyncclient:4.1.1'
  compile 'com.rabbitmq:amqp-client:3.6.0'
}
```

之后即可在Java代码中通过`import`引用这些库中的类了。在执行`gradle jar`打包时，Gradle会自动下载所有必须的第三方jar包，并按照`build.gradle`中的指示将其与我们的代码合成到一个jar文件里。




[rf]: http://robotframework.org/
[rf-standalone]: https://github.com/robotframework/robotframework/blob/master/INSTALL.rst#standalone-jar-distribution
[rf-jython]: http://jython.org/
[gradle]: http://gradle.org/gradle-download/
[apache-hc]: https://hc.apache.org/
[amqp]: http://search.maven.org/#artifactdetails%7Ccom.rabbitmq%7Camqp-client%7C3.6.0%7Cjar
