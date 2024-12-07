package softuni.exam.models.dto.importJsons;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class CountryImportDto {

    @Expose
    @Length(min = 3, max = 40)
    private String name;

    @Expose
    @Positive
    private double area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
