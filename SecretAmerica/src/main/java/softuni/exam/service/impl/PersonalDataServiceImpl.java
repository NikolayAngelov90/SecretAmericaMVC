package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.importXmls.PersonalDataImportDto;
import softuni.exam.models.dto.importXmls.PersonalDatasImportDto;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private static final String IMPORT_XML_PATH = "C:\\Users\\nikit\\OneDrive\\Desktop\\projects\\SecretAmerica\\SecretAmerica\\src\\main\\resources\\files\\xml\\personal_data.xml";

    private final PersonalDataRepository personalDataRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;


    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository, ModelMapper modelMapper,
                                   ValidationUtil validationUtil, XmlParser xmlParser) {
        this.personalDataRepository = personalDataRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(IMPORT_XML_PATH));
    }

    @Override
    public String importPersonalData() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        PersonalDatasImportDto personalDatasImportDto = this.xmlParser.fromFile(
                IMPORT_XML_PATH, PersonalDatasImportDto.class);

        for (PersonalDataImportDto personalData : personalDatasImportDto.getPersonalDatas()) {
            if (this.personalDataRepository.findByCardNumber(personalData.getCardNumber()).isPresent() ||
                    !this.validationUtil.isValid(personalData)) {
                sb.append("Invalid personal data").append(System.lineSeparator());
                continue;
            }

            this.personalDataRepository.saveAndFlush(
                    this.modelMapper.map(personalData, PersonalData.class));

            sb.append(String.format(
                    "Successfully imported personal data for visitor with card number %s",
                    personalData.getCardNumber()));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
