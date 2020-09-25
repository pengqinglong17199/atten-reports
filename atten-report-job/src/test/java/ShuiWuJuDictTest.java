import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.job.ReportsJobApplication;
import com.quanroon.atten.reports.job.shenzhen.shuiwuju.ShenZhenSWJService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReportsJobApplication.class)
public class ShuiWuJuDictTest {

    @Autowired
    private ShenZhenSWJService shenZhenSWJService;

    private String api_secret = "71cdd9b7300649f0b66f6175b09c5f3c";
    private String api_key = "379142c703274f2c83c63b6b3e9fcb29";
    private String api_version = "1.0";
    private String client_serial = "379142c703274f2c83c63b6b3e9fcb29";
    private String timestamp = DateUtils.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");

    @Test
    public void getJobName() throws Exception{
        UpParams upParams = new UpParams();
        upParams.setKey("379142c703274f2c83c63b6b3e9fcb29");
        upParams.setSecret("71cdd9b7300649f0b66f6175b09c5f3c");
        shenZhenSWJService.getJobName(upParams);
        //shenZhenSWJService.getCompanyType(upParams);
        //shenZhenSWJService.getGroupType(upParams);
        //shenZhenSWJService.getWorkerType(upParams);
    }

}
