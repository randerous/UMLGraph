# UMLGraph
## preface
### 环境配置
#### 1 papyrus，直接搜官网可下载
#### 2 jdk 11
#### 3 为papyrus安装支持uml识别的插件
教程见 https://wiki.eclipse.org/MDT/UML2/Getting_Started_with_UML2
#### 4 需要导入一个额外的jar包用于识别JSON
fastjson-1.2.78.jar，右键项目AttackPathGenerator->properties->Java Build Path ->Libraries->Add External JARS
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


### TODOlist
#### 1 连通图生成
##### 1.1 组合关系的连接没加，子组件和父组件，组件和端口
##### 1.2 包导入和包合并没加
#### 2 暴露面合并
##### 并查集算法有bug，parent数组在方法中是值传递，更新和最后的集合计算不对
##### 连接关系，考虑通用的节点组合并算法，应该加所有输入和输出。


