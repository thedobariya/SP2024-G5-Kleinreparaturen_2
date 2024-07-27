/*
 * Copyright 2014-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kleinreparatur_service.startpage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		String role;
		if (authentication != null) {
			role = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.findFirst()
				.orElse("ROLE_USER"); // default to ROLE_USER if no roles are found
		} else {
			role = "ROLE_GUEST"; // default to ROLE_GUEST if no user is logged in
		}

		model.addAttribute("role", role);
		return "welcome";
	}

	@GetMapping("/impressum")
	public String impressum() {
		return "impressum";
	}

	@GetMapping("/datenschutz")
	public String datenschutz() {
		return "datenschutz";
	}

	@GetMapping("/agb")
	public String agb() {
		return "agb";
	}
}

