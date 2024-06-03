# online-bank-system-frontend

## 安装依赖
```
npm install
```

### 运行
```
npm run serve
```





页面主要放在views这个文件夹底下，然后我的建议是每个组的页面单独建一个文件夹（参考放的CreditCard文件夹）

router文件夹下的index.js文件存储路由信息

store文件下下的index.js文件存储静态变量信息，大概是用来放切换页面的时候需要保存的信息（如果不用cookie的话）

component和assets文件夹下我也不知道要放啥（似乎可能用不到？），你们需要的话就用吧qwq

然后我有个小小的建议，要不大家就不要在src/App.vue这个文件里加单独的元素，不然可能会影响到别人的页面orz



现在这个应该是可以运行的（虽然没啥功能，只是为了测测配置文件有没有问题orz）

大家把自己以前写的东西迁移过去应该就行了（不过可能有些地方需要改一下相对路径orz）
