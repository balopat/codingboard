import com.samebug.notifier.servlet.SamebugFilter
import com.samebug.notifier.{SamebugUncaughtExceptionHandler, Samebug}
import java.util
import javax.servlet.DispatcherType
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.webapp.WebAppContext
import com.balopat.codingboard.CodingBoardServlet
import org.eclipse.jetty.servlet.ServletHolder

object JettyLauncher {


  def main(args: Array[String]) {
    Samebug.handleUncaughtExceptions()
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

    Samebug.init()

    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.addServlet(new ServletHolder(new CodingBoardServlet()), "/*")
    context.addServlet(classOf[DefaultServlet], "/")
    context.addFilter(classOf[SamebugFilter], "/*", util.EnumSet.allOf(classOf[DispatcherType]))

    server.setHandler(context)

    server.start
    server.join
  }

}
