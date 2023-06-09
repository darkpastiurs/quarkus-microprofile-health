package sv.com.dkps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tvserie")
public class TvSerieEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column(length = 1000)
    private String summary;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String nombre) {
        this.name = nombre;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String resumen) {
        this.summary = resumen;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TvSerieEntity other = (TvSerieEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TvSerieEntity [id=" + id + ", nombre=" + name + ", resumen=" + summary + "]";
    }    
}
