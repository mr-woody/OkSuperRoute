## OkSuperRoute for Android 支持在组件化和插件化项目机构下使用的路由框架


### 示例介绍
1. 页面路由

   * 打开浏览器百度首页
   * 跳转到数据打印页(使用动态登录检查)
   * 跳转到数据打印页(使用指定登录拦截器)
   * 跳转到数据打印页(不触发拦截器)
   * 跳转到用户信息页(自带登录拦截器)
   * 跳转到数据打印页(添加额外数据)
   * 使用ActivityResult回调

2. 动作路由
   * 启动一个简单的动作路由
   * 启动一个简单的动作路由(使用注解进行线程指定)
   * 启动一个简单的动作路由(使用执行器进行线程指定)

3. 对象路由
   * 使用对象路由 创建实体bean

### 演示下载
[*Sample Apk*](/apk/app-debug.apk)


### 如何开始

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.mr-woody.OkSuperRoute:router-api:1.1.0'
    annotationProcessor 'com.github.mr-woody.OkSuperRoute:router-compiler:1.1.0'
}
```

## 用法

### 为Activity添加路由规则

- 指定路由前缀与路由表生成包名

```
@RouteConfig(
	baseUrl = "woodys://page/", // 路由前缀：用于结合Route合成完整的路由链接
	pack = "com.woodys.router") // 路由表生成包名：配置后。路由表生成类将会放置于此包下
class App extends Application {...}
```

- 为目标页指定路由规则链接

```
// 在目标Activity上添加Route注解，添加对应的路由规则
// 同一目标页可以添加多个不同的路由链接。
@Route({
	// 可只指定path, 此种数据会与RouteConfig中的baseUrl进行结合:
	// 最终完整链接为：woodys://page/example
	"example",
	// 也可直接指定完整链接
	"total://example"
	})
class ExampleActivity extends BaseActivity { ... }
```

### 注册路由表

经过上面的配置后，编译后即可生成对应的路由表类，生成的路由表类名统一为**RouterRuleCreator**:

然后即可通过以下代码进行路由表注册使用：

```java
RouterConfiguration.get().addRouteCreator(new RouterRuleCreator())
```

### 启动路由

还是以上面的example为例。要使用Router启动ExampleActivity, 使用以下链接进行跳转

```java
Router.create("woodys://page/example").open(context)
```

### 启动浏览器打开网页

当路由链接为http/https时，且此时本地的也没有页面配置过此链接地址时，将触发使用**跳转浏览器打开链接**逻辑

比如浏览器打开百度页面

```
Router.create("https://www.baidu.com").open(context)
```

### 启用内部日志打印

```java
Router.DEBUG = true
```

启用后，即可通过OkSuperRoute进行过滤查看

### 添加额外数据启动

```java
Bundle extra = getExtras();
Router.create(url)
	.addFlags(flag) // 添加启动标记位，与Intent.addFlags(flag)相同
	.addExtras(extra) // 添加额外数据：Intent.addExtras(bundle)
	.requestCode(code) // 使用startActivityForResult进行启动
	.setAnim(in, out) // 设置转场动画。Activity.overridePendingTransition(inAnim, outAnim)
	.open(context)
```

### 使用路由回调

路由回调为**RouteCallback**接口，用于在进行路由启动后，对该次路由事件的状态做回调通知：

```java
public interface RouteCallback {
	// 当用于启动的路由链接未匹配到对应的目标页时。回调到此
	void notFound(Uri uri, NotFoundException e);
	// 当启动路由成功后，回调到此。可在此通过rule.getRuleClz()获取对应的目标页类名。
	void onOpenSuccess(Uri uri, RouteRule rule);
	// 当启动路由失败后，回调到此。
	void onOpenFailed(Uri uri, Throwable e);
}
```

路由回调的配置分为两种：

1. **全局路由回调**：所有的路由启动事件均会回调到此

```java
RouterConfiguration.get().setCallback(callback)
```

2. **临时路由回调**：只对当次路由事件生效

```java
Router.create(url).setCallback(callback).open(context)
```

路由回调机制在进行界面路由跳转埋点时，是个很好的特性。

有时候我们会需要在回调中使用启动时添加的额外数据，而回调的api中并没有提供此数据，所以此时我们需要使用以下方法进行额外数据获取：

```java
此方法只能在回调方法内调用，运行完回调方法后会自动清空。
RouteBundleExtras extras = RouterConfiguration.get().restoreExtras(uri);
```

### 使用ActivityResultCallback

**ActivityResultCallback**接口用于自动处理onActivityResult逻辑，可有效避免在onActivityResult中写一堆的判断switch逻辑。是个很棒的特性。

```java
public interface ActivityResultCallback {
	void onResult(int resultCode, Intent data);
}
```

使用此特性前，需要在BaseActivity中的onActivityResult方法处，添加上派发方法：

```java
RouterConfiguration.get()
	.dispatchActivityResult(this, requestCode, resultCode, data)
```

然后即可直接使用

```java
// 添加了resultCallback属性后，即可不指定requestCode了。免去了取值的烦恼
Router.create(url).resultCallback(resultCallback).open(context)
```

### 使用路由拦截器拦截器

拦截器，顾名思义，就是在路由启动过程中，进行中间状态判断，是否需要拦截掉此次路由事件。使其启动失败。

拦截器的接口名为**RouteInterceptor**

```java
public interface RouteInterceptor {
    void intercept(@NonNull ActionChain chain);

    interface ActionChain {
        // 表示当前拦截自己处理，不继续传递给下一个拦截器
        void onIntercept();

        // 分发给下一个拦截器
        void proceed(@NonNull ActionRequest actionPost);

