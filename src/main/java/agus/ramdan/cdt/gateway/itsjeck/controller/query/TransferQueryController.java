package agus.ramdan.cdt.gateway.itsjeck.controller.query;

//import id.my.hendisantika.observability.entity.Peanuts;
//import id.my.hendisantika.observability.service.PeanutsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@Slf4j
//@Service
//@EnableCaching
@RestController
@RequestMapping("/api/cdt/gateway/itsjeck/local-transfer/query")
//@RequiredArgsConstructor
public class TransferQueryController {
    //private final PeanutsService peanutsService;

    @GetMapping("")
    public String root(@RequestParam(value = "name", defaultValue = "World") String name, @RequestHeader HttpHeaders headers) {
        log.error(headers.toString());
        log.error("Hello {}!!", name);
        log.debug("Debugging log");
        log.info("Info log");
        log.warn("Hey, This is a warning!");
        log.error("Oops! We have an Error. OK");
        return String.format("Hello %s!!", name);
    }

//    @GetMapping("/io_task")
//    public String ioTask() throws InterruptedException {
//        Thread.sleep(1000);
//        log.info("io_task");
//        return "io_task";
//    }
//
//    @GetMapping("/cpu_task")
//    public String cpuTask() {
//        for (int i = 0; i < 100; i++) {
//            int tmp = i * i * i;
//        }
//        log.info("cpu_task");
//        return "cpu_task";
//    }
//
//    @GetMapping("/random_sleep")
//    public String randomSleep() throws InterruptedException {
//        Thread.sleep((int) (Math.random() / 5 * 10000));
//        log.info("random_sleep");
//        return "random_sleep";
//    }
//
//    @GetMapping("/random_status")
//    public String randomStatus(HttpServletResponse response) throws InterruptedException {
//        List<Integer> givenList = Arrays.asList(200, 200, 300, 400, 500);
//        Random rand = new Random();
//        int randomElement = givenList.get(rand.nextInt(givenList.size()));
//        response.setStatus(randomElement);
//        log.info("random_status");
//        return "random_status";
//    }
//
//    @GetMapping("/chain")
//    public String chain() throws InterruptedException, IOException {
//        String TARGET_ONE_HOST = System.getenv().getOrDefault("TARGET_ONE_HOST", "localhost");
//        String TARGET_TWO_HOST = System.getenv().getOrDefault("TARGET_TWO_HOST", "localhost");
//        log.debug("chain is starting");
//        Request.Get("http://localhost:8080/")
//                .execute().returnContent();
//        Request.Get(String.format("http://%s:8080/io_task", TARGET_ONE_HOST))
//                .execute().returnContent();
//        Request.Get(String.format("http://%s:8080/cpu_task", TARGET_TWO_HOST))
//                .execute().returnContent();
//        log.debug("chain is finished");
//        return "chain";
//    }
//
//    @GetMapping("/error_test")
//    public String errorTest() throws Exception {
//        throw new Exception("Error test");
//    }
//
//    @GetMapping("/peanuts/{id}")
//    public Peanuts getPeanutsById(@PathVariable Long id) {
//        log.info("Get Peanuts Character by id");
//        return peanutsService.getPeanutsById(id);
//    }
//
//    @PostMapping("/peanuts")
//    public Peanuts savePeanuts(@RequestBody Peanuts peanuts) {
//        log.info("Create Peanuts Character");
//        return peanutsService.savePeanuts(peanuts);
//    }
}