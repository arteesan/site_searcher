package artesan

import java.net.URL

import org.openqa.selenium.{By, Platform}
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._


object Main extends App{
  println("Hello")

  val capabilities = DesiredCapabilities.chrome();
  capabilities.setCapability("version", "");
  capabilities.setPlatform(Platform.LINUX);

  val driver = new RemoteWebDriver(new URL("http://192.168.233.138:4444/wd/hub"), capabilities)

  val hiveConnect = new HiveJdbcClient()
  hiveConnect.init()

  val companies = hiveConnect.executeSelect("SELECT * FROM companies")

  var compList = new ListBuffer[Company]()

  while(companies.next()){
    compList += Company(companies.getString(1),
      companies.getString(2),
      companies.getString(3),
      companies.getString(4)
    )
  }

  def parse (comp: Company):Site =
  {
    //driver.get("https://www.list-org.com/")
    //if name exists
    if(comp.name != null){
      println(comp.name)
      driver.get("https://www.google.com/search?q=компания+" + comp.name)
      val res = driver.findElementByClassName("g")
        .findElement(By.className("r"))
        .findElement(By.tagName("a"))
        .getAttribute("href")
      println(res)
      Site(comp.name, res)
    }else{
      println("null")
      Site(comp.name, "err")
    }

  }

  val sites = compList.map(parse)

  var createTable = "CREATE TABLE IF NOT EXISTS sites (company STRING, site STRING)"
  var countRows = "SELECT COUNT(*) FROM sites"
  var truncateTable = "TRUNCATE TABLE sites"

  var sqlInsert = sites.foldLeft("INSERT INTO sites VALUES ")
    {(acc, x) => acc + "('" + x.company + "', '" + x.site + "'),"}
  sqlInsert = sqlInsert.dropRight(1)

  println (sqlInsert)

  hiveConnect.execute(createTable)
  val count = hiveConnect.executeSelect(countRows)
  count.next()
  if (count.getInt(1) > 0){
    hiveConnect.execute(truncateTable)
  }
  hiveConnect.execute(sqlInsert)

  println("complete")

  Thread.sleep(10000)
  driver.close()
}
