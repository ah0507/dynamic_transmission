package net.chensee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "od_logging")
public class LoggingPo {

    @Id
    private String id;

    private String exceptionName;

    private String exceptionContent;

    private String exceptionDetail;

    private Date createTime;
}
