package cn.projects.com.projectsdemo.treeview;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.projects.com.projectsdemo.R;
import cn.projects.com.projectsdemo.treeview.bean.Node;

/**
 * Created by fengxing on 2017/4/28.
 */

public class TreeHelper {


    /**
     * 传入普通的Bean 转换为排序后的node
     *
     * @param data
     * @param defaultExpandLevel
     * @return
     */
    public static <T> List<Node> getSortedNodes(List<T> data, int defaultExpandLevel) {
        List<Node> result = new ArrayList<>();
        //将用户数据转换成List<Node> 以及设置node之间的关系
        List<Node> nodes = null;
        try {
            nodes = convertData2Node(data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //获取根结点
        List<Node> nodeRoot = getRootNodes(nodes);

        //排序
        for (Node node : nodeRoot) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }


    /**
     * 把一个节点上的内容全部都挂载上去
     *
     * @param result             最后的结果
     * @param node               根结点
     * @param defaultExpandLevel 默认展开几级
     * @param currentLevel       当前等级
     */
    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        //默认展开
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }
        for (int i = 0; i < node.children.size(); i++) {
            addNode(result, node.children.get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    /**
     * 将我们的数据转换成成树的节点
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> List<Node> convertData2Node(List<T> data) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        for (T t : data) {
            int id = -1;
            int pId = -1;
            String label = null;
            Class<? extends Object> aClass = t.getClass();
            //getDeclaredFields()只能获取自己声明的各种字段，包括public，protected，private。
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {

                if (field.getAnnotation(TreeNodeId.class) != null) {
                    //成员变量为private,故必须进行此操作
                    field.setAccessible(true);
                    id = field.getInt(t);
                }

                if (field.getAnnotation(TreeNodePId.class) != null) {
                    field.setAccessible(true);
                    pId = field.getInt(t);
                }

                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }

                if (id != -1 && pId != -1 && label != null) {
                    break;
                }
            }
            Node node = new Node(id, pId, label);
            nodes.add(node);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            Node nodeOne = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node nodeTwo = nodes.get(j);

                //不明白啊  到底怎么回事
                if (nodeTwo.pid == nodeOne.id) {
                    nodeOne.children.add(nodeTwo);
                    nodeTwo.parent = nodeOne;
                } else if (nodeTwo.id == nodeOne.pid) {
                    nodeTwo.children.add(nodeOne);
                    nodeOne.parent = nodeTwo;
                }

            }
        }

        //设置图片
        for (Node n : nodes) {
            setNodeIcon(n);
        }

        return nodes;
    }

    /**
     * 过滤出可见的node
     * 只要是根结点 或者 上层目录为展开状态
     *
     * @param mAllData
     * @return
     */
    public static List<Node> filterVisibleNodes(List<Node> mAllData) {
        List<Node> result = new ArrayList<>();
        for (Node node : mAllData) {
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    public static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> roots = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                roots.add(node);
            }
        }
        return roots;
    }


    private static void setNodeIcon(Node node) {
        if (node.children.size() > 0 && node.isExpand) {
            node.icon = R.drawable.tree_ex;
        }else if(node.children.size()>0 && !node.isExpand){
            node.icon = R.drawable.tree_ec;
        }else {
            node.icon = -1;
        }
    }
}
