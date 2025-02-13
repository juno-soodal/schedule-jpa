# Schedule

### API

https://documenter.getpostman.com/view/12745356/2sAYXCiyFN

### SQL

```sql
CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created_at datetime NOT NULL,
                        updated_at datetime NOT NULL,
                        deleted_at datetime
);

CREATE TABLE schedule (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        content longtext NOT NULL,
                        member_id BIGINT NOT NULL,
                        created_at datetime NOT NULL,
                        updated_at datetime NOT NULL,
                        deleted_at datetime,
                        CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

CREATE TABLE comment (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        comment_content varchar(255) NOT NULL,
                        member_id BIGINT NOT NULL,
                        schedule_id BIGINT NOT NULL,
                        created_at datetime NOT NULL,
                        updated_at datetime NOT NULL,
                        deleted_at datetime,
                        CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES MEMBER (id),
                        CONSTRAINT fk_schedule FOREIGN KEY (schedule_id) REFERENCES SCHEDULE (id)
);
```

<br>

### ERD

<img width="697" alt="Image" src="https://github.com/user-attachments/assets/a6317166-75bc-4d6b-a872-580fa8ed620f" />