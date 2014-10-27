import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovyx.net.http.RESTClient
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test
/**
 * Created by u2346 on 10/21/14.
 */

class FileServiceTest {
    Config config = ConfigFactory.load("application.conf");
    def http
    def url = config.getString("warn.test.webserviceUrl")

    @BeforeTest
    def setUp () {
        http = new RESTClient(url)
    }

    @Test (description = "This method test the file web service")
    void fileWebService () {
        def filePath = "/file/partners/85/2223/88017 1.jpg"
        def badFilePath = "/file/partners/85/2223/88017001.jpg"
        // perform a GET request, expecting BINARY aka a file
        println "The url is " + url + filePath
        try {
            def resp = http.get(path : filePath)
            assert resp.status == 200
        } catch (ex) {
            assert ex.response.status == 404
            //throw FileNotFoundException
        }

        println "The bad url is " + url + badFilePath
        try {
            def resp = http.get(path : badFilePath)
            assert resp.status == 200
        } catch (ex) {
            assert ex.response.status == 404
            //throw FileNotFoundException
        }

    }

    @Test (description = "This method test the directory web service")
    void directoryWebService () {
        def directoryPath = "/directory/partners/85/2223"
        def badDirectoryPath = "/directory/partners/85/222300"
        // perform a GET request, expecting TEXT in the response
        println "The url is " + url + directoryPath
        try {
            def resp = http.get(path : directoryPath)
            assert resp.status == 200
        } catch (ex) {
            assert ex.response.status == 404
            //throw DirectoryIteratorException
        }

        println "The bad url is " + url + badDirectoryPath
        try {
            def resp = http.get(path : badDirectoryPath)
            assert resp.status == 200
        } catch (ex) {
            assert ex.response.status == 404
            //throw DirectoryIteratorException
        }
    }
}
