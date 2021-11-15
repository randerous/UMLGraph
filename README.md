# UMLGraph
## preface
### 环境配置
1 papyrus，直接搜官网可下载
 2 jdk 11
 3 为papyrus安装支持uml识别的插件，教程见 https://wiki.eclipse.org/MDT/UML2/Getting_Started_with_UML2
 
### test
ATM实例的UML图

### AttackPathGenerator
连通图生成代码，图相关数据定义在/src/AttackPathGenerator/中

## 三个算法

### 提取
提了组件图、部署图，只提取了组件类型作为节点，依赖作为连接，连接件connector等都没提


### 合并和简化
简化做了等价节点的简化

### 路径生成
完成了一个简化版的dfs
