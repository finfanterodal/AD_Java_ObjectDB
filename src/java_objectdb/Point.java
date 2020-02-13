//represent Point objects in the database
package java_objectdb;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private int x;
    private int y;

    public Point() {
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    @Override
    public String toString() {
        return String.format("id: "+id+" punto:"+"(%d, %d)", this.x, this.y);
    }
    
}
