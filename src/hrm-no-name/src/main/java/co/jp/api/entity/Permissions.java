package co.jp.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = Roles.TABLE_NAME)
@Getter
@Setter
public class Permissions {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "permissions_modules";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "permissions_modules_id", nullable = false)
    private Integer permissionsModulesId;

    @Column(name = "name", nullable = false)
    private Integer name;

    @Column(name = "permissions_key", nullable = false)
    private Integer permissions_key;

    @Column(name = "description")
    private Integer description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;
}
