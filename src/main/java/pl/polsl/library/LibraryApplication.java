package pl.polsl.library;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.library.model.Member;
import pl.polsl.library.model.Role;
import pl.polsl.library.repository.MemberRepository;
import pl.polsl.library.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);

		}

//	@Bean
//	CommandLineRunner run(RoleRepository roleRepository, MemberRepository userRepository, PasswordEncoder passwordEncode){
//		return args ->{
//
//			String encodedPassword = passwordEncode.encode("admin");
//			Role userRole = roleRepository.findByAuthority("ADMIN").get();
//
//			Set<Role> authorities = new HashSet<>();
//			authorities.add(userRole);
//
//			Member member = new Member("admin@gmail.com", encodedPassword, "michael", "santa", "New York", authorities);
//			userRepository.save(member);
//		};
//	}
}
