package softuni.exam.models.dto.importXmls;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "visitors")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorsImportDto {

    @XmlElement(name = "visitor")
    private List<VisitorImportDto> visitorImportDtos;

    public List<VisitorImportDto> getVisitorImportDtos() {
        return visitorImportDtos;
    }

    public void setVisitorImportDtos(List<VisitorImportDto> visitorImportDtos) {
        this.visitorImportDtos = visitorImportDtos;
    }
}
