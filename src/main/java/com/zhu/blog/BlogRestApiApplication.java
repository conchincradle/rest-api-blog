package com.zhu.blog;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.zhu.blog.entity.Role;
import com.zhu.blog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogRestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role useRole = new Role();
		useRole.setName("ROLE_USER");
		roleRepository.save(useRole);



	}

}
