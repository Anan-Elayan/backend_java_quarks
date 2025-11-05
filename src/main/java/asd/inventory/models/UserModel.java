package asd.inventory.models;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public double city;

    @Column(nullable = false)
    public int phoneNumber;

    @Column(nullable = false)
    public String password;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCity() { return city; }
    public void setCity(double city) { this.city = city; }

    public int getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(int phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void getPassword(String password) { this.password = password; }

}
