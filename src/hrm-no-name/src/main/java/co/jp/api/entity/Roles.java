package co.jp.api.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Roles.TABLE_NAME)
@Getter
@Setter
public class Roles implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "roles";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "role_name", nullable = false)
	private String rolesName;

	@Column(name = "role_id", nullable = false)
	private Integer rolesId;

	@Column(name = "status", nullable = false)
	private Integer status;

	@Column(name = "permission_ids", nullable = false)
	private String permissionIds;

	@OneToMany(mappedBy = "rolesSet")
	private Set<User> users;
}
