# UMLGraph
## preface
### 环境配置
#### 1 papyrus，直接搜官网可下载
#### 2 jdk 17
我装的这个
#### 3 为papyrus安装支持uml识别的插件
教程见 https://wiki.eclipse.org/MDT/UML2/Getting_Started_with_UML2
#### 4 需要导入一个额外的jar包用于识别JSON
fastjson-1.2.78.jar，右键项目AttackPathGenerator->properties->Java Build Path ->Libraries->Add External JARS
#### 5 安装win10上的graphviz用于展示图
勾选添加到环境路径，stable_windows_10_cmake_Release_x64_graphviz-install-2.49.3-win64

## 项目结构
### test
ATM实例的UML图

### AttackPathGenerator
连通图生成代码，路径生成代码，新增了类成员提取接口
数据结构修改
邻接表存储图，新增了前驱节点信息
```
public class Vertex {
    int type; // 0: surface, 1: Node, 2: Asset
    Node itself;// keep current Node info
    Set<Vertex> next_vertexes;
    Set<Vertex> pre_vertexes;
    
```


### adjustmentWork
架构优化算法

### controlNodeWork
隔离域操作

### CriticalNodesGenerator
控制点生成算法

### EvaluationWork
架构评估

### StartOver
启动函数

### Visualization
图可视化

### UMLgenerator
uml样例生成算法



## 三个算法

### 提取
提了组件图、部署图，只提取了组件类型作为节点，依赖作为连接，连接件connector等都没提


### 合并和简化
简化做了等价节点的简化

### 路径生成
完成了一个简化版的dfs


### Complishlist
#### 1 连通图生成
##### 1.1 组合关系的连接没加，子组件和父组件，组件和端口
已经加了，通过层次关系建立联系
##### 1.2 包导入和包合并没加
getMembers可以获取导入的元素，并不用另外处理
#### 2 串联关系合并
合并了单输入的节点，除了资产

### TODOlist
简化和启发式算法，30个点就有一百多万条。
