package coding.proxy;

public class Order implements IOrder{

    //模拟支付状态，0 未支付
    int state = 0;
    @Override
    public void pay() throws InterruptedException {
        Thread.sleep(50);
        this.state = 1;
    }

    //显示支付状态
    @Override
    public void show() {
        System.out.println("order status:" + this.state);
    }

}
