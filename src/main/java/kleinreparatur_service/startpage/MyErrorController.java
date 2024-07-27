package kleinreparatur_service.startpage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		Object servletName = request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
		Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

		String trace = null;
		if (exception != null) {
			Throwable throwable = (Throwable) exception;
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement element : throwable.getStackTrace()) {
				sb.append(element.toString()).append("\n");
			}
			trace = sb.toString();
		}

		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			model.addAttribute("status", statusCode);
			model.addAttribute("statusDescription", HttpStatus.valueOf(statusCode).getReasonPhrase());
		} else {
			model.addAttribute("status", "N/A");
			model.addAttribute("statusDescription", "N/A");
		}

		model.addAttribute("error", errorMessage != null ? errorMessage.toString() : "N/A");
		model.addAttribute("message", servletName != null ? servletName.toString() : "N/A");
		model.addAttribute("path", requestUri != null ? requestUri.toString() : "N/A");
		model.addAttribute("trace", trace != null ? trace : "N/A");

		return "error";
	}
}