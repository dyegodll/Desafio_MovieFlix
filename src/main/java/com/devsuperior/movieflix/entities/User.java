package com.devsuperior.movieflix.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	public User() {
	}

	public User(Long id, String name, String lastName, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(name, other.name) && Objects.equals(id, other.id);
	}

	//lista de papéis/perfís atribuídos ao usuário
	//converte tipo Role em GrantedAuthority a partir da lista roles
	//para enviar os dados para o AuthorizationServer do JWT(Jason Web Token) através do Spring Security
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
		//SimpleGrantedAuthority classe concreta da interface GrantedAuthority
	}

	//nome de credecial do usuário = email
	@Override
	public String getUsername() {
		return email;
	}

	//a conta não está expirada?
	@Override
	public boolean isAccountNonExpired() {
		// sim. a conta não está expirada
		return true;
	}

	//a conta não está bloqueada?
	@Override
	public boolean isAccountNonLocked() {
		// sim. a conta não está bloqueada
		return true;
	}

	//as credenciais não estão expiradas/inválidas?
	@Override
	public boolean isCredentialsNonExpired() {
		// sim. a credencial não está expirada
		return true;
	}

	//está habilitado?
	@Override
	public boolean isEnabled() {
		// sim. está habilitado
		return true;
	}

}