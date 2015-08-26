package ua.kiev.prog;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="Photos")
@XmlRootElement(name="Photo")
public class Photo {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Column(length=5120000)
    private byte[] body;

    public Photo() {}

    public Photo(String name, byte[] body) {
        this.name = name;
        this.body = body;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="body")
    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
