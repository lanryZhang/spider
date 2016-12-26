import com.ifeng.pgc.core.distribute.master.Master;
import org.junit.Test;

/**
 * Created by zhanglr on 2016/9/19.
 */
public class ServerMaster {
    @Test
    public void main() throws Exception {

        Master master = new Master();
        master.start();
    }
}
