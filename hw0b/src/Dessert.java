public class Dessert {
    // 实例变量
    private int flavor;
    private int price;

    // 静态变量（类变量） - 记录创建的甜点数量
    private static int numDesserts = 0;

    // 构造函数
    public Dessert(int flavor, int price) {
        this.flavor = flavor;
        this.price = price;
        numDesserts++;  // 每创建一个甜点，计数加1
    }

    // 打印甜点信息的方法
    public void printDessert() {
        System.out.println(flavor + " " + price + " " + numDesserts);
    }

    // 主方法
    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}