        // 获取 ActionRequest
        ActionRequest action();
    }
}
```

Router经过长期的迭代，对拦截器进行了详细的分类，提供了三种拦截器提供使用:

**1. 全局拦截器**：对所有的路由事件生效。

```
RouterConfiguration.get().setInterceptor(interceptor);
```

**2. 单次拦截器**：对当次路由事件生效。

```java
Router.create(url)
	// 是的你没有看错，可以配置多个不同的拦截器实例
	.addInterceptor(interceptor1)
	.addInterceptor(interceptor2)
	.open(context);
```

**3. 指定目标的拦截器**：对指定的目标页面生效

```java
// 在配置的Route的目标页，添加此RouteInterceptors注解即可。
// 在此配置的拦截器，当使用路由启动此页面时，即可被触发。
@RouteInterceptors({CustomRouteInterceptor.class})
@Route("user")
public class UserActivity extends BaseActivity {...}
```

### 恢复路由的方式

既然路由可以被拦截，那么也可以直接被恢复。

```java
Router.resume(uri, extras).open(context);
```

光这样看有点不太直观。我们举个最经典的**登录检查拦截**案例作为说明：

![](/image/image1.png)

当不使用路由进行跳转时，这种情况就会导致你本地写上了大量的登录判断逻辑代码。这在维护起来是很费劲的。而且也非常不灵活，而使用拦截器的方式来做登录检查，就会很方便了：

![](/image/image2.png)

下面是一个简单的登录拦截实现：

```java
// 实现RouteInterceptor接口
public class LoginInterceptor implements RouteInterceptor{
    @Override
    public boolean intercept(Uri uri, RouteBundleExtras extras, Context context){
    	// 未登录时进行拦截
        return !LoginChecker.isLogin();
    }

    @Override
    public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
    	// 拦截后跳转登录页并路由信息传递过去，便于登录后进行恢复
        Intent loginIntent = new Intent(context,LoginActivity.class);
        // uri为路由链接
        loginIntent.putExtra("uri",uri);
        // extras中装载了所有的额外配置数据
        loginIntent.putExtra("extras",extras);
        context.startActivity(loginIntent);
    }
}
```

```java
public class LoginActivity extends BaseActivity {

	@Arg
	Uri uri;
	@Arg
	RouteBundleExtras extras;

	void onLoginSuccess() {
		if(uri != null) {
			// 登录成功。使用此方法直接无缝恢复路由启动
			Router.resume(uri, extras).open(context);
		}
		finish();
	}
}
```

### 自动解析传递url参数

Router支持自动从url中解析参数进行传递：

```java
Router.create("woodys://page/user?username=woodys&uid=123456")
	.open(context);
```

上面的链接即代表：跳转到**woodys://page/user**页面，并传递username和uid数据过去。



### 使用动作路由

上面主要介绍的页面跳转的路由，也叫页面路由，但实际上。有的时候我们使用路由启动的，并不是需要启动某个页面。而是需要执行一些特殊的操作：比如**添加购物车**、**强制登出**等。此时就需要使用动作路由了。

#### 创建动作路由

动作路由通过继承**ActionSupport**类进行创建：

```java
// 与页面路由一样。添加Route注解配置路由链接即可。
@Route("action/hello")
public class SayHelloAction extends ActionSupport {
	@Override
	public void invoke(Context context, Bundle data) {
		//  启动动作路由成功会触发调用此方法。
		Toast.makeText(context, "Hello! this is an action route!", Toast.LENGTH_SHORT).show();
	}
}
```

动作路由的启动方式与页面路由一致:

```java
Router.create("woodys://page/action/hello").open(context)
```

#### 指定动作路由的执行线程

动作路由是用于执行一些特殊的操作的路由，而有时候部分操作是需要在指定线程进行处理的：

动作路由提供两种指定线程的操作：

1. 启动前进行配置(优先级高)：

```java
Router.create(url).setExecutor(executor).open(context);
```

2. 在定制动作路由时，直接指定线程：

```java
@RouteExecutor(CustomExecutor.class)
@Route("action/hello")
public class SayHelloAction extends ActionSupport {...}
```

**在没有配置过线程切换器时。默认使用MainThreadExecutor。指定线程为主线程**

### 在目标页获取启动链接

```java
// 先从目标页读取bundle数据
Bundle bundle = getBundle();
// 然后从bundle中读取即可
Uri lauchUri = bundle.getParcelable(Router.RAW_URI);
```

### 使用对象路由

对象路由(`InstanceRouter`)，主要作用为通过指定路由链接，创建出具体的对象实例提供使用：

`对象路由`的配置方式是与`页面路由`,`动作路由`类似。也是直接在指定类上添加Route注解，如此处将UserFragment作为实例创建目标：

```
@Route("woodys://page/fragment/user")
class UserFragment extends Fragment
		// 实现ICreatorInjector接口。复写方法以接收传参
		implements ICreatorInjector{

	@Override
	public void inject(Bundle bundle) {
		// 接收传参
	}
}
```

然后即可通过路由链接启动并获取UserFragment实例：

```
UserFragment user = Router.createInstanceRouter("woodys://page/fragment/user")
		.addExtras(bundle)// 也可以添加额外参数
		.createInstance<UserFragment>()// 获取具体实例进行使用
```

当然，不限于Fragment，你也可以为其他的任意类(除`Activity`与`ActionSupport`)添加上对象路由的配置，比如一个简单的普通bean：

```
// 对任意类添加路由配置注解
@Route("woodys://pojo/user")
class Uesr implements ICreatorInjector {
	@Override
	public void inject(Bundle bundle) {
		// 接收传参
	}
}

// 然后通过指定的链接，直接获取实例
User user = Router.createInstanceRouter("woodys://pojo/user")
			.createInstance<User>()
```


### 其他文档
* [ChangeLog](/document/CHANGE_LOG.md)