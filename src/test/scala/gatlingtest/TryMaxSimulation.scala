package gatlingtest

import io.gatling.core.Predef._

// 2
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {
  // 3

  val httpConf = http // 4
    .baseURL("http://computer-database.gatling.io") // 5
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
    .disableFollowRedirect

  val scn = scenario("BasicSimulation") // 7
    .exec(http("request_1") // 8
    .get("/")) // 9
    .pause(5) // 10
    .exec(session => session.set("counter", 3))
    .tryMax("${counter}") {
      exec(http("should_fail_3_times")
        .get("/")
        .check(status is 401))
        .pause(2)
    }
    .exec(session => session.set("counter_as_string", "3"))
    .tryMax("${counter_as_string}") {
      exec(http("should_fail_once")
        .get("/")
        .check(status is 401))
        .pause(2)
    }
    .tryMax(5) {
      exec(http("should_fail_5_times")
        .get("/")
        .check(status is 401))
        .pause(2)
    }

  setUp(// 11
    scn.inject(atOnceUsers(1)) // 12
  ).protocols(httpConf) // 13
}