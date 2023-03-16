package mg.douane.intervention.controller;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.service.SendingMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class FormController {

	@Autowired
	SendingMailService sendingMailService;

	@GetMapping("/contact")
	public String contact() {
		return "redirect:/form";
	}

	@GetMapping("/form")
	public String formGet(Model model) {
		model.addAttribute("useremail", new Agent());
		return "form";
	}

	@PostMapping("/form")
	public String formPost(@Valid Agent agent, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "form";
		}

		model.addAttribute("noErrors", true);
		model.addAttribute("agent", agent);
		String subject = agent.getNumMatAgent() + " " + agent.getUsername() + " sent you a message";
		sendingMailService.sendMail(subject, "Mot de passe oublier");
		return "form";
	}

}
