package CriticalNodesGenerator;


import AttackPathGenerator.*;
import java.lang.Math;
import java.math.BigInteger;
import java.util.*;

public class Matrix {
    Graph graph;
//    int n=graph.vertexes.size();//总结点数
    int n;
    int m;//节点组中节点个数
    double[][] AdjacentMatrix =new double[n][n];//邻接矩阵定义和无向图一样
    double[][] DegreeMatrix=new double[n][n];//入度+出度
    double[][] Laplacian=new double[n][n];
    Map<String,Integer> vertexesIndex=new HashMap(n);//index对应node的id

    public  Matrix(Graph graph,int m){
        this.graph=graph;
        this.n=graph.vertexes.size();
        this.m=m;

        int i=0;
        for(Map.Entry<String,Vertex> v:graph.vertexes.entrySet()){
            vertexesIndex.put(new String(v.getKey()),i++);
        }
        for(Map.Entry<String,Vertex> v:graph.vertexes.entrySet()) {
            int source=vertexesIndex.get(v.getKey());
            DegreeMatrix[source][source]+=v.getValue().getNextV().size();
            for(Vertex each:v.getValue().getNextV()){
                int target=vertexesIndex.get(each.getItself().getID());
                AdjacentMatrix[source][target]=1;//这里以后可以改成边的权值
                AdjacentMatrix[target][source]=1;
                DegreeMatrix[target][target]++;
            }
        }
    }

    public  Matrix(double[][] adjM,double[][] DegM,int n,int m){
        this.AdjacentMatrix=adjM;
        this.DegreeMatrix=DegM;
        this.n=n;
        this.m=m;
    }

    public  Matrix(double[][] lap,int n,int m){
        this.Laplacian=lap;
        this.n=n;
        this.m=m;
    }



    public void GetLaplacian(){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                this.Laplacian[i][j]=this.DegreeMatrix[i][j]-this.AdjacentMatrix[i][j];
    }


    public List<Integer> getCriticalNodesForce(int m, List<Double> MinEigenvalues){
        /*
        @m:选择m个节点作为关键节点组（有很多种选法，需要选出最小特征值最大的那组）
        @MinEigenvalues: 所有组的最小特征值，虽然我也不知道返回这个有什么用
        @return:具有最大的最小特征值的节点组
         */
        List<List<Integer>> combinations=Comb.combine(n,m);
        int size=combinations.size();
        System.out.println("size="+size);
//        List<Double>MinEigenvalues=new ArrayList<>(size);

        List<Integer> res=null;
        double max=0;
        for(int i=0;i<size;i++){
            /*
            1. 获取matrix
            2. 循环获取特征值
             */
            List<Integer> combi=combinations.get(i);
            System.out.println("第"+i+"个");
            double[][] subMatrix=new double[n-m][n-m];
            double[] EigVal=new double[n-m];
            for(int j=1,x=0;j<=n && x<n-m;j++){
                //删掉第j行
                if(combi.contains(j)) continue;
                for(int k=1,y=0;k<=n && y<n-m;k++){
                    //删掉第k列
                    if(combi.contains(k)) continue;
                    subMatrix[x][y++]=Laplacian[j-1][k-1];
                }
                x++;
            }

            Jacobi.Find_Eigen(n-m,subMatrix,EigVal);
            double minEign=EigVal[0];
            for(int j=1;j<n-m;j++)
                if(EigVal[j]<minEign) minEign=EigVal[j];
            MinEigenvalues.add(minEign);
            if(max<minEign) {
                max = minEign;
                res=combi;
            }
        }

        return res;
    }



}
