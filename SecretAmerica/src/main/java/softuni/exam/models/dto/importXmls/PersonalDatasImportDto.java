package softuni.exam.models.dto.importXmls;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "personal_datas")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDatasImportDto {

    @XmlElement(name = "personal_data")
    private List<PersonalDataImportDto> personalDatas;

    public List<PersonalDataImportDto> getPersonalDatas() {
        return personalDatas;
    }

    public void setPersonalDatas(List<PersonalDataImportDto> personalDatas) {
        this.personalDatas = personalDatas;
    }
}
