import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.distribute.worker.Worker;
import org.junit.Test;

/**
 * Created by zhanglr on 2016/9/19.
 */
public class Server {
    @Test
    public void main() throws Exception {
        System.out.println("beginning....");
        BeanLoader.getContext();
        System.out.println("initializing over");
        System.out.println("spide beginning");

//        EIPProcessJob eipProcessJob = new EIPProcessJob();
//        eipProcessJob.execute();
//        Master master = new Master();
//        master.start();

//        Worker worker = new Worker();
//        worker.connect();
    }
}
