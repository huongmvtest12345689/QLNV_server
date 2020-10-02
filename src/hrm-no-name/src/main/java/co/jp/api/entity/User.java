package co.jp.api.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

import co.jp.api.model.CellInfoDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = User.TABLE_NAME)
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "user";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "start_date")
	private Timestamp startDate;

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
	private Integer deleteFlag;

	@Column(name = "display_order")
	private long displayOrder;
	
	@Column(name = "status")
	private Integer status;

	@Column(name = "role_id")
	private Integer roleId;

	@ManyToOne
	@JoinTable(name = "roles", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Roles rolesSet;

	public void setFieldValue(CellInfoDTO cellInfoDTO, String rawValue) throws NoSuchFieldException, IllegalAccessException {
		Class<User> userClass = User.class;
		Field nameField = userClass.getDeclaredField(cellInfoDTO.getName());
		System.out.println("Type: " +nameField.getType());
		if (nameField.getType() == Integer.class) {
			nameField.setAccessible(true);
			nameField.set(nameField, Integer.parseInt(rawValue));
		} else {
			nameField.setAccessible(true);
			nameField.set(nameField, rawValue);
		}
	}
}
