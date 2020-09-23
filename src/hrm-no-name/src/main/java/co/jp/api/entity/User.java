package co.jp.api.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = User.TABLE_NAME)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
//@AllArgsConstructor
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "user";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	@NonNull private String name;

	@Column(name = "phone")
	@NonNull private String phone;

	@Column(name = "email", nullable = false)
	@NonNull private String email;

	@Column(name = "password", nullable = false)
	@NonNull private String password;

	@Column(name = "start_date")
	@NonNull private Timestamp startDate;

	@Column(name = "end_date")
	private Timestamp endDate;
	
	@Column(name = "token")
	private String token;

	@Column(name = "token_start")
	private Timestamp tokenStart;

	@Column(name = "token_end")
	private Timestamp tokenEnd;

	@Column(name = "code")
	private String code;

	@Column(name = "code_start")
	private Timestamp codeStart;

	@Column(name = "code_end")
	private Timestamp codeEnd;
	
	@Column(name = "delete_flag")
	@NonNull  private Integer deleteFlag;

	@Column(name = "display_order")
	@NonNull private long displayOrder;
	
	@Column(name = "status")
	@NonNull private Integer status;

	@Column(name = "roles_id")
	private Integer rolesId;

	@ManyToMany
	@JoinTable(name = "roles_name", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private Set<Roles> rolesSet;

}
