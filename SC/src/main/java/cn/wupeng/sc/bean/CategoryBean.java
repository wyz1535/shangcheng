package cn.wupeng.sc.bean;

import java.util.List;

/**
 * Created by heshun on 2016/7/5.
 */
public class CategoryBean {

    /**
     * category : [{"id":1,"isLeafNode":false,"name":"巧克力区","parentId":0,"pic":"/images/category/qiaokeli.jpg","tag":"巧克力"},{"id":2,"isLeafNode":false,"name":"果冻区","parentId":0,"pic":"/images/category/guodong.jpg","tag":"果冻"},{"id":3,"isLeafNode":false,"name":"坚果区","parentId":0,"pic":"/images/category/jianguo.jpg","tag":"坚果"},{"id":4,"isLeafNode":false,"name":"熟食区","parentId":0,"pic":"/images/category/shushi.jpg"},{"id":5,"isLeafNode":false,"name":"糕点区","parentId":0,"pic":"/images/category/gaodian.jpg"},{"id":6,"isLeafNode":false,"name":"饼干区","parentId":0,"pic":"/images/category/binggan.jpg"},{"id":11,"isLeafNode":true,"name":"ENON巧克力 6盒装 更实惠","parentId":1,"pic":"/images/category/c2.jpg","tag":""},{"id":12,"isLeafNode":true,"name":"ROSHEN巧克力  乌克兰特色巧克力","parentId":1,"pic":"/images/category/c3.jpg","tag":""},{"id":13,"isLeafNode":true,"name":"国产大包装巧克力棒 好吃停不下来","parentId":1,"pic":"/images/category/c4.jpg","tag":""},{"id":14,"isLeafNode":true,"name":"韩国进口 Lock果冻","parentId":2,"pic":"/images/category/b1.jpg","tag":"0"},{"id":15,"isLeafNode":true,"name":"水果口味果冻 好吃的不得了","parentId":2,"pic":"/images/category/b2.jpg","tag":"0"},{"id":21,"isLeafNode":true,"name":"百草味坚果 买三送一仅限5天","parentId":3,"pic":"/images/category/a1.jpg"},{"id":22,"isLeafNode":true,"name":"新疆特产 格格坚果","parentId":3,"pic":"/images/category/a2.jpg"},{"id":23,"isLeafNode":true,"name":"好吃的糕点","parentId":5,"pic":"/images/category/g1.jpg"},{"id":33,"isLeafNode":true,"name":"肉干","parentId":4,"pic":"/images/category/h1.jpg"},{"id":111,"isLeafNode":true,"name":"意芙金币巧克力 可以玩的巧克力","parentId":1,"pic":"/images/category/c9.jpg","tag":""},{"id":112,"isLeafNode":true,"name":"DairyMilk 牛奶巧克力","parentId":1,"pic":"/images/category/c10.jpg"},{"id":113,"isLeafNode":true,"name":"HB 生日快乐巧克力 送人必备","parentId":1,"pic":"/images/category/c11.jpg"},{"id":114,"isLeafNode":true,"name":"盼盼肉松饼 前30位购买更多优惠哦!","parentId":5,"pic":"/images/category/j3.jpg"},{"id":115,"isLeafNode":true,"name":"俄罗斯进口威化饼干,来自战斗民族的美味!","parentId":5,"pic":"/images/category/j4.jpg","tag":"0"},{"id":116,"isLeafNode":true,"name":"椰蓉酥手工饼干 高端美味 你值得拥有","parentId":5,"pic":"/images/category/j5.jpg","tag":"0"},{"id":117,"isLeafNode":true,"name":"永恒经典 水晶之恋果冻 ","parentId":2,"pic":"/images/category/jelly-9.jpg","tag":"0"},{"id":118,"isLeafNode":true,"name":"喜之郎果肉果冻 果肉更大更爽","parentId":2,"pic":"/images/category/jelly-10.jpg","tag":"0"},{"id":119,"isLeafNode":true,"name":"喜之郎吸吸 吸吸嘻嘻","parentId":2,"pic":"/images/category/jelly-11.jpg","tag":"0"},{"id":120,"isLeafNode":true,"name":"韩国紫水晶果冻 现在购买更多优惠哦","parentId":2,"pic":"/images/category/jelly-12.jpg","tag":"0"},{"id":121,"isLeafNode":true,"name":"恰恰瓜子 根本停不下来","parentId":3,"pic":"/images/category/nut-3.jpg","tag":"0"},{"id":122,"isLeafNode":true,"name":"良品铺子蚕豆 好滋味 嘎嘣脆","parentId":3,"pic":"/images/category/nut-9.jpg","tag":"0"},{"id":123,"isLeafNode":true,"name":"恰恰核桃 好吃洽一洽","parentId":3,"pic":"/images/category/nut-11.jpg","tag":"0"},{"id":124,"isLeafNode":true,"name":"香山瓜子 更大包装","parentId":3,"pic":"/images/category/nut-7.jpg","tag":"0"},{"id":125,"isLeafNode":true,"name":"烤饼干","parentId":6,"pic":"/images/category/m4.jpg","tag":"0"},{"id":126,"isLeafNode":true,"name":"特级饼干","parentId":6,"pic":"/images/category/m5.jpg","tag":"0"},{"id":127,"isLeafNode":true,"name":"苏打饼干","parentId":6,"pic":"/images/category/m1.jpg","tag":"0"},{"id":128,"isLeafNode":true,"name":"魔法士","parentId":6,"pic":"/images/category/m2.jpg"},{"id":129,"isLeafNode":true,"name":"饼干","parentId":6,"pic":"/images/category/m3.jpg"},{"id":130,"isLeafNode":true,"name":"小饼干","parentId":6,"pic":"/images/category/m4.jpg"},{"id":131,"isLeafNode":true,"name":"鸭爪 经典美味","parentId":4,"pic":"/images/category/w1.jpg"},{"id":132,"isLeafNode":true,"name":"麻辣猪耳朵 和啤酒更配哦","parentId":4,"pic":"/images/category/w2.jpg"},{"id":133,"isLeafNode":true,"name":"进口牛肉 供不应求 美味至上","parentId":4,"pic":"/images/category/w3.jpg"},{"id":134,"isLeafNode":true,"name":"爆款小龙虾 仅售5天 速速抢购吧","parentId":4,"pic":"/images/category/w4.jpg"},{"id":135,"isLeafNode":true,"name":"经典猪肉脯 袋装即食","parentId":4,"pic":"/images/category/w5.jpg"},{"id":144,"isLeafNode":true,"name":"杯子","parentId":33,"pic":"/images/category/r.jpg"},{"id":145,"isLeafNode":true,"name":"餐具","parentId":33,"pic":"/images/category/re.jpg"},{"id":146,"isLeafNode":true,"name":"餐具叉子","parentId":33,"pic":"/images/category/rg.jpg"},{"id":147,"isLeafNode":true,"name":"玩具","parentId":33,"pic":"/images/category/rh.jpg"},{"id":148,"isLeafNode":true,"name":"老虎钳","parentId":33,"pic":"/images/category/ry.jpg"},{"id":149,"isLeafNode":true,"name":"电饭锅","parentId":33,"pic":"/images/category/rj.jpg"},{"id":150,"isLeafNode":true,"name":"衣服","parentId":154,"pic":"/images/category/qe.jpg"},{"id":151,"isLeafNode":true,"name":"韩国膨化饼干,现在购买更多优惠哦!","parentId":5,"pic":"/images/category/j1.jpg"},{"id":152,"isLeafNode":true,"name":"新款衣服","parentId":154,"pic":"/images/category/qo.jpg"},{"id":153,"isLeafNode":true,"name":"新款衣服","parentId":154,"pic":"/images/category/qr.jpg"},{"id":154,"isLeafNode":true,"name":"超大包装 促销价9元带回家","parentId":5,"pic":"/images/category/j2.jpg"}]
     * response : category
     */

    private String response;
    /**
     * id : 1
     * isLeafNode : false
     * name : 巧克力区
     * parentId : 0
     * pic : /images/category/qiaokeli.jpg
     * tag : 巧克力
     */

    private List<SimpleCategoryBean> category;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<SimpleCategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<SimpleCategoryBean> category) {
        this.category = category;
    }

}
