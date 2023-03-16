package mg.douane.intervention.service;

public interface SendingMailService {
	boolean sendMail(String subject, String body);
}
