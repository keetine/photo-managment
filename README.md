# photo-managment

# 前端（Vue）
这是一个基于springboot+vue的相册管理程序，是一个练手项目，项目实现了一些基本的文件/数据库读写功能，前端适应移动端
# project setup
~~~
npm install
~~~
# 运行项目
~~~
npm run serve
~~~

# 此套项目包含：
## 1. 自定义全局弹窗

![image](https://user-images.githubusercontent.com/56806703/203675375-c6c8d73e-8208-47ed-a2f9-86e245741269.png)
`{message:"请输入用户名"，type:"error"}`
![image](https://user-images.githubusercontent.com/56806703/203676039-60ae4987-48ae-43d4-b86c-64bb18e41bf0.png)
`{message:"今天天天气不错"，type:"default"}`
![image](https://user-images.githubusercontent.com/56806703/203676401-a0a05bd1-f421-4db5-be8f-a1e287d2f0f5.png)
`{message:"登录成功"，type:"success"}`
![image](https://user-images.githubusercontent.com/56806703/203676647-75afcb43-ed83-4fea-9cf8-8ed15815cece.png)
`{message:"住手！别点那里"，type:"warning"}`

## 2. 页面效果
### 2.1 登陆注册
![image](https://user-images.githubusercontent.com/56806703/203677009-a3cb3601-ccab-46d2-bf51-a63dc6b5647f.png)
### 2.2 相册分类导航页
![image](https://user-images.githubusercontent.com/56806703/203679666-2a350724-d46d-4f3c-bf05-55ffe2fc969b.png)

### 2.3 相册详情及预览图片
![GIF](https://user-images.githubusercontent.com/56806703/203680852-1c251381-b268-4b62-b50c-b352e2c8b3ed.gif)

### 2.4 双指捏合缩小首页布局(不便于演示)
![image](https://user-images.githubusercontent.com/56806703/203683091-9b03787a-9ee4-4328-b2c3-1e047f9ef5c5.png)

## 3. 全局导航
![image](https://user-images.githubusercontent.com/56806703/203677174-a70cfe2f-e481-425d-85b7-473a00d9898e.png)

## 4. 封装全局全局axios请求拦截和响应拦截器
~~~ JavaScript
const request = axios.create({...});
// 请求处理
request.interceptors.request.use(
    (config) => {...},
    (err) => {...}
)
// 响应拦截
request.interceptors.response.use(
    (response) => {...}，
    (err) => {...}
)

export default request;
~~~

## 5.伸缩菜单
![GIF](https://user-images.githubusercontent.com/56806703/203678748-137d28f3-6788-4cd5-9d80-8b3727bad6f4.gif)
![GIF](https://user-images.githubusercontent.com/56806703/203680113-bd48c55e-c3cf-459c-8bec-aa41c06ee557.gif)

## 6. 弹出框
![GIF](https://user-images.githubusercontent.com/56806703/203680519-61f8293e-2fe7-4072-ae73-e2d6bfe53f2b.gif)


# 后端（Spring boot）

## 1. 图片压缩
~~~ java
public static void compressPic(String srcFilePath, String descFilePath, double ratio) {
    File srcFile = new File(srcFilePath);
    long srcFileSize = srcFile.length();
    // 图片小于100kb不压缩
    if (srcFileSize < 100 * 1024) {
        ratio = 1;
    }else if (srcFileSize < 500 * 1024) {
        ratio = 0.8;
    }else if (srcFileSize < 1024 * 1024) {
        ratio = 0.6;
    }else if (srcFileSize < 2 * 1024 * 1024) {
        ratio = 0.4;
    }else if (srcFileSize < 5 * 1024 * 1024) {
        ratio = 0.3;
    }else {
        ratio = 0.2;
    }
    try {
        BufferedImage src = ImageIO.read(srcFile);
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage tag = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src, 0, 0, (int) (width * ratio), (int) (height * ratio), null);
        ImageIO.write(tag, "JPEG", new File(descFilePath));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
~~~

## 2. 全局异常处理
~~~ java
@ExceptionHandler(MException.class)
public AjaxResult exceptionHandler(MException e, HttpServletResponse response) {
    response.setStatus(e.getCode());
    return new AjaxResult().fail(e.getCode(),e.getMessage());
}
...
~~~
## 3.ajax工具类
~~~
...
public AjaxResult(int code, boolean success, String message, Object dataSet) {
    this.code = code;
    this.success = success;
    this.message = message;
    this.dataSet = dataSet;
}
...
~~~

## 密码密文处理（瞎搞）
~~~ java
...
// 加密
public static String encrypt(String password) {
    // 加密方式：将密码的每个字符的ASCII码值乘以2，再将结果转为16进制
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < password.length(); i++) {
        sb.append(Integer.toHexString(password.charAt(i) * 2));
    }
    return sb.toString();
}
// 解密
public static String decrypt(String password) {
    // 解密方式：将密码的每个字符的ASCII码值除以2，再将结果转为10进制
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < password.length(); i += 2) {
        sb.append((char) (Integer.parseInt(password.substring(i, i + 2), 16) / 2));
    }
    return sb.toString();
}
...
~~~

后端还有Authorization验证等功能就不一一赘述了
