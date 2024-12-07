package softuni.exam.models.dto.importXmls;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Length;

@XmlRootElement(name = "visitor")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorImportDto {

    @XmlElement(name = "first_name")
    @Length(min = 2, max = 20)
    private String firstName;

    @XmlElement(name = "last_name")
    @Length(min = 2, max = 20)
    private String lastName;

    @XmlElement(name = "attraction_id")
    private Long attraction;

    @XmlElement(name = "country_id")
    private Long country;

    @XmlElement(name = "personal_data_id")
    private Long personalData;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAttraction() {
        return attraction;
    }

    public void setAttraction(Long attraction) {
        this.attraction = attraction;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getPersonalData() {
        return personalData;
    }

    public void setPersonalData(Long personalData) {
        this.personalData = personalData;
    }
}